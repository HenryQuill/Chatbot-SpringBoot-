package com.example.chatbotv2.controllers;

import com.example.chatbotv2.services.KnowledgeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final KnowledgeService knowledgeService;
    public TestController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }
    @PostMapping("/load-knowledge")
    public String loadKnowledge(@RequestParam String content, @RequestParam String source) {
        knowledgeService.addKnowledge(content, source);
        return "Knowledge loaded successfully! Content: " + content;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }


}