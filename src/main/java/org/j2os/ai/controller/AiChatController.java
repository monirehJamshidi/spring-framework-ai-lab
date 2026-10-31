package org.j2os.ai.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.j2os.ai.dto.AiChatRequest;
import org.j2os.ai.dto.AiChatResponse;
import org.j2os.ai.service.AiChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiChatController {

    private final AiChatService aiChatService;

    @PostMapping("/chat")
    public AiChatResponse chat(
            @Valid @RequestBody AiChatRequest request
            ) {

        String answer = aiChatService.chat(request.message(), request.conversationId());

        return new AiChatResponse(answer);

    }
}
