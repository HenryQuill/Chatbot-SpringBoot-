package com.example.chatbotv2.dto;

public class CacheMessageDTO {
    private Long id;
    private String text;
    private String sender;

    public CacheMessageDTO() {}

    public CacheMessageDTO(Long id, String text, String sender) {
        this.id = id;
        this.text = text;
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}