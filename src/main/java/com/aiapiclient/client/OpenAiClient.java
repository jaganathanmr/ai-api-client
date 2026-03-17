package com.aiapiclient.client;

import com.aiapiclient.config.OpenAiProperties;
import com.aiapiclient.dto.chat.ChatCompletionRequest;
import com.aiapiclient.dto.chat.ChatCompletionResponse;
import com.aiapiclient.dto.common.OpenAiError;
import com.aiapiclient.dto.embedding.EmbeddingRequest;
import com.aiapiclient.dto.embedding.EmbeddingResponse;
import com.aiapiclient.dto.model.Model;
import com.aiapiclient.dto.model.ModelListResponse;
import com.aiapiclient.exception.OpenAiApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Client for interacting with the OpenAI API.
 *
 * <p>This client provides typed methods for the most commonly used OpenAI endpoints:
 * <ul>
 *   <li>Chat Completions</li>
 *   <li>Embeddings</li>
 *   <li>Models</li>
 * </ul>
 *
 * <p>When used with Spring Boot auto-configuration, this bean is automatically created
 * and configured. Simply inject it into your components:
 * <pre>
 * &#64;Autowired
 * private OpenAiClient openAiClient;
 * </pre>
 */
public class OpenAiClient {

    private static final Logger log = LoggerFactory.getLogger(OpenAiClient.class);

    private static final String CHAT_COMPLETIONS_PATH = "/v1/chat/completions";
    private static final String EMBEDDINGS_PATH = "/v1/embeddings";
    private static final String MODELS_PATH = "/v1/models";

    private final RestClient restClient;
    private final OpenAiProperties properties;

    public OpenAiClient(RestClient restClient, OpenAiProperties properties) {
        this.restClient = restClient;
        this.properties = properties;
    }

    // ---- Chat Completions ----

    /**
     * Send a chat completion request to OpenAI.
     *
     * @param request the chat completion request
     * @return the chat completion response
     * @throws OpenAiApiException if the API returns an error
     */
    public ChatCompletionResponse chatCompletion(ChatCompletionRequest request) {
        if (request.getModel() == null) {
            request.setModel(properties.getDefaultModel());
        }

        log.debug("Sending chat completion request with model: {}", request.getModel());

        return restClient.post()
                .uri(CHAT_COMPLETIONS_PATH)
                .body(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, response) -> {
                    handleErrorResponse(response.getStatusCode().value(), response.getBody());
                })
                .body(ChatCompletionResponse.class);
    }

    /**
     * Convenience method: send a simple user message and get the response content.
     *
     * @param userMessage the user message to send
     * @return the assistant's reply content
     * @throws OpenAiApiException if the API returns an error
     */
    public String chat(String userMessage) {
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .addUserMessage(userMessage)
                .build();

        ChatCompletionResponse response = chatCompletion(request);
        return response.getFirstContent();
    }

    /**
     * Convenience method: send a user message with a system prompt and get the response content.
     *
     * @param systemPrompt the system prompt
     * @param userMessage  the user message
     * @return the assistant's reply content
     * @throws OpenAiApiException if the API returns an error
     */
    public String chat(String systemPrompt, String userMessage) {
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .addSystemMessage(systemPrompt)
                .addUserMessage(userMessage)
                .build();

        ChatCompletionResponse response = chatCompletion(request);
        return response.getFirstContent();
    }

    // ---- Embeddings ----

    /**
     * Create embeddings for the given input.
     *
     * @param request the embedding request
     * @return the embedding response
     * @throws OpenAiApiException if the API returns an error
     */
    public EmbeddingResponse createEmbedding(EmbeddingRequest request) {
        if (request.getModel() == null) {
            request.setModel(properties.getDefaultEmbeddingModel());
        }

        log.debug("Sending embedding request with model: {}", request.getModel());

        return restClient.post()
                .uri(EMBEDDINGS_PATH)
                .body(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, response) -> {
                    handleErrorResponse(response.getStatusCode().value(), response.getBody());
                })
                .body(EmbeddingResponse.class);
    }

    /**
     * Convenience method: create an embedding for a single text input using the default model.
     *
     * @param text the text to embed
     * @return the embedding vector
     * @throws OpenAiApiException if the API returns an error
     */
    public List<Double> embed(String text) {
        EmbeddingRequest request = EmbeddingRequest.of(null, text);
        EmbeddingResponse response = createEmbedding(request);
        return response.getFirstEmbedding();
    }

    // ---- Models ----

    /**
     * List all available models.
     *
     * @return the list of models
     * @throws OpenAiApiException if the API returns an error
     */
    public ModelListResponse listModels() {
        log.debug("Listing available models");

        return restClient.get()
                .uri(MODELS_PATH)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, response) -> {
                    handleErrorResponse(response.getStatusCode().value(), response.getBody());
                })
                .body(ModelListResponse.class);
    }

    /**
     * Retrieve details about a specific model.
     *
     * @param modelId the model ID (e.g., "gpt-4o")
     * @return the model details
     * @throws OpenAiApiException if the API returns an error
     */
    public Model retrieveModel(String modelId) {
        log.debug("Retrieving model: {}", modelId);

        return restClient.get()
                .uri(MODELS_PATH + "/{modelId}", modelId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, response) -> {
                    handleErrorResponse(response.getStatusCode().value(), response.getBody());
                })
                .body(Model.class);
    }

    // ---- Error Handling ----

    private void handleErrorResponse(int statusCode, java.io.InputStream body) {
        OpenAiError error = null;
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            error = mapper.readValue(body, OpenAiError.class);
        } catch (Exception e) {
            log.warn("Failed to parse OpenAI error response body", e);
        }

        if (error != null) {
            throw new OpenAiApiException(statusCode, error);
        } else {
            throw new OpenAiApiException(statusCode, "Unknown error");
        }
    }
}
