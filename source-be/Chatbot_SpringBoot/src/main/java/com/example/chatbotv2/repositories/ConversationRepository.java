package com.example.chatbotv2.repositories;

import com.example.chatbotv2.models.Conversation;
import com.example.chatbotv2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation,Long> {

    // Find all conversations by user id
    List<Conversation> findByUserId(Long user);
}
