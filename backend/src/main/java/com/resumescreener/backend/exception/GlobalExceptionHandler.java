package com.resumescreener.backend.exception;

import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatusCode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // === Utility: Create consistent error response body ===
    private Map<String, Object> buildErrorBody(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return body;
    }

    // === 400: Apache Tika Parsing error ===
    @ExceptionHandler(TikaException.class)
    public ResponseEntity<Map<String, Object>> handleTikaException(TikaException ex) {
        logger.error("Tika parsing error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                buildErrorBody(HttpStatus.BAD_REQUEST, "Invalid or unreadable resume file. Ensure it's a valid PDF or Word document."),
                HttpStatus.BAD_REQUEST
        );
    }

    // === 400: IllegalArgumentException (Validation failures from SafeResumeParser, bad params) ===
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Validation error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                buildErrorBody(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    // === 400: I/O errors from file reading/processing ===
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, Object>> handleIOException(IOException ex) {
        logger.error("File I/O error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                buildErrorBody(HttpStatus.BAD_REQUEST, "File processing error: " + ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    // === 400: Missing multipart/form-data field (file or jobDescription) ===
    @ExceptionHandler({ MissingServletRequestPartException.class, MissingServletRequestParameterException.class })
    public ResponseEntity<Map<String, Object>> handleMissingPartOrParam(Exception ex) {
        logger.error("Missing request part/parameter: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                buildErrorBody(HttpStatus.BAD_REQUEST, "Required form-data field is missing."),
                HttpStatus.BAD_REQUEST
        );
    }

    // === 400: General multipart handling errors ===
    @ExceptionHandler({ MultipartException.class })
    public ResponseEntity<Map<String, Object>> handleMultipartErrors(MultipartException ex) {
        logger.error("Multipart error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                buildErrorBody(HttpStatus.BAD_REQUEST, "Error processing file upload. Ensure the file is valid."),
                HttpStatus.BAD_REQUEST
        );
    }

    // === 413: File exceeds maximum upload size configured in Spring ===
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, Object>> handleMaxUploadSize(MaxUploadSizeExceededException ex) {
        logger.error("File too large: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                buildErrorBody(HttpStatus.PAYLOAD_TOO_LARGE, "Uploaded file is too large. Maximum allowed size is 2 MB."),
                HttpStatus.PAYLOAD_TOO_LARGE
        );
    }

    // === 500: Database errors ===
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, Object>> handleDatabaseError(DataAccessException ex) {
        logger.error("Database access error: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                buildErrorBody(HttpStatus.INTERNAL_SERVER_ERROR, "Database error occurred while processing your request."),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // === 400: Handle ResponseStatusException cleanly for validations etc. ===
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        logger.warn("ResponseStatusException: {}", ex.getMessage());

        HttpStatusCode statusCode = ex.getStatusCode();
        String message = ex.getReason() != null ? ex.getReason() : ex.getMessage();

        // cast HttpStatusCode to HttpStatus if needed
        HttpStatus status = (statusCode instanceof HttpStatus) ? (HttpStatus) statusCode : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(
                buildErrorBody(status, message),
                statusCode
        );
    }

    // === 500: Fallback for unhandled exceptions ===
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        logger.error("Unhandled exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                buildErrorBody(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please try again later."),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
