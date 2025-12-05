package com.example.chatbotv2.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KnowledgeService {
    private final VectorStore vectorStore;
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeService.class);
    public KnowledgeService (VectorStore vectorStore){
        this.vectorStore=vectorStore;
    }

    public void addKnowledge(String content, String source){
        Document document = new Document(content, Map.of(
                "created_at", System.currentTimeMillis(),
                "source", source
        ));

        // convert and store the vectors
        vectorStore.add(List.of(document));
        logger.info("Input knowlegde recieved from: {}", source);
    }
}
