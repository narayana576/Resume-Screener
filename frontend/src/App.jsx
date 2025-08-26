// App.jsx
import { useState, useEffect } from "react";
import { Routes, Route, Navigate, useLocation, useNavigate } from "react-router-dom";

import Navbar from "./components/Navbar";
import WelcomePage from "./components/WelcomePage";
import HomePage from "./components/HomePage";
import AboutPage from "./components/AboutPage";
import ContactPage from "./components/ContactPage";
import ResumeUpload from "./components/ResumeUpload";
import LoginPage from "./components/LoginPage";
import SignupPage from "./components/SignupPage";
import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  const [showWelcome, setShowWelcome] = useState(true);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    setIsAuthenticated(!!token);
  }, []);

  const handleAuthSuccess = () => {
    setIsAuthenticated(true);

    // redirect back to where user wanted to go
    const from = location.state?.from?.pathname;
    if (from) {
      navigate(from, { replace: true });
    } else {
      navigate("/", { replace: true });
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    setIsAuthenticated(false);
    navigate("/", { replace: true });
  };

  if (showWelcome) {
    return <WelcomePage onEnter={() => setShowWelcome(false)} />;
  }

  return (
    <div className="app">
      {/* ✅ use isLoggedIn to match Navbar.jsx */}
      <Navbar isLoggedIn={isAuthenticated} onLogout={handleLogout} />

      <Routes>
        {/* Public routes */}
        <Route path="/" element={<HomePage />} />
        <Route path="/about" element={<AboutPage />} />
        <Route path="/contact" element={<ContactPage />} />

        {/* Auth */}
        <Route
          path="/login"
          element={<LoginPage onLoginSuccess={handleAuthSuccess} />}
        />
        <Route
          path="/signup"
          element={<SignupPage onSignupSuccess={handleAuthSuccess} />}
        />

        {/* Protected */}
        <Route
          path="/resume-upload"
          element={
            <ProtectedRoute>
              {/* ✅ show ResumeUpload, not HomePage */}
              <ResumeUpload />
            </ProtectedRoute>
          }
        />

        {/* Fallback */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </div>
  );
}

export default App;
