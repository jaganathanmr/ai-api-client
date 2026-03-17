package com.aiapiclient.dto.topic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a sub-topic within a main topic.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubTopic {

    private String name;
    private String description;

    public SubTopic() {
    }

    public SubTopic(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
