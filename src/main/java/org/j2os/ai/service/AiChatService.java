package org.j2os.ai.service;

import org.j2os.ai.tools.SimpleInterestTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.stereotype.Service;

@Service
public class AiChatService {

    private final ChatClient chatClient;
    private final SimpleInterestTools simpleInterestTools;

    public AiChatService(
            ChatClient chatClient,
            SimpleInterestTools simpleInterestTools
    ) {
        this.chatClient = chatClient;
        this.simpleInterestTools = simpleInterestTools;
    }

    public String chat(String message, String conversationId) {
        return chatClient.prompt()
                .options(OllamaChatOptions.builder()
                        .model("qwen3:4b-instruct")
                        .disableThinking()
                        .numPredict(256)
                        .numCtx(1024)
                        .temperature(0.2)
                        )
                .system("""
                        You are a helpful Java and Spring Boot tutor.
                        Explain technical concepts clearly.
                        Use practical examples when useful.
                        If you are uncertain, say so.
                        """
                )
                .user(message)
                .tools(simpleInterestTools)
                .advisors(advisors ->
                        advisors.param(
                                ChatMemory.CONVERSATION_ID,
                                conversationId
                        )

                )
                .call()
                .content();
    }
}
