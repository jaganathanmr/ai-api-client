package com.aiapiclient.dto.mcq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Optional parameters for MCQ generation from a PDF.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class McqRequest {

    /**
     * Number of questions to generate. Defaults to 5.
     */
    private int numberOfQuestions = 5;

    /**
     * Difficulty level: "easy", "medium", or "hard". Defaults to "medium".
     */
    private String difficulty = "medium";

    /**
     * Optional topic focus within the PDF content.
     */
    private String topic;

    public McqRequest() {
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
