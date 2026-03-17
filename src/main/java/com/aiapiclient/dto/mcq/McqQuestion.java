package com.aiapiclient.dto.mcq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Represents a single multiple choice question generated from PDF content.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class McqQuestion {

    private int questionNumber;
    private String question;
    private List<String> options;
    private String correctAnswer;
    private String explanation;

    public McqQuestion() {
    }

    public McqQuestion(int questionNumber, String question, List<String> options,
                       String correctAnswer, String explanation) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
