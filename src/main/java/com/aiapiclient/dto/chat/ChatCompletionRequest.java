package com.aiapiclient.dto.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Request body for the OpenAI Chat Completions API.
 *
 * <p>Usage example:
 * <pre>
 * ChatCompletionRequest request = ChatCompletionRequest.builder()
 *     .model("gpt-4o")
 *     .addSystemMessage("You are a helpful assistant.")
 *     .addUserMessage("Hello!")
 *     .temperature(0.7)
 *     .build();
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatCompletionRequest {

    private String model;
    private List<ChatMessage> messages;
    private Double temperature;

    @JsonProperty("max_tokens")
    private Integer maxTokens;

    @JsonProperty("top_p")
    private Double topP;

    @JsonProperty("frequency_penalty")
    private Double frequencyPenalty;

    @JsonProperty("presence_penalty")
    private Double presencePenalty;

    private Integer n;
    private Boolean stream;
    private String user;

    public ChatCompletionRequest() {
        this.messages = new ArrayList<>();
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public Double getTopP() {
        return topP;
    }

    public void setTopP(Double topP) {
        this.topP = topP;
    }

    public Double getFrequencyPenalty() {
        return frequencyPenalty;
    }

    public void setFrequencyPenalty(Double frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    public Double getPresencePenalty() {
        return presencePenalty;
    }

    public void setPresencePenalty(Double presencePenalty) {
        this.presencePenalty = presencePenalty;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static class Builder {
        private final ChatCompletionRequest request;

        private Builder() {
            this.request = new ChatCompletionRequest();
        }

        public Builder model(String model) {
            request.setModel(model);
            return this;
        }

        public Builder messages(List<ChatMessage> messages) {
            request.setMessages(new ArrayList<>(messages));
            return this;
        }

        public Builder addMessage(ChatMessage message) {
            request.getMessages().add(message);
            return this;
        }

        public Builder addSystemMessage(String content) {
            request.getMessages().add(ChatMessage.system(content));
            return this;
        }

        public Builder addUserMessage(String content) {
            request.getMessages().add(ChatMessage.user(content));
            return this;
        }

        public Builder addAssistantMessage(String content) {
            request.getMessages().add(ChatMessage.assistant(content));
            return this;
        }

        public Builder temperature(Double temperature) {
            request.setTemperature(temperature);
            return this;
        }

        public Builder maxTokens(Integer maxTokens) {
            request.setMaxTokens(maxTokens);
            return this;
        }

        public Builder topP(Double topP) {
            request.setTopP(topP);
            return this;
        }

        public Builder frequencyPenalty(Double frequencyPenalty) {
            request.setFrequencyPenalty(frequencyPenalty);
            return this;
        }

        public Builder presencePenalty(Double presencePenalty) {
            request.setPresencePenalty(presencePenalty);
            return this;
        }

        public Builder n(Integer n) {
            request.setN(n);
            return this;
        }

        public Builder stream(Boolean stream) {
            request.setStream(stream);
            return this;
        }

        public Builder user(String user) {
            request.setUser(user);
            return this;
        }

        public ChatCompletionRequest build() {
            return request;
        }
    }
}
