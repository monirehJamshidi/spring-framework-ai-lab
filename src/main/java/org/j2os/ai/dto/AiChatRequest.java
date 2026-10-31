package org.j2os.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AiChatRequest(

        @NotBlank
        @Size(max = 100)
        String conversationId,

        @NotBlank
        @Size(max = 2000)
        String message
) {
}
