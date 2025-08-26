import React, { useState, useRef } from "react";
import axios from "axios";
import { FiXCircle, FiUploadCloud } from "react-icons/fi";
import "./ResumeUpload.css";

const ResumeUpload = () => {
  const [file, setFile] = useState(null);
  const [jobDescription, setJobDescription] = useState("");
  const [matchScore, setMatchScore] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fileInputRef = useRef();

  const handleFileChange = (e) => {  
    setFile(e.target.files[0]);
    setMatchScore(null);
    setError(null);
  };

  const handleJobDescChange = (e) => {
    setJobDescription(e.target.value);
    setMatchScore(null);
    setError(null);
  };

  const handleRemoveFile = (e) => {
    e.preventDefault();
    setFile(null);
    if (fileInputRef.current) fileInputRef.current.value = "";
  };

  // Drag & Drop handlers
  const [dragActive, setDragActive] = useState(false);

  const handleDrag = (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (e.type === "dragenter" || e.type === "dragover") setDragActive(true);
    else if (e.type === "dragleave") setDragActive(false);
  };

  const handleDrop = (e) => {
    e.preventDefault();
    e.stopPropagation();
    setDragActive(false);
    if (e.dataTransfer.files && e.dataTransfer.files[0]) {
      setFile(e.dataTransfer.files);
      setMatchScore(null);
      setError(null);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!file) {
      setError("⚠️ Please upload a resume file.");
      return;
    }
    if (!jobDescription.trim()) {
      setError("⚠️ Please enter a job description.");
      return;
    }
    setError(null);
    setMatchScore(null);
    setLoading(true);

    const token = localStorage.getItem("token");
    const formData = new FormData();
    formData.append("file", file);
    formData.append("jobDescription", jobDescription);

    try {
      const response = await axios.post("/api/resume/upload", formData, {
        headers: { 
          "Content-Type": "multipart/form-data",
          "Authorization": `Bearer ${token}`,
        },
      });
      setMatchScore(response.data.matchScore);
    } catch (err) {
      setError(
        err.response?.data?.message ||
          "❌ Failed to upload resume. Please try again."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="upload-card">
      <form
        onSubmit={handleSubmit}
        className="upload-form"
        onDragEnter={handleDrag}
        onDragOver={handleDrag}
        onDragLeave={handleDrag}
        onDrop={handleDrop}
      >
        {/* Job Description */}
        <label htmlFor="job-desc-input" className="upload-label">
          Job Description:
        </label>
        <textarea
          id="job-desc-input"
          value={jobDescription}
          onChange={handleJobDescChange}
          placeholder="Paste or type the job description here"
          autoComplete="off"
          className="job-desc-input"
          rows={5}
          disabled={loading}
        />

        {/* File Upload */}
        <label className="upload-label mt-4">Upload Resume (PDF, DOC, DOCX):</label>
        <div
          className={`upload-box ${dragActive ? "drag-active" : ""} ${
            file ? "file-present" : ""
          }`}
          onClick={() => fileInputRef.current.click()}
        >
          {!file ? (
            <>
              <input
                type="file"
                accept=".pdf,.doc,.docx"
                style={{ display: "none" }}
                onChange={handleFileChange}
                ref={fileInputRef}
                id="resume-upload"
                disabled={loading}
              />
              <div className="upload-zone">
                <FiUploadCloud size={54} className="upload-icon" />
                <div className="upload-zone-text">
                  <strong>Click to select a file</strong> or drag & drop here
                </div>
              </div>
            </>
          ) : (
            <div className="file-name-row">
              <span className="file-name" title={file.name}>
                {file.name}
              </span>
              <button
                onClick={handleRemoveFile}
                title="Remove file"
                className="remove-file-btn"
                type="button"
                disabled={loading}
              >
                <FiXCircle size={24} />
              </button>
            </div>
          )}
        </div>

        {/* Submit Button */}
        <button type="submit" disabled={loading} className="upload-btn">
          <FiUploadCloud size={22} className="btn-icon" />
          {loading ? " Uploading..." : " Upload"}
        </button>
      </form>

      {/* Results & Errors */}
      {matchScore !== null && (
        <div className="score-msg success">✅ Match Score: {matchScore}</div>
      )}
      {error && <div className="error-msg">{error}</div>}
    </div>
  );
};

export default ResumeUpload;
