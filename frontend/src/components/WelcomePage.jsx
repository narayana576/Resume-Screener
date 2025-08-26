import { motion, AnimatePresence } from "framer-motion";
import { useState } from "react";
import "./WelcomePage.css";
import logo from "../assets/logo.png"

const typingContainer = {
  hidden: { opacity: 0 },
  visible: {
    opacity: 1,
    transition: {
      staggerChildren: 0.12,
      delayChildren: 0.3,
    },
  },
  exit: {
    opacity: 0,
    transition: { duration: 0.8, ease: "easeInOut" },
  },
};

const letterAnimation = {
  hidden: { opacity: 0, y: 20, filter: "blur(3px)" },
  visible: {
    opacity: 1,
    y: 0,
    filter: "blur(0)",
    transition: {
      type: "spring",
      stiffness: 300,
      damping: 20,
    },
  },
};

function WelcomePage({ onEnter }) {
  const phrase = "Resume Screener";
  const [exiting, setExiting] = useState(false);

  const handleClick = () => {
    setExiting(true);
    setTimeout(() => {
      onEnter();
    }, 900); // Exit animation duration
  };

  return (
    <>
      <div className="creative-background" />
      <AnimatePresence>
        {!exiting && (
          <motion.div
            key="welcome"
            className="welcome-container"
            initial="hidden"
            animate="visible"
            exit="exit"
            variants={typingContainer}
            onClick={handleClick}
          >
            {/* Logo added here */}
            <img
              src={logo}
              alt="App Logo"
              className="welcome-logo"
              draggable={false}
            />

            <h1 className="welcome-title" aria-label={phrase}>
              {phrase.split("").map((char, i) => (
                <motion.span
                  key={`char-${i}`}
                  variants={letterAnimation}
                  className="welcome-letter"
                >
                  {char}
                </motion.span>
              ))}
            </h1>
            <motion.p
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: phrase.length * 0.12 + 0.3, duration: 1 }}
              className="welcome-subtitle"
            >
              Empowering smarter hiring decisions
            </motion.p>
          </motion.div>
        )}
      </AnimatePresence>
    </>
  );
}

export default WelcomePage;
