package com.hackathon.jcs.modules.chat.dto;

import jakarta.validation.constraints.NotEmpty;

public record NpcChatRequest(
        @NotEmpty
        String message
) {
}
