package com.hackathon.jcs.modules.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.jcs.global.config.CustomWindowMemoryStore;
import com.hackathon.jcs.modules.chat.dto.NpcChatRequest;
import com.hackathon.jcs.modules.chat.dto.NpcChatResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatClient chatClient;
    private final CustomWindowMemoryStore windowMemoryStore = new CustomWindowMemoryStore(100);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NpcChatResponse sendMessage(Long npcId, @Valid NpcChatRequest chatRequest) {
        String npcResponse = chatClient.prompt()
                .advisors(MessageChatMemoryAdvisor.builder(windowMemoryStore.getMemory("npc" + npcId)).build())
                .system("시스템 프롬프트")
                .user(chatRequest.message())
                .call()
                .content();
        log.info(npcResponse);
        return null;
    }
}
