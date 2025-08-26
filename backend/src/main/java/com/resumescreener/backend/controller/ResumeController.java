package com.resumescreener.backend.controller;

import com.resumescreener.backend.dto.ResumeRequest;
import com.resumescreener.backend.model.Resume;
import com.resumescreener.backend.repository.ResumeRepository;
import com.resumescreener.backend.utils.SafeResumeParser;
import com.resumescreener.backend.utils.SkillExtractor;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private static final Logger logger = LoggerFactory.getLogger(ResumeController.class);

    @Autowired
    private ResumeRepository resumeRepository;

    /**
     * Endpoint for screening resume from raw text (JSON payload)
     */
    @PostMapping("/screen")
    public Resume screenResume(@RequestBody ResumeRequest request) {
        // === Input Validation ===
        if (request == null) {
            logger.warn("Received null request body");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body cannot be empty");
        }
        if (request.getResumeText() == null || request.getResumeText().isBlank()) {
            logger.warn("Received empty resume text");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Resume text cannot be empty");
        }
        if (request.getJobDescription() == null || request.getJobDescription().isBlank()) {
            logger.warn("Received empty job description");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job description cannot be empty");
        }

        String resumeText = request.getResumeText();
        String jobDesc = request.getJobDescription();

        logger.info("Screening raw resume text ({} chars) against job description ({} chars)",
                resumeText.length(), jobDesc.length());

        // === Extract Skills & Score ===
        List<String> skills = SkillExtractor.extractSkills(resumeText);
        double score = SkillExtractor.calculateMatchScore(skills, jobDesc);

        logger.info("Extracted {} skills: {}", skills.size(), skills);
        logger.info("Calculated match score: {}", score);

        // === Save Resume to MongoDB ===
        Resume resume = new Resume();
        resume.setJobDescription(jobDesc);
        resume.setExtractedSkills(skills);
        resume.setMatchScore(score);
        resume.setSubmittedAt(LocalDateTime.now());

        try {
            Resume saved = resumeRepository.save(resume);
            logger.info("Resume saved successfully with ID {}", saved.getId());
            return saved;
        } catch (DataAccessException dae) {
            logger.error("Database error while saving resume: {}", dae.getMessage(), dae);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error while saving resume");
        }
    }

    /**
     * Endpoint for uploading resume as file (PDF/DOC, DOCX)
     */
    @PostMapping("/upload")
    public Resume uploadResume(@RequestParam("file") MultipartFile file,
                               @RequestParam("jobDescription") String jobDescription)
            throws IOException, TikaException {
        // === Input Validation ===
        if (jobDescription == null || jobDescription.isBlank()) {
            logger.warn("Received empty job description");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job description cannot be empty");
        }
        if (file == null || file.isEmpty()) {
            logger.warn("Received empty resume file");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Resume file is required");
        }

        logger.info("Received file '{}' ({} bytes)", file.getOriginalFilename(), file.getSize());

        // === Parse Resume Text ===
        String resumeText = SafeResumeParser.parse(file);
        logger.info("Parsed resume text length: {}", resumeText.length());

        // === Extract Skills & Score ===
        List<String> extractedSkills = SkillExtractor.extractSkills(resumeText);
        double matchScore = SkillExtractor.calculateMatchScore(extractedSkills, jobDescription);

        logger.info("Extracted {} skills: {}", extractedSkills.size(), extractedSkills);
        logger.info("Calculated match score: {}", matchScore);

        // === Save Resume to MongoDB ===
        Resume resume = new Resume();
        resume.setJobDescription(jobDescription);
        resume.setExtractedSkills(extractedSkills);
        resume.setMatchScore(matchScore);
        resume.setSubmittedAt(LocalDateTime.now());

        try {
            Resume savedResume = resumeRepository.save(resume);
            logger.info("Resume saved successfully with ID {}", savedResume.getId());
            return savedResume;
        } catch (DataAccessException dae) {
            logger.error("Database error while saving uploaded resume: {}", dae.getMessage(), dae);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error while saving resume");
        }
    }
}