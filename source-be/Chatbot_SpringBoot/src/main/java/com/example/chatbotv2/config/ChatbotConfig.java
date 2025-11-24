package com.example.chatbotv2.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatbotConfig {
    @Bean
    public ChatClient chatClient( ChatClient.Builder chatlientBuilder ){

        return chatlientBuilder.build();
    }
}
