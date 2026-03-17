package com.aiapiclient.dto.topic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Represents a main topic with its sub-topics extracted from PDF content.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicInfo {

    private String topic;
    private String description;
    private List<SubTopic> subTopics;

    public TopicInfo() {
    }

    public TopicInfo(String topic, String description, List<SubTopic> subTopics) {
        this.topic = topic;
        this.description = description;
        this.subTopics = subTopics;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SubTopic> getSubTopics() {
        return subTopics;
    }

    public void setSubTopics(List<SubTopic> subTopics) {
        this.subTopics = subTopics;
    }
}
