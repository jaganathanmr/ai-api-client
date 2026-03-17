package com.aiapiclient.dto.embedding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Request body for the OpenAI Embeddings API.
 *
 * <p>Usage example:
 * <pre>
 * EmbeddingRequest request = EmbeddingRequest.of("text-embedding-3-small", "Hello, world!");
 * </pre>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmbeddingRequest {

    private String model;
    private Object input;

    @JsonProperty("encoding_format")
    private String encodingFormat;

    private Integer dimensions;
    private String user;

    public EmbeddingRequest() {
    }

    /**
     * Create an embedding request for a single text input.
     */
    public static EmbeddingRequest of(String model, String input) {
        EmbeddingRequest request = new EmbeddingRequest();
        request.setModel(model);
        request.setInput(input);
        return request;
    }

    /**
     * Create an embedding request for multiple text inputs.
     */
    public static EmbeddingRequest of(String model, List<String> input) {
        EmbeddingRequest request = new EmbeddingRequest();
        request.setModel(model);
        request.setInput(input);
        return request;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Object getInput() {
        return input;
    }

    public void setInput(Object input) {
        this.input = input;
    }

    public String getEncodingFormat() {
        return encodingFormat;
    }

    public void setEncodingFormat(String encodingFormat) {
        this.encodingFormat = encodingFormat;
    }

    public Integer getDimensions() {
        return dimensions;
    }

    public void setDimensions(Integer dimensions) {
        this.dimensions = dimensions;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
