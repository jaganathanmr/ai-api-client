package com.aiapiclient.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Error response structure returned by the OpenAI API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenAiError {

    private ErrorDetail error;

    public OpenAiError() {
    }

    public ErrorDetail getError() {
        return error;
    }

    public void setError(ErrorDetail error) {
        this.error = error;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ErrorDetail {

        private String message;
        private String type;
        private String param;
        private String code;

        public ErrorDetail() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
