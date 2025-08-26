import { NavLink, useLocation, useNavigate } from "react-router-dom";
import { useEffect } from "react";
import "./Navbar.css";
import logo from "../assets/logo.png";

function Navbar({ isLoggedIn, onLogout }) {
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    // Optionally close mobile menu on navigation, etc.
  }, [location]);

  return (
    <nav>
      <div>
        <img src={logo} alt="Logo" />
        <h1>Resume Screener</h1>
      </div>

      <div>
        <NavLink 
          to="/" 
          end 
          className={({ isActive }) => (isActive ? "active" : "")}>
          Home
        </NavLink>
        <NavLink 
          to="/about" 
          className={({ isActive }) => (isActive ? "active" : "")}>
          About
        </NavLink>
        <NavLink 
          to="/contact" 
          className={({ isActive }) => (isActive ? "active" : "")}>
          Contact
        </NavLink>

        {isLoggedIn ? (
          <button className="logout" onClick={onLogout}>
            Logout
          </button>
        ) : (
          <button
            className="login"
            onClick={() => navigate("/login")}
          >
            Login
          </button>
        )}
      </div>
    </nav>
  );
}

export default Navbar;
