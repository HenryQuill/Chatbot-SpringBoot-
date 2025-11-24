import React, { useState } from "react";
import { login } from "../services/authService";
import { Link } from "react-router-dom";
import '../assets/css/auth.css';

const LoginPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const user = await login(username, password); // returns response.data
      if (user && user.accessToken) {
        localStorage.setItem("user", JSON.stringify(user)); // ensure it's stored
        window.location.href = "/chat"; // then redirect
      } else {
        setError("Login failed: no token returned");
      }
    } catch (err) {
      console.error("Login error:", err);
      setError("Invalid username or password");
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <div id="id01" className="form-container">
        <form className="form-content" onSubmit={handleSubmit}>
          <div className="container">
            <label className="username-label" htmlFor="username-input">Username</label>
            <input id="username-input" type="text" placeholder="Enter username" value={username} onChange={(e) => setUsername(e.target.value)} required/>

            <label className="password-label" htmlFor="password-input">Password</label>
            <input id="password-input" type="password" placeholder="Enter password" value={password}onChange={(e) => setPassword(e.target.value)} required/>
            
            <button type="submit">Login</button>
            <label><input type="checkbox" name="remember" /> Remember me</label>
          </div>
          {error && <p className="error-message">{error}</p>}
          <div className="container" style={{backgroundColor:"#f1f1f1"}}>
            <button type="button" onClick="document.getElementById('id01').style.display='none'" className="cancelBtn">Cancel</button>
            <span>Dont have an account ? <Link to="/register">Register here</Link></span>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;