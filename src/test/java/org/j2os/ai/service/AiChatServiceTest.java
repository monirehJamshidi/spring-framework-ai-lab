package org.j2os.ai.service;

import org.j2os.ai.tools.SimpleInterestTools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaChatOptions;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AiChatServiceTest {

    @Mock
    private ChatClient chatClient;

    @Mock
    private ChatClient.ChatClientRequestSpec requestSpec;

    @Mock
    private ChatClient.CallResponseSpec responseSpec;

    @Mock
    private SimpleInterestTools simpleInterestTools;

    private AiChatService aiChatService;

    @BeforeEach
    void setUp() {
        aiChatService = new AiChatService(
                chatClient,
                simpleInterestTools
        );
    }

    @Test
    void shouldReturnAnswerFromChatClient() {
        // Arrange
        String message = "Explain Spring Boot";
        String conversationId = "test-conversation";
        String expectedAnswer = "Spring Boot simplifies Spring applications.";

        when(chatClient.prompt()).thenReturn(requestSpec);
        when(requestSpec.options(any(OllamaChatOptions.Builder.class)))
                .thenReturn(requestSpec);
        when(requestSpec.system(anyString())).thenReturn(requestSpec);
        when(requestSpec.user(message)).thenReturn(requestSpec);
        when(requestSpec.tools(any(Object[].class)))
                .thenReturn(requestSpec);
        when(requestSpec.advisors(any(Consumer.class)))
                .thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(responseSpec);
        when(responseSpec.content()).thenReturn(expectedAnswer);

        // Act
        String actualAnswer = aiChatService.chat(
                message,
                conversationId
        );

        // Assert
        assertEquals(expectedAnswer, actualAnswer);
        verify(chatClient).prompt();
        verify(requestSpec).user(message);
        verify(requestSpec).call();
        verify(responseSpec).content();
    }
}