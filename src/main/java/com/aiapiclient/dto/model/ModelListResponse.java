package com.aiapiclient.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Response from the OpenAI List Models API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelListResponse {

    private String object;
    private List<Model> data;

    public ModelListResponse() {
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Model> getData() {
        return data;
    }

    public void setData(List<Model> data) {
        this.data = data;
    }
}
