import React, { useState } from "react";
import { register } from "../services/authService";
import { Link } from "react-router-dom";
import '../assets/css/auth.css';

const RegisterPage = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [validationErrors, setValidationErrors] = useState({});

  const isValidEmail = (email) => {
    return /\S+@\S+\.\S+/.test(email);
  };

  const validateForm = () => {
    const errors = {};

    // Validate Username
    if (!username.trim()) {
        errors.username = "Please enter username.";
    } else if (username.length < 3 || username.length > 20) {
        errors.username = "Username must be between 3 and 20 characters.";
    }

    // Validate Email
    if (!email.trim()) {
        errors.email = "Please enter Email.";
    } else if (!isValidEmail(email)) {
        errors.email = "Invalid Email.";
    }

    // Validate Password
    if (!password) {
        errors.password = "Please enter password.";
    } else if (password.length < 6 || password.length > 40) {
        errors.password = "Password must be between 6 and 40 characters.";
    }

    return errors;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");

    const errors = validateForm();
    if (Object.keys(errors).length > 0) {
        setValidationErrors(errors); 
        return; 
    }
    setValidationErrors({});

    try {
      const response = await register(username, email, password);
      if (response.data?.message?.startsWith("Error")) {
        setError(response.data.message);
      } else {
        setMessage("Registration successful! You can now login.");
      }
    
    } catch (err) {
      console.error("Registration error:", err);
      // Handle Spring Boot Validation Errors (400 Bad Request)
      if (err.response?.status === 400) {
        const data = err.response.data;
        if (data.errors) {
          const errorMsg = data.errors.map(e => e.defaultMessage).join(", ");
          setError(errorMsg);
        } else if (data.message) {
          setError(data.message);
        } else {
          setError("Validation failed. Please check your inputs.");
        }
      } else {
        setError("An error occurred. Please try again later.");
      }
    }
  };

  const handleCancel = () => {
    setUsername("");
    setEmail("");
    setPassword("");
    setMessage("");
    setError("");
    setValidationErrors({});
  };

  return (
    <div>
      <h2>Register</h2>
      <div className="form-container">     
        <form className="form-content" onSubmit={handleSubmit} noValidate>
          <div className="container">
            {/* username */}
            <label className="username-label" htmlFor="username-input">Username</label>
            <input id="username-input" 
              type="username" 
              placeholder="Enter username" 
              value={username} 
              className={validationErrors.username ? "input-error" : ""} 
              onChange={(e) => setUsername(e.target.value)} 
              required minLength="3" maxLength="20"
            />
            {validationErrors.username && <span className="field-error">{validationErrors.username}</span>}

            {/* email */}
            <label className="email-label" htmlFor="email-input">Email</label>
            <input id="email-input" 
              type="email" 
              placeholder="Enter email" 
              value={email} 
              className={validationErrors.email ? "input-error" : ""}
              onChange={(e) => setEmail(e.target.value)} 
              required maxLength="50"
            />
            {validationErrors.email && <span className="field-error">{validationErrors.email}</span>}

            {/* password */}
            <label className="password-label" htmlFor="password-input">Password</label>
            <input id="password-input" 
              type="password" 
              placeholder="Enter password" 
              value={password}
              className={validationErrors.password ? "input-error" : ""}
              onChange={(e) => setPassword(e.target.value)} 
              required minLength="6" maxLength="40"
            /> 
            {validationErrors.password && <span className="field-error">{validationErrors.password}</span>}

            <button type="submit">Register</button>
          </div>
          
          {error && <p style={{color: 'red', textAlign: 'center', marginTop: '10px'}}>{error}</p>}
          {message && <p style={{color: 'green', textAlign: 'center', marginTop: '10px'}}>{message}</p>}
          
          <div className="form-footer-container">
            <button type="button" onClick={handleCancel} className="cancelBtn">Cancel</button>
            <span>Already had an account ? <Link to="/">Login here</Link></span>
          </div>
        </form>
      </div>
    </div>
  );
};

export default RegisterPage;