package com.hackathon.jcs.modules.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.jcs.global.config.CustomWindowMemoryStore;
import com.hackathon.jcs.modules.chat.dto.AiRequest;
import com.hackathon.jcs.modules.chat.dto.NpcChatBetweenRequest;
import com.hackathon.jcs.modules.chat.dto.NpcChatRequest;
import com.hackathon.jcs.modules.chat.dto.NpcChatResponse;
import com.hackathon.jcs.modules.chat.exception.ChatException;
import com.hackathon.jcs.modules.chat.exception.ChatExceptionType;
import com.hackathon.jcs.modules.npc.model.Npc;
import com.hackathon.jcs.modules.npc.model.SystemPrompt;
import com.hackathon.jcs.modules.npc.repository.NpcRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.stereotype.Service;

import static com.hackathon.jcs.modules.npc.model.SystemPrompt.SYSTEM_PROMPT;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatClient chatClient;
    private final CustomWindowMemoryStore windowMemoryStore = new CustomWindowMemoryStore(100);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NpcRepository npcRepository;

    public NpcChatResponse sendMessage(Long npcId, @Valid NpcChatRequest chatRequest) {
        Npc npc = npcRepository.findById(npcId)
                .orElseThrow(() -> new ChatException(ChatExceptionType.NOT_EXISTING_NPC_ID));
        AiRequest request = AiRequest.builder()
                .mood(npc.getMood())
                .valence(npc.getValence())
                .valenceStrength(npc.getValence_strength())
                .say(chatRequest.message())
                .build();
        String message = null;
        try {
            message = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new ChatException(ChatExceptionType.MESSAGE_CONVERSION_ERROR);
        }

        String npcResponse = chatClient.prompt()
                .advisors(MessageChatMemoryAdvisor.builder(windowMemoryStore.getMemory("npc" + npcId)).build())
                .system(SYSTEM_PROMPT.getPromptMessage())
                .user(message)
                .call()
                .content();

        log.info(npcResponse);
        NpcChatResponse response = null;
        try {
            response = objectMapper.readValue(npcResponse, NpcChatResponse.class);
        } catch (JsonProcessingException e) {
            throw new ChatException(ChatExceptionType.MESSAGE_CONVERSION_ERROR);
        }
        npc.updateFromAiResponse(response);
        return response;
    }

    public NpcChatResponse sendMessageBetween(@Valid NpcChatBetweenRequest chatRequest) {
        Npc fromNpc = npcRepository.findById(chatRequest.from())
                .orElseThrow(() -> new ChatException(ChatExceptionType.NOT_EXISTING_NPC_ID));
        Npc toNpc = npcRepository.findById(chatRequest.to())
                .orElseThrow(() -> new ChatException(ChatExceptionType.NOT_EXISTING_NPC_ID));

        String npcResponse = chatClient.prompt()
                .advisors(MessageChatMemoryAdvisor.builder(windowMemoryStore.getMemory("npc" + toNpc.getId())).build())
                .system(SYSTEM_PROMPT.getPromptMessage())
                .user(chatRequest.message())
                .call()
                .content();
        log.info(npcResponse);
        return null;
    }
}
