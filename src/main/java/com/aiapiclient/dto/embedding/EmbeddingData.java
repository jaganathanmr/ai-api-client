package com.aiapiclient.dto.embedding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Represents a single embedding vector in an embedding response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbeddingData {

    private String object;
    private int index;
    private List<Double> embedding;

    public EmbeddingData() {
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Double> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<Double> embedding) {
        this.embedding = embedding;
    }
}
