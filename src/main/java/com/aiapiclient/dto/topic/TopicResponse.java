package com.aiapiclient.dto.topic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Response containing topics and sub-topics extracted from a PDF.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicResponse {

    private String sourceFileName;
    private int totalTopics;
    private List<TopicInfo> topics;

    public TopicResponse() {
    }

    public TopicResponse(String sourceFileName, int totalTopics, List<TopicInfo> topics) {
        this.sourceFileName = sourceFileName;
        this.totalTopics = totalTopics;
        this.topics = topics;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public int getTotalTopics() {
        return totalTopics;
    }

    public void setTotalTopics(int totalTopics) {
        this.totalTopics = totalTopics;
    }

    public List<TopicInfo> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicInfo> topics) {
        this.topics = topics;
    }
}
