import React, { useState } from "react";
import { login } from "../services/authService";
import { Link, useNavigate } from "react-router-dom";
import '../assets/css/auth.css';

const LoginPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleCancel = () => {
    setUsername("");
    setPassword("");
    setError("");
  };
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const user = await login(username, password); // returns response.data
      if (user?.accessToken) {
        localStorage.setItem("user", JSON.stringify(user)); 
        navigate("/chat"); 
      } else {
        setError("Login failed: please check your credentials");
      }
    } catch (err) {
      console.error("Login error:", err);
      if (err.response?.status === 401) {
        setError("Invalid username or password.");
      } else if (err.response?.status === 400) {
        setError("Missing username or password.");
      } else {
        setError("Server error. Please try again later.");
      }
    }
  };

  

  return (
    <div>
      <h2>Login</h2>
      <div id="id01" className="form-container">
        <form className="form-content" onSubmit={handleSubmit}>
          <div className="container">
            <label className="username-label" htmlFor="username-input">Username</label>
            <input 
              id="username-input" 
              type="text" 
              placeholder="Enter username" 
              value={username} 
              onChange={(e) => setUsername(e.target.value)} 
              required
            />

            <label className="password-label" htmlFor="password-input">Password</label>
            <input 
              id="password-input" 
              type="password" 
              placeholder="Enter password" 
              value={password}onChange={(e) => setPassword(e.target.value)} 
              required
            />
            
            <button type="submit">Login</button>
          </div>
          {error && <p style={{color: 'red', textAlign: 'center', marginTop: '10px'}}>{error}</p>}
          <div className="container" style={{backgroundColor:"#f1f1f1"}}>
            <button type="button" onClick={handleCancel} className="cancelBtn">Cancel</button>
            <span>Dont have an account ? <Link to="/register">Register here</Link></span>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;