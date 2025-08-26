import React, { useState } from "react";
import "./ContactPage.css";

const ContactPage = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    message: "",
  });
  const [submitted, setSubmitted] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Add form submission logic here
    setSubmitted(true);
  };

  return (
    <main className="contactpage-container">
      <section className="contact-hero">
        <h1 className="contact-title">Get in Touch</h1>
        <p className="contact-description">
          Have questions or want to know more? Reach out, and we’ll help you build a better hiring process.
        </p>
      </section>

      {!submitted ? (
        <form className="contact-form glass-card" onSubmit={handleSubmit} noValidate>
          <label htmlFor="name" className="input-label">Name</label>
          <input
            id="name"
            name="name"
            className="input-field"
            type="text"
            placeholder="Your full name"
            value={formData.name}
            onChange={handleChange}
            required
          />

          <label htmlFor="email" className="input-label">Email</label>
          <input
            id="email"
            name="email"
            className="input-field"
            type="email"
            placeholder="you@example.com"
            value={formData.email}
            onChange={handleChange}
            required
          />

          <label htmlFor="message" className="input-label">Message</label>
          <textarea
            id="message"
            name="message"
            className="textarea-field"
            placeholder="Write your message here"
            rows={6}
            value={formData.message}
            onChange={handleChange}
            required
          ></textarea>

          <button type="submit" className="submit-btn">
            Send Message
          </button>
        </form>
      ) : (
        <div className="thankyou-message glass-card">
          <h2>Thank you!</h2>
          <p>Your message has been received. We will get back to you soon.</p>
        </div>
      )}

      <section className="contact-details glass-card">
        <h3>Contact Details</h3>
        <p><strong>Phone:</strong> +91 12312 31231</p>
        <p><strong>Email:</strong> support@resumescreener.com</p>
        <p><strong>Address:</strong> Hi-Tech City, Hyderabad, Telangana 500081</p>
        <p><strong>Office Hours:</strong> Mon - Fri, 9:00 AM - 6:00 PM</p>
      </section>
    </main>
  );
};

export default ContactPage;
