import { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "./Auth.css";

function SignupPage({ onSignupSuccess }) {
  const [form, setForm] = useState({
    name: "",
    phone: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const location = useLocation();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setError("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    // Client-side validation
    if (!/^[0-9]{10}$/.test(form.phone)) {
      setError("Phone number must be 10 digits.");
      setLoading(false);
      return;
    }
    if (form.password !== form.confirmPassword) {
      setError("Passwords do not match.");
      setLoading(false);
      return;
    }

      try {
    const response = await fetch("/api/auth/signup", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        name: form.name.trim(),
        phone: form.phone.trim(),
        email: form.email.trim(),
        password: form.password,
      }),
    });

    const contentType = response.headers.get("content-type");
    let data;
    if (contentType && contentType.includes("application/json")) {
      data = await response.json();
    } else {
      data = await response.text();
    }

    if (!response.ok) {
      setError(data.message || data || "Signup failed!");
      setLoading(false);
      return;
    }

    if (data.token) {
      localStorage.setItem("token", data.token);
      onSignupSuccess();
      const redirectTo = location.state?.from || "/";
      navigate(redirectTo, { replace: true });
    } else {
     
      navigate("/login");
    }

  } catch (err) {
    setError("Server unreachable. Please try again.");
    setLoading(false);
  }

  };

  return (
    <section className="auth-container fade-in">
      <form className="auth-form glass-card" onSubmit={handleSubmit} noValidate>
        <h2 className="auth-title">Create Your Account</h2>

        <label htmlFor="name" className="input-label">Full Name</label>
        <input
          id="name"
          name="name"
          className="input-field"
          type="text"
          placeholder="Your full name"
          value={form.name}
          onChange={handleChange}
          required
          autoFocus
        />

        <label htmlFor="phone" className="input-label">Phone Number</label>
        <input
          id="phone"
          name="phone"
          className="input-field"
          type="tel"
          placeholder="10-digit phone number"
          value={form.phone}
          onChange={handleChange}
          required
          pattern="[0-9]{10}"
          inputMode="numeric"
        />

        <label htmlFor="email" className="input-label">Email</label>
        <input
          id="email"
          name="email"
          className="input-field"
          type="email"
          placeholder="Your email"
          value={form.email}
          onChange={handleChange}
          required
        />

        <label htmlFor="password" className="input-label">Password</label>
        <input
          id="password"
          name="password"
          className="input-field"
          type="password"
          placeholder="Enter a strong password"
          value={form.password}
          onChange={handleChange}
          required
          minLength={6}
        />

        <label htmlFor="confirmPassword" className="input-label">Confirm Password</label>
        <input
          id="confirmPassword"
          name="confirmPassword"
          className="input-field"
          type="password"
          placeholder="Confirm your password"
          value={form.confirmPassword}
          onChange={handleChange}
          required
          minLength={6}
        />

        {error && <div className="error-message">{error}</div>}

        <button className="submit-btn" type="submit" disabled={loading}>
          {loading ? "Signing up..." : "Sign Up"}
        </button>

        <div className="form-bottom-text">
          <span>
            Already have an account?{" "}
            <button
              type="button"
              className="form-link-btn"
              onClick={() => navigate("/login")}
            >Jump right in here!</button>
          </span>
        </div>
      </form>
    </section>
  );
}

export default SignupPage;
