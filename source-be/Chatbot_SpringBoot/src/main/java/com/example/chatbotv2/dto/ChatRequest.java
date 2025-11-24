package com.example.chatbotv2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChatRequest {
    @NotNull
    private Long userId;

    @NotBlank
    private String message;

    public Long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }
}
