package com.example.chatbotv2.services;

import com.example.chatbotv2.dto.CacheMessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service
public class ChatCacheService {
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public ChatCacheService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
    }

    // caching with userId instead of conversationId
    private String getRedisKey(Long userId) {
        return "chat:session:" + userId;
    }

    // saving messages to Redis (list)
    public void addMessage(Long userId, String sender, String messageText) {
        try {
            // create an object to save cache messages
            CacheMessageDTO msg= new CacheMessageDTO(System.currentTimeMillis(), messageText,sender);
            String json= objectMapper.writeValueAsString(msg);

            // newest message at the end of the list
            redisTemplate.opsForList().rightPush(getRedisKey(userId), json);

            // save the last 50 messages
            redisTemplate.opsForList().trim(getRedisKey(userId), -50, -1);

            // expiration time
            redisTemplate.expire(getRedisKey(userId), 7, TimeUnit.DAYS);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error serializing chat message", e);
        }
    }

    public List<CacheMessageDTO> getChatHistory(Long userId) {
        List<String> jsonList = redisTemplate.opsForList().range(getRedisKey(userId), 0, -1);

        // List.of() returns immutable empty list (same return type)
        if (jsonList == null) return List.of();

        return jsonList.stream()
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, CacheMessageDTO.class);
                    } catch (JsonProcessingException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull) // Filter null elements if parse fails to avoid client side NullPointerException
                .toList();
    }
}

