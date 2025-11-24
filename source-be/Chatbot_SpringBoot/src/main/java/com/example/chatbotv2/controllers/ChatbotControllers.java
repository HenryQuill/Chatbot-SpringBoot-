package com.example.chatbotv2.controllers;

import com.example.chatbotv2.dto.CacheMessageDTO;
import com.example.chatbotv2.dto.ChatRequest;
import com.example.chatbotv2.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:3000") // allow React frontend
public class ChatbotControllers {
    private final ChatService chatService;

    @Autowired
    public ChatbotControllers(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/response")
    public String chat(@RequestBody ChatRequest request) {
        return chatService.handleMessage(request.getUserId(), request.getMessage());
    }

    @GetMapping("/history/{userId}")
    public List<CacheMessageDTO> getChatHistory(@PathVariable Long userId) {
        return chatService.getHistory(userId);
    }

}
