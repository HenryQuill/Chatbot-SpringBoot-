import React, { useState, useEffect } from 'react';
import { authAxios, getCurrentUser ,logout } from '../services/authService';
import { FaPaperPlane, FaSignOutAlt } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import '../assets/css/chatbot.css';

const Chatbot = () => {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState('');
    const [loading, setLoading] = useState(false)
    const [authUser, setAuthUser] = useState(null);
    const navigate = useNavigate();
    const suggestions = [
        "What is AI?",
        "How does machine learning work?",
        "What are the applications of deep learning?",
        "Explain natural language processing.",
        "What is computer vision?"
    ];

    // Load logged-in user on component mount
    useEffect(() => {
        const user = getCurrentUser();
        if (user) {
            setAuthUser(user);
            fetchChatHistory(user.userId);
        }else {
            navigate('/'); // redirect to login if no user
        }
    }, [navigate]);

    const handleLogout = () => {
        logout();
        navigate("/");
    };

    const fetchChatHistory = async (userId) => {
        try {
            const response = await authAxios.get(`http://localhost:8080/api/chat/history/${userId}`);
            if (response.data) {
                setMessages(response.data);
            }
        } catch (error) {
            console.error('Error fetching chat history:', error);
        }
    };

    const handleSend = async (messageText) => {
        // send suggestions or input field
        const messageToSend= typeof messageText === 'string' ? messageText : input;
        
        // empty input => send nothing
        if (messageToSend.trim() === "") return;

        const newMessage = { id: Date.now(), text: messageToSend, sender: 'user' };
        setMessages(prev => [...prev, newMessage]);

        setInput(''); // clear input field
        setLoading(true);

        try {
            const response = await authAxios.post(
                "http://localhost:8080/api/chat/response",
                {
                    userId: authUser.userId,   
                    message: messageToSend
                }
            );

            const aiMessage = { id: Date.now() + 1, text: response.data, sender: 'ai' };
            setMessages(prev => [...prev, aiMessage]);
        } catch (error) {
            console.error('Error fetching AI response:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        setInput(e.target.value);
    };

    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            handleSend();
        }
    };
    
    return (
        <div className="chatbot-container">
            <div className="chat-header">
                <img src="/images/logo.png" alt="Chatbot Logo" className="chat-logo" />
                <div className="breadcrumb">CHATBOT</div>

                <button onClick={handleLogout} 
                    style={{
                        backgroundColor: '#ff4d4d', 
                        width: 'auto', 
                        padding: '8px 15px',
                        display: 'flex',
                        gap: '8px',
                        alignItems: 'center',
                        fontSize: '14px'
                    }}
                >
                    <FaSignOutAlt/> Logout
                </button>
            </div>
            
            <div className="chatbox">
                {/* show suggestions if no chat history found */}
                <div className="suggestions-container">
                    <div className="message-container ai" style={{ marginBottom: '20px' }}>
                        <img 
                            src="/images/chatbot.jpg" 
                            alt="AI avatar" 
                            className="avatar" 
                        />
                        <div className="message ai">
                            Hello im your personal AI asistant.<br/>
                            How can I help you today ?
                        </div>
                    </div>

                    <div className="suggestions-grid">
                        {suggestions.map((suggestion) => (
                            <button 
                                key={suggestion}
                                className="suggestion-chip"
                                onClick={() => handleSend(suggestion)}
                            >
                                {suggestion}
                            </button>
                        ))}
                    </div>
                </div>

                {/* if chat history found then show it instead of suggestions */}
                {messages.map((message) => (
                    <div key={message.id} className={`message-container ${message.sender === 'user' ? 'user' : 'ai'}`}>
                        <img
                            src={message.sender === 'user' ? '/images/user.jpg' : '/images/chatbot.jpg'}
                            alt={`${message.sender} avatar`}
                            className="avatar"
                        />
                        <div className={`message ${message.sender === 'user' ? 'user' : 'ai'}`}>
                            {message.text}
                        </div>
                    </div>
                ))}
                {loading && (
                    <div className="message-container ai">
                        <img src="/images/chatbot.jpg" alt="AI avatar" className="avatar" />
                        <div className="message ai">...</div>
                    </div>
                )}
            </div>
            
            <div className="input-container">
                <input
                    type="text"
                    value={input}
                    onChange={handleInputChange}
                    onKeyDown={handleKeyPress}
                    placeholder="Type your message..."
                    className="chat-input-field"
                />
                <button onClick={handleSend} className='send-button'>
                    <FaPaperPlane />
                </button>
            </div>
        
        </div>
    );
};

export default Chatbot;