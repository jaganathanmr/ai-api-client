package com.aiapiclient;

import com.aiapiclient.config.OpenAiProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OpenAiPropertiesTest {

    @Test
    void defaultValuesShouldBeSet() {
        OpenAiProperties properties = new OpenAiProperties();

        assertEquals("https://api.openai.com", properties.getBaseUrl());
        assertEquals("gpt-4o", properties.getDefaultModel());
        assertEquals("text-embedding-3-small", properties.getDefaultEmbeddingModel());
        assertEquals(10000, properties.getConnectTimeout());
        assertEquals(30000, properties.getReadTimeout());
        assertNull(properties.getApiKey());
    }

    @Test
    void propertiesShouldBeConfigurable() {
        OpenAiProperties properties = new OpenAiProperties();

        properties.setApiKey("sk-test-key");
        properties.setBaseUrl("https://custom.api.com");
        properties.setDefaultModel("gpt-3.5-turbo");
        properties.setDefaultEmbeddingModel("text-embedding-ada-002");
        properties.setConnectTimeout(5000);
        properties.setReadTimeout(15000);

        assertEquals("sk-test-key", properties.getApiKey());
        assertEquals("https://custom.api.com", properties.getBaseUrl());
        assertEquals("gpt-3.5-turbo", properties.getDefaultModel());
        assertEquals("text-embedding-ada-002", properties.getDefaultEmbeddingModel());
        assertEquals(5000, properties.getConnectTimeout());
        assertEquals(15000, properties.getReadTimeout());
    }
}
