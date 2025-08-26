import { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "./Auth.css";

function LoginPage({ onLoginSuccess }) {
  const [form, setForm] = useState({ email: "", password: "" });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const location = useLocation(); // so we know where user came from

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setError("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      const response = await fetch("/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });

      const data = await response.json();

      if (!response.ok) {
        setError(data.message || "Invalid email or password.");
        setLoading(false);
        return;
      }

      localStorage.setItem("token", data.token);
      onLoginSuccess();

      // if user was redirected from Resume, go back there
      const redirectTo = location.state?.from || "/";
      navigate(redirectTo, { replace: true });
    } catch (err) {
      setError("Server unreachable. Please try again.");
      setLoading(false);
    }
  };

  return (
    <section className="auth-container fade-in">
      <form className="auth-form glass-card" onSubmit={handleSubmit} noValidate>
        <h2 className="auth-title">Welcome Back!</h2>

        <label htmlFor="email" className="input-label">Email</label>
        <input
          id="email"
          name="email"
          className="input-field"
          type="email"
          placeholder="Enter your email"
          value={form.email}
          onChange={handleChange}
          required
          autoFocus
        />

        <label htmlFor="password" className="input-label">Password</label>
        <input
          id="password"
          name="password"
          className="input-field"
          type="password"
          placeholder="Enter your password"
          value={form.password}
          onChange={handleChange}
          required
          minLength={6}
        />

        {error && <div className="error-message">{error}</div>}

        <button className="submit-btn" type="submit" disabled={loading}>
          {loading ? "Logging in..." : "Login"}
        </button>

        <div className="form-bottom-text">
          <span>New here? <button
            type="button"
            className="form-link-btn"
            onClick={() => navigate("/signup")}
          >Let's build your career—Sign up now!</button></span>
        </div>
      </form>
    </section>
  );
}

export default LoginPage;
