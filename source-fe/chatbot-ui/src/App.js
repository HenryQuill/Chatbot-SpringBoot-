import React from "react";  
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ChatbotPage from "./pages/ChatbotPage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/chat" element={<ChatbotPage />} />
      </Routes>
    </Router>
  );
}

export default App;
