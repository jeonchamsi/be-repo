package com.hackathon.jcs.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class CustomWindowMemoryStore {

    private final int windowSize;
    private final Map<String, ChatMemory> memoryMap = new ConcurrentHashMap<>();

    public ChatMemory getMemory(String id) {
        return memoryMap.computeIfAbsent(id, t ->
                MessageWindowChatMemory.builder()
                        .maxMessages(windowSize)
                        .build()
        );
    }

}
