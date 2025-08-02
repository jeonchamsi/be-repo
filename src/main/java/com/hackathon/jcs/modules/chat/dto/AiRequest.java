package com.hackathon.jcs.modules.chat.dto;

import lombok.Builder;

@Builder
public record AiRequest(
        String mood,
        int valenceStrength,
        String valence,
        String say
) {
}
