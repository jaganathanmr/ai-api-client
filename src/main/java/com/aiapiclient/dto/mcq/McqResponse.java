package com.aiapiclient.dto.mcq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Response containing multiple choice questions generated from PDF content.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class McqResponse {

    private String sourceFileName;
    private int totalQuestions;
    private String difficulty;
    private List<McqQuestion> questions;

    public McqResponse() {
    }

    public McqResponse(String sourceFileName, int totalQuestions, String difficulty,
                       List<McqQuestion> questions) {
        this.sourceFileName = sourceFileName;
        this.totalQuestions = totalQuestions;
        this.difficulty = difficulty;
        this.questions = questions;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<McqQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<McqQuestion> questions) {
        this.questions = questions;
    }
}
