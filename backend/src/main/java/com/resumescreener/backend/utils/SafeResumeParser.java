package com.resumescreener.backend.utils;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class SafeResumeParser {

    private static final Logger logger = LoggerFactory.getLogger(SafeResumeParser.class);

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2 MB
    private static final String[] ALLOWED_TYPES = {
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    };

    public static String parse(MultipartFile file) throws IOException, TikaException {
        validateFile(file); // basic checks before parsing

        Tika tika = new Tika();
        try {
            String text = tika.parseToString(file.getInputStream());

            // Normalize whitespace for checks & preview
            String cleaned = text == null ? "" : text.replaceAll("\\s+", " ").trim();

            // Debugging: log extracted content info
            if (cleaned.isEmpty()) {
                logger.debug("Parsed text is EMPTY or NULL");
            } else {
                logger.debug("Parsed text length = {} | First 100 chars = \"{}\"",
                        cleaned.length(),
                        cleaned.substring(0, Math.min(100, cleaned.length())));
            }

            // Validation:
            // - must not be blank
            // - must contain at least one real word (2+ alphanumeric chars)
            boolean hasRealWord = cleaned.matches(".*[a-zA-Z0-9]{2,}.*");

            if (cleaned.isBlank() || !hasRealWord) {
                throw new IllegalArgumentException("Uploaded file contains no readable text.");
            }

            return cleaned;

        } catch (IllegalArgumentException e) {
            throw e; // validation case
        } catch (IOException e) {
            logger.error("I/O Error while reading '{}': {}", file.getOriginalFilename(), e.getMessage(), e);
            throw new IOException("Unable to read uploaded file. Please try again.");
        } catch (TikaException e) {
            logger.error("Tika parsing error for '{}': {}", file.getOriginalFilename(), e.getMessage(), e);
            throw new TikaException("Unable to parse file. Ensure it is a valid PDF or Word document.", e);
        } catch (Exception e) {
            logger.error("Unexpected error parsing '{}': {}", file.getOriginalFilename(), e.getMessage(), e);
            throw new TikaException("Unexpected error occurred while parsing the file.", e);
        }
    }

    private static void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty or missing.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File exceeds the maximum allowed size of 2 MB.");
        }

        String contentType = file.getContentType();
        String fileName = Objects.requireNonNullElse(file.getOriginalFilename(), "").toLowerCase();

        if (fileName.isBlank()) {
            throw new IllegalArgumentException("File name is invalid.");
        }

        if (contentType == null || !isAllowedType(contentType)) {
            logger.warn("Rejected file '{}' with Content-Type '{}'. Allowed: {}",
                    fileName, contentType, Arrays.toString(ALLOWED_TYPES));
            throw new IllegalArgumentException("Unsupported file type. Only PDF and Word documents are allowed.");
        }
    }

    private static boolean isAllowedType(String contentType) {
        return Arrays.stream(ALLOWED_TYPES)
                .anyMatch(allowed -> allowed.equalsIgnoreCase(contentType));
    }
}
