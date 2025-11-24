package com.example.chatbotv2.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageId")
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversationId", nullable = false)
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "sender", nullable = false)
    private String sender; // 'user' or 'ai'

    @Column(name = "messageText", columnDefinition = "TEXT", nullable = false)
    private String messageText;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    public Message() {}

    public Message(Conversation conversation, User user, String sender, String messageText) {
        this.conversation = conversation;
        this.user = user;
        this.sender=sender;
        this.messageText = messageText;
        this.createdAt = LocalDateTime.now();
    }

    public Long getMessageId() { return messageId; }
    public void setMessageId(Long messageId) { this.messageId = messageId; }

    public Conversation getConversation() { return conversation; }
    public void setConversation(Conversation conversation) { this.conversation = conversation; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user=user; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender=sender; }

    public String getMessageText() { return messageText; }
    public void setMessageText(String messageText) { this.messageText = messageText; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

