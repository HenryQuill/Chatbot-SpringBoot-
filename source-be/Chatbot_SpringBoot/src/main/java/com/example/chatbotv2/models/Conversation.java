package com.example.chatbotv2.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversationId")
    private long conversationId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userid",nullable = false)
    private User user;

    @Column(name = "startedAt", nullable = false)
    private LocalDateTime startedAt;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages= new ArrayList<>();

    public Conversation(){}

    public Conversation(User user, LocalDateTime startedAt, LocalDateTime endedAt ){
        this.user=user;
        this.startedAt=startedAt;
    }

    public Long getConversationId (){return conversationId;}
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }
}
