import React, { useState } from "react";
import { register } from "../services/authService";
import { Link } from "react-router-dom";
import '../assets/css/auth.css';

const RegisterPage = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await register(username, email, password);
      setMessage("Registration successful! You can now login.");
    } catch (err) {
      console.error("Login error:", err);
      setError("Invalid username or password");
    }
  };

  return (
    <div>
      <h2>Register</h2>
      <div id="id01" className="form-container">     
        <form className="form-content" onSubmit={handleSubmit}>
          <div className="container">
            <label className="username-label" htmlFor="username-input">Username</label>
            <input id="username-input" type="text" placeholder="Enter username" value={username} onChange={(e) => setUsername(e.target.value)} required/>

            <label className="email-label" htmlFor="email-input">Email</label>
            <input id="email-input" type="text" placeholder="Enter email" value={email} onChange={(e) => setEmail(e.target.value)} required/>
            
            <label className="password-label" htmlFor="password-input">Password</label>
            <input id="password-input" type="password" placeholder="Enter password" value={password}onChange={(e) => setPassword(e.target.value)} required/>
              
            <button type="submit">Register</button>
          </div>
          <div className="container" style={{backgroundColor:"#f1f1f1"}}>
            <button type="button" onClick="document.getElementById('id01').style.display='none'" className="cancelBtn">Cancel</button>
            <span>Already had an account ? <Link to="/">Login here</Link></span>
          </div>
        </form>
        {message && <p>{message}</p>}
      </div>
    </div>
  );
};

export default RegisterPage;