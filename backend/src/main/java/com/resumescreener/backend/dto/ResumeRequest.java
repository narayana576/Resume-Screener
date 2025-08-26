package com.resumescreener.backend.dto;

public class ResumeRequest {
    private String resumeText;      // Can be raw text for now (later: file or URL)
    private String jobDescription;  // Job description provided by user

    public ResumeRequest() {}

    public ResumeRequest(String resumeText, String jobDescription) {
        this.resumeText = resumeText;
        this.jobDescription = jobDescription;
    }

    public String getResumeText() {
        return resumeText;
    }

    public void setResumeText(String resumeText) {
        this.resumeText = resumeText;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
}