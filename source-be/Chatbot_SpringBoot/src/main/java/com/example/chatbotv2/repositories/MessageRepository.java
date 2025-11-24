package com.example.chatbotv2.repositories;

import com.example.chatbotv2.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversation_ConversationId(Long conversationId);
}
