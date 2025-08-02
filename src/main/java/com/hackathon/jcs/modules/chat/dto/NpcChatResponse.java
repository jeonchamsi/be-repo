package com.hackathon.jcs.modules.chat.dto;

import lombok.Builder;

@Builder
public record NpcChatResponse(
    String mood,
    String valence,
    int valence_strength,
    String reply
) {
}
