package com.hackathon.jcs.modules.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.jcs.global.config.CustomWindowMemoryStore;
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
        String npcResponse = chatClient.prompt()
                .advisors(MessageChatMemoryAdvisor.builder(windowMemoryStore.getMemory("npc" + npcId)).build())
                .system(SYSTEM_PROMPT.getPromptMessage() + npc.getInitPrompt())
                .user(chatRequest.message())
                .call()
                .content();
        log.info(npcResponse);
        return null;
    }
}
