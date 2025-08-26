import React from "react";
import "./AboutPage.css";

const AboutPage = () => {
  return (
    <div className="aboutpage-container">
      {/* 🔹 Hero Section */}
      <section className="hero-section">
        <h1 className="hero-title">About Resume Screener</h1>
        <p className="hero-subtitle">
          Empowering smarter hiring decisions with innovative technology and seamless experience.
        </p>
      </section>

      {/* 🔹 Content Section */}
      <section className="content-section">
        <article className="glass-card about-app">
          <h2 className="section-title">About the Application</h2>
          <p>
            Resume Screener is a cutting-edge recruitment tool designed to simplify and optimize
            the hiring process. Leveraging advanced algorithms and AI-driven analytics, our platform
            helps companies swiftly identify the best candidates for their job openings. By
            automating resume evaluation and match scoring, Resume Screener eliminates human bias
            and reduces the time-to-hire dramatically.
          </p>
          <p>
            Our intuitive interface allows recruiters to easily upload candidate resumes and job
            descriptions, instantly generating a comprehensive match score. This score guides
            recruiters by highlighting top candidates, enabling them to focus their attention where
            it matters the most.
          </p>
          <p>
            With Resume Screener, every hiring decision is backed by deep insights and data-driven
            precision, making recruitment more efficient, fair, and scalable.
          </p>
        </article>

        <article className="glass-card uses-advantages">
          <h2 className="section-title">Uses & Advantages</h2>
          <ul>
            <li>
              <strong>Efficiency Boost:</strong> Automates the resume screening process, reducing
              hours of manual review to mere minutes.
            </li>
            <li>
              <strong>Objective Hiring:</strong> Utilizes unbiased algorithms ensuring fair candidate
              assessment free of human prejudices.
            </li>
            <li>
              <strong>Data-Driven Insights:</strong> Provides detailed match scores and analytics
              that identify candidates who truly fit the job criteria.
            </li>
            <li>
              <strong>Scalable Solution:</strong> Handles bulk resume uploads seamlessly, ideal for
              companies of all sizes, from startups to enterprises.
            </li>
            <li>
              <strong>Seamless Integration:</strong> Easy to adopt and integrates smoothly with
              existing recruitment workflows.
            </li>
            <li>
              <strong>Interactive Dashboard:</strong> Visual tools that allow tracking and comparing
              candidates effectively.
            </li>
            <li>
              <strong>Cost Savings:</strong> Decreases hiring costs by speeding up the selection
              process and enhancing recruitment quality.
            </li>
          </ul>
          <p>
            Resume Screener empowers organizations to make the right hires faster — a crucial
            advantage in today’s competitive job market.
          </p>
        </article>

        <article className="glass-card tech-stack">
          <h2 className="section-title">Technology Stack</h2>
          <p>
            Our application is built on a robust and scalable modern technology stack ensuring
            speed, reliability, and extensibility:
          </p>
          <ul>
            <li>
              <strong>Backend:</strong> Java Spring Boot - provides a secure, high-performance REST
              API powering core logic and integrations.
            </li>
            <li>
              <strong>Database:</strong> MongoDB - flexible NoSQL document database storing resumes,
              job descriptions, and scoring data effectively.
            </li>
            <li>
              <strong>Frontend:</strong> React (with Vite) - delivers a lightning-fast, highly
              responsive user interface with reusable components and smooth microinteractions.
            </li>
            <li>
              <strong>Design:</strong> Advanced CSS with Glassmorphism and Neumorphism styles for an
              elegant, modern look and feel.
            </li>
            <li>
              <strong>Deployment & DevOps:</strong> Docker containers enable portability and easy
              scaling; CI/CD pipelines automate builds and tests.
            </li>
            <li>
              <strong>Authentication:</strong> JWT tokens secure user sessions ensuring safe access
              control and data privacy.
            </li>
            <li>
              <strong>API Communication:</strong> Axios used for efficient HTTP communication
              between frontend and backend services.
            </li>
          </ul>
          <p>
            This combination allows Resume Screener to provide a fast, secure, and delightful
            experience for recruiters and candidates alike. The modular architecture ensures
            continuous improvements and easy integration with future technologies.
          </p>
        </article>
      </section>
    </div>
  );
};

export default AboutPage;
