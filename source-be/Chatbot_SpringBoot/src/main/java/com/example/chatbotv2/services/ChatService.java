package com.example.chatbotv2.services;

import com.example.chatbotv2.dto.CacheMessageDTO;
import com.example.chatbotv2.models.Conversation;
import com.example.chatbotv2.models.Message;
import com.example.chatbotv2.models.User;
import com.example.chatbotv2.repositories.ConversationRepository;
import com.example.chatbotv2.repositories.MessageRepository;
import com.example.chatbotv2.repositories.UserRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    private final ConversationRepository conversationRepo;
    private final MessageRepository messageRepo;
    private final UserRepository userRepo;
    private final ChatClient chatClient;

    private final ChatCacheService chatCacheService;

    public ChatService(ChatClient chatClient,
                       ConversationRepository conversationRepo,
                       MessageRepository messageRepo,
                       UserRepository userRepo,
                       ChatCacheService chatCacheService) {
        this.conversationRepo= conversationRepo;
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

        // create new convo
        Conversation conversation = new Conversation();
        conversation.setUser(user);
        conversation.setStartedAt(LocalDateTime.now());
        conversation = conversationRepo.save(conversation);

        // create and save user messages
        Message userMessage = new Message();
        userMessage.setConversation(conversation);
        userMessage.setUser(user);
        userMessage.setSender("user");
        userMessage.setMessageText(userInput);
        userMessage.setCreatedAt(LocalDateTime.now());
        messageRepo.save(userMessage);
        chatCacheService.addMessage(userId,"user",userInput);

        // create and save bot responses
        String botReply = chatClient.prompt(userInput).call().content();
        Message botMessage = new Message();
        botMessage.setConversation(conversation);
        botMessage.setUser(null);
        botMessage.setSender("ai");
        botMessage.setMessageText(botReply);
        botMessage.setCreatedAt(LocalDateTime.now());
        messageRepo.save(botMessage);
        chatCacheService.addMessage(userId,"ai",botReply);

        conversationRepo.save(conversation);

        return botReply;
    }

    // Hàm lấy lịch sử cho Controller gọi
    public List<CacheMessageDTO> getHistory(Long userId) {
        // for now
        return chatCacheService.getChatHistory(userId);
        // later: if redis doesnt have the message -> query straight to DB
    }

}
