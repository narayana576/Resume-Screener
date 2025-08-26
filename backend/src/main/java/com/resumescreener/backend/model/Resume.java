package com.resumescreener.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "resumes")
public class Resume {

    @Id
    private String id;

    @Field("candidate_name")
    private String name;

    // Optional: Raw skills provided by candidate (if any)
    @Field("candidate_skills")
    private List<String> skills = new ArrayList<>();

    // Job description against which resume is evaluated
    @Field("job_description")
    private String jobDescription;

    // Skills extracted from uploaded resume text/file
    @Field("extracted_skills")
    private List<String> extractedSkills = new ArrayList<>();

    @Field("match_score")
    private double matchScore;

    @Field("submitted_at")
    private LocalDateTime submittedAt;

    // ---- Constructors ----
    public Resume() {
        // Default constructor for MongoDB mapping
    }

    public Resume(String name, List<String> skills) {
        this.name = name;
        this.skills = (skills != null) ? skills : new ArrayList<>();
    }

    public Resume(String name, List<String> skills, String jobDescription,
                  List<String> extractedSkills, double matchScore, LocalDateTime submittedAt) {
        this.name = name;
        this.skills = (skills != null) ? skills : new ArrayList<>();
        this.jobDescription = jobDescription;
        this.extractedSkills = (extractedSkills != null) ? extractedSkills : new ArrayList<>();
        this.matchScore = matchScore;
        this.submittedAt = (submittedAt != null) ? submittedAt : LocalDateTime.now();
    }

    // ---- Getters & Setters ----
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) {
        this.skills = (skills != null) ? skills : new ArrayList<>();
    }

    public String getJobDescription() { return jobDescription; }
    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }

    public List<String> getExtractedSkills() { return extractedSkills; }
    public void setExtractedSkills(List<String> extractedSkills) {
        this.extractedSkills = (extractedSkills != null) ? extractedSkills : new ArrayList<>();
    }

    public double getMatchScore() { return matchScore; }
    public void setMatchScore(double matchScore) { this.matchScore = matchScore; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = (submittedAt != null) ? submittedAt : LocalDateTime.now();
    }

    // ---- Utility Methods ----
    public void autoFillSubmittedAtIfNull() {
        if (this.submittedAt == null) {
            this.submittedAt = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "Resume{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", extractedSkills=" + extractedSkills +
                ", matchScore=" + matchScore +
                ", submittedAt=" + submittedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;
        Resume resume = (Resume) o;
        return Objects.equals(id, resume.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}