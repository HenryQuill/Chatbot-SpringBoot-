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

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");
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
    setPassword("");
    setError("");
  };

  return (
    <div>
      <h2>Register</h2>
      <div id="id01" className="form-container">     
        <form className="form-content" onSubmit={handleSubmit}>
          <div className="container">
            <label className="username-label" htmlFor="username-input">Username</label>
            <input id="username-input" type="text" placeholder="Enter username" value={username} onChange={(e) => setUsername(e.target.value)} required minLength="3" maxLength="20"/>

            <label className="email-label" htmlFor="email-input">Email</label>
            <input id="email-input" type="text" placeholder="Enter email" value={email} onChange={(e) => setEmail(e.target.value)} required maxLength="50"/>
            
            <label className="password-label" htmlFor="password-input">Password</label>
            <input id="password-input" type="password" placeholder="Enter password" value={password}onChange={(e) => setPassword(e.target.value)} required minLength="6" maxLength="40"/> 
            <button type="submit">Register</button>
          </div>
          
          {error && <p style={{color: 'red', textAlign: 'center', marginTop: '10px'}}>{error}</p>}
          {message && <p style={{color: 'green', textAlign: 'center', marginTop: '10px'}}>{message}</p>}

          <div className="container" style={{backgroundColor:"#f1f1f1"}}></div>
          <div className="container" style={{backgroundColor:"#f1f1f1"}}>
            <button type="button" onClick={handleCancel} className="cancelBtn">Cancel</button>
            <span>Already had an account ? <Link to="/">Login here</Link></span>
          </div>
        </form>
        {message && <p>{message}</p>}
      </div>
    </div>
  );
};

export default RegisterPage;