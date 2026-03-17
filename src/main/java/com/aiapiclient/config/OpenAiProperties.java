package com.aiapiclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the OpenAI API client.
 *
 * <p>Configure these properties in your application.yml or application.properties:
 * <pre>
 * openai:
 *   api-key: sk-your-api-key
 *   base-url: https://api.openai.com
 *   default-model: gpt-4o
 *   connect-timeout: 10000
 *   read-timeout: 30000
 * </pre>
 */
@ConfigurationProperties(prefix = "openai")
public class OpenAiProperties {

    /**
     * The OpenAI API key. Required.
     */
    private String apiKey;

    /**
     * The base URL for the OpenAI API. Defaults to https://api.openai.com
     */
    private String baseUrl = "https://api.openai.com";

    /**
     * The default model to use for chat completions.
     */
    private String defaultModel = "gpt-4o";

    /**
     * The default model to use for embeddings.
     */
    private String defaultEmbeddingModel = "text-embedding-3-small";

    /**
     * Connection timeout in milliseconds. Defaults to 10000 (10 seconds).
     */
    private int connectTimeout = 10000;

    /**
     * Read timeout in milliseconds. Defaults to 30000 (30 seconds).
     */
    private int readTimeout = 30000;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getDefaultModel() {
        return defaultModel;
    }

    public void setDefaultModel(String defaultModel) {
        this.defaultModel = defaultModel;
    }

    public String getDefaultEmbeddingModel() {
        return defaultEmbeddingModel;
    }

    public void setDefaultEmbeddingModel(String defaultEmbeddingModel) {
        this.defaultEmbeddingModel = defaultEmbeddingModel;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
}
