package com.aiapiclient.dto.chat;

import com.aiapiclient.dto.common.Usage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Response from the OpenAI Chat Completions API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatCompletionResponse {

    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChatChoice> choices;
    private Usage usage;

    public ChatCompletionResponse() {
    }

    /**
     * Convenience method to get the content of the first choice's message.
     *
     * @return the content of the first choice, or null if no choices exist
     */
    public String getFirstContent() {
        if (choices != null && !choices.isEmpty()) {
            ChatMessage message = choices.get(0).getMessage();
            return message != null ? message.getContent() : null;
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChatChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<ChatChoice> choices) {
        this.choices = choices;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }
}
