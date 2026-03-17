package com.aiapiclient;

import com.aiapiclient.dto.chat.ChatCompletionRequest;
import com.aiapiclient.dto.chat.ChatMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ChatCompletionRequestTest {

    @Test
    void builderShouldCreateRequestWithMessages() {
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-4o")
                .addSystemMessage("You are helpful.")
                .addUserMessage("Hello!")
                .temperature(0.7)
                .maxTokens(100)
                .build();

        assertEquals("gpt-4o", request.getModel());
        assertNotNull(request.getMessages());
        assertEquals(2, request.getMessages().size());
        assertEquals("system", request.getMessages().get(0).getRole());
        assertEquals("You are helpful.", request.getMessages().get(0).getContent());
        assertEquals("user", request.getMessages().get(1).getRole());
        assertEquals("Hello!", request.getMessages().get(1).getContent());
        assertEquals(0.7, request.getTemperature());
        assertEquals(100, request.getMaxTokens());
    }

    @Test
    void chatMessageFactoryMethodsShouldWork() {
        ChatMessage system = ChatMessage.system("sys");
        ChatMessage user = ChatMessage.user("usr");
        ChatMessage assistant = ChatMessage.assistant("asst");

        assertEquals("system", system.getRole());
        assertEquals("sys", system.getContent());
        assertEquals("user", user.getRole());
        assertEquals("usr", user.getContent());
        assertEquals("assistant", assistant.getRole());
        assertEquals("asst", assistant.getContent());
    }
}
