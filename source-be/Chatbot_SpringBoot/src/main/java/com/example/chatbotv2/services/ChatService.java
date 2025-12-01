package com.example.chatbotv2.services;

import com.example.chatbotv2.dto.CacheMessageDTO;
import com.example.chatbotv2.models.Message;
import com.example.chatbotv2.models.User;
import com.example.chatbotv2.repositories.MessageRepository;
import com.example.chatbotv2.repositories.UserRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final MessageRepository messageRepo;
    private final UserRepository userRepo;
    private final ChatClient chatClient;
    private final ChatCacheService chatCacheService;

    public ChatService(ChatClient chatClient,
                       MessageRepository messageRepo,
                       UserRepository userRepo,
                       ChatCacheService chatCacheService) {
        this.messageRepo=messageRepo;
        this.userRepo=userRepo;
        this.chatClient = chatClient;
        this.chatCacheService=chatCacheService;
    }
    public String botResponseMessage(String userInput) {
        return chatClient.prompt(userInput).call().content();
    }

    public String handleMessage(Long userId, String userInput){
        // fetch current user
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // create and save user messages
        Message userMessage = new Message(user, "user", userInput);

        messageRepo.save(userMessage);
        chatCacheService.addMessage(userId,"user",userInput);

        // create and save bot responses
        String botReply = chatClient.prompt(userInput).call().content();
        Message botMessage = new Message(user, "ai", botReply);
        messageRepo.save(botMessage);
        chatCacheService.addMessage(userId,"ai",botReply);

        return botReply;
    }

    // get chat history
    public List<CacheMessageDTO> getHistory(Long userId) {
        // get history from Redis first
        List<CacheMessageDTO> cachedHistory = chatCacheService.getChatHistory(userId);
        if (!cachedHistory.isEmpty()) {
            return cachedHistory;
        }

        // if redis doesn't have the message -> query straight to DB
        List<Message> dbMessages = messageRepo.findByUser_UseridOrderByCreatedAtAsc(userId);
        return dbMessages.stream()
                .map(msg -> new CacheMessageDTO(
                        msg.getMessageId(), // Dùng tạm ID làm timestamp hoặc convert createdAt
                        msg.getMessageText(),
                        msg.getSender()
                ))
                .toList();
    }
}
