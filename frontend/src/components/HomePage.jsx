// HomePage.jsx
import { useNavigate } from "react-router-dom";
import "./HomePage.css";

function HomePage() {
  const navigate = useNavigate();

  const handleShowUpload = () => {
    const token = localStorage.getItem("token");
    if (token) {
      navigate("/resume-upload");
    } else {
      navigate("/login", { state: { from: { pathname: "/resume-upload" } } });
    }
  };

  return (
    <div className="home-container p-6">
      <h2 className="gradient-title">Welcome to Resume Screener</h2>
      <p className="home-welcome-text">
        Welcome to the ultimate platform for smarter, faster, and unbiased
        hiring decisions. You can securely upload your resume for analysis
        and match scoring against targeted job descriptions.
      </p>
      <button className="auth-button" onClick={handleShowUpload}>
        Check Your Resume
      </button>
    </div>
  );
}

export default HomePage;
