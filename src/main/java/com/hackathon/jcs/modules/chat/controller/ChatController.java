package com.hackathon.jcs.modules.chat.controller;

import com.hackathon.jcs.modules.chat.dto.NpcChatBetweenRequest;
import com.hackathon.jcs.modules.chat.dto.NpcChatRequest;
import com.hackathon.jcs.modules.chat.dto.NpcChatResponse;
import com.hackathon.jcs.modules.chat.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/npc/{npcId}")
    public NpcChatResponse sendMessage(@PathVariable("npcId") Long npcId, @Valid @RequestBody NpcChatRequest chatRequest) {
        return chatService.sendMessage(npcId, chatRequest);
    }

    @PostMapping("/npc/between")
    public NpcChatResponse sendMessageBetween(@Valid @RequestBody NpcChatBetweenRequest chatRequest) {
        return chatService.sendMessageBetween(chatRequest);
    }
}
