package com.aiapiclient.service;

import com.aiapiclient.client.OpenAiClient;
import com.aiapiclient.dto.chat.ChatCompletionRequest;
import com.aiapiclient.dto.chat.ChatCompletionResponse;
import com.aiapiclient.dto.topic.TopicInfo;
import com.aiapiclient.dto.topic.TopicResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Service that extracts topics and sub-topics from PDF content using OpenAI.
 */
@Service
public class TopicExtractorService {

    private static final Logger log = LoggerFactory.getLogger(TopicExtractorService.class);

    private final PdfExtractorService pdfExtractorService;
    private final OpenAiClient openAiClient;
    private final ObjectMapper objectMapper;

    public TopicExtractorService(PdfExtractorService pdfExtractorService,
                                 OpenAiClient openAiClient,
                                 ObjectMapper objectMapper) {
        this.pdfExtractorService = pdfExtractorService;
        this.openAiClient = openAiClient;
        this.objectMapper = objectMapper;
    }

    /**
     * Extract topics and sub-topics from an uploaded PDF file.
     *
     * @param file the uploaded PDF file
     * @return response containing the extracted topics and sub-topics
     * @throws IOException if the PDF cannot be read
     */
    public TopicResponse extractTopics(MultipartFile file) throws IOException {
        String pdfText = pdfExtractorService.extractText(file);

        log.info("Extracting topics from PDF: {}", file.getOriginalFilename());

        String systemPrompt = buildSystemPrompt();
        String userPrompt = buildUserPrompt(pdfText);

        ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
                .addSystemMessage(systemPrompt)
                .addUserMessage(userPrompt)
                .temperature(0.3)
                .build();

        ChatCompletionResponse chatResponse = openAiClient.chatCompletion(chatRequest);
        String content = chatResponse.getFirstContent();

        List<TopicInfo> topics = parseTopics(content);

        TopicResponse response = new TopicResponse();
        response.setSourceFileName(file.getOriginalFilename());
        response.setTotalTopics(topics.size());
        response.setTopics(topics);

        return response;
    }

    private String buildSystemPrompt() {
        return """
                You are an expert content analyst who identifies topics and sub-topics from text.
                You MUST respond with ONLY a valid JSON array — no markdown, no explanation, no extra text.

                Each element in the array must have this exact structure:
                {
                  "topic": "Main Topic Name",
                  "description": "Brief description of what this topic covers",
                  "subTopics": [
                    {
                      "name": "Sub-Topic Name",
                      "description": "Brief description of this sub-topic"
                    }
                  ]
                }

                Rules:
                - Identify all major topics covered in the text
                - For each topic, identify relevant sub-topics
                - Keep descriptions concise (1-2 sentences)
                - Order topics by their prominence in the text
                - Each topic should have at least 1 sub-topic
                """;
    }

    private String buildUserPrompt(String pdfText) {
        return "Analyze the following text and extract all topics and sub-topics covered in it.\n\n" +
                "Text content:\n\n" + pdfText;
    }

    private List<TopicInfo> parseTopics(String content) {
        try {
            String json = content.trim();
            if (json.startsWith("```json")) {
                json = json.substring(7);
            } else if (json.startsWith("```")) {
                json = json.substring(3);
            }
            if (json.endsWith("```")) {
                json = json.substring(0, json.length() - 3);
            }
            json = json.trim();

            return objectMapper.readValue(json, new TypeReference<List<TopicInfo>>() {});
        } catch (Exception e) {
            log.error("Failed to parse topic response from OpenAI: {}", content, e);
            throw new RuntimeException("Failed to parse topic response from OpenAI. " +
                    "The AI response was not in the expected format.", e);
        }
    }
}
