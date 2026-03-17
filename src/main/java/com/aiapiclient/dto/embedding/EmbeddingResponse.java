package com.aiapiclient.dto.embedding;

import com.aiapiclient.dto.common.Usage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Response from the OpenAI Embeddings API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbeddingResponse {

    private String object;
    private String model;
    private List<EmbeddingData> data;
    private Usage usage;

    public EmbeddingResponse() {
    }

    /**
     * Convenience method to get the first embedding vector.
     *
     * @return the first embedding vector, or null if no data exists
     */
    public List<Double> getFirstEmbedding() {
        if (data != null && !data.isEmpty()) {
            return data.get(0).getEmbedding();
        }
        return null;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<EmbeddingData> getData() {
        return data;
    }

    public void setData(List<EmbeddingData> data) {
        this.data = data;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }
}
