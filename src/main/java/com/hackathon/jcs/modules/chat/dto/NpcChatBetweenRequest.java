package com.hackathon.jcs.modules.chat.dto;

public record NpcChatBetweenRequest(
        Long from,
        Long to,
        String message
) {
}
