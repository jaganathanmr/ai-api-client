package com.aiapiclient.exception;

import com.aiapiclient.dto.common.OpenAiError;

/**
 * Exception thrown when the OpenAI API returns an error response.
 */
public class OpenAiApiException extends RuntimeException {

    private final int statusCode;
    private final OpenAiError error;

    public OpenAiApiException(int statusCode, OpenAiError error) {
        super(buildMessage(statusCode, error));
        this.statusCode = statusCode;
        this.error = error;
    }

    public OpenAiApiException(int statusCode, String message) {
        super("OpenAI API error (HTTP " + statusCode + "): " + message);
        this.statusCode = statusCode;
        this.error = null;
    }

    private static String buildMessage(int statusCode, OpenAiError error) {
        if (error != null && error.getError() != null) {
            return "OpenAI API error (HTTP " + statusCode + "): " + error.getError().getMessage();
        }
        return "OpenAI API error (HTTP " + statusCode + ")";
    }

    public int getStatusCode() {
        return statusCode;
    }

    public OpenAiError getError() {
        return error;
    }

    /**
     * Returns the error type from the OpenAI error response, if available.
     */
    public String getErrorType() {
        if (error != null && error.getError() != null) {
            return error.getError().getType();
        }
        return null;
    }

    /**
     * Returns the error code from the OpenAI error response, if available.
     */
    public String getErrorCode() {
        if (error != null && error.getError() != null) {
            return error.getError().getCode();
        }
        return null;
    }
}
