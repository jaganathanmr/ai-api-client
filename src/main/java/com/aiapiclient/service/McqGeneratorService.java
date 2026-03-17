package com.aiapiclient.service;

import com.aiapiclient.client.OpenAiClient;
import com.aiapiclient.dto.chat.ChatCompletionRequest;
import com.aiapiclient.dto.chat.ChatCompletionResponse;
import com.aiapiclient.dto.mcq.McqQuestion;
import com.aiapiclient.dto.mcq.McqRequest;
import com.aiapiclient.dto.mcq.McqResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Service that generates multiple choice questions from PDF content using OpenAI.
 */
@Service
public class McqGeneratorService {

    private static final Logger log = LoggerFactory.getLogger(McqGeneratorService.class);

    private final PdfExtractorService pdfExtractorService;
    private final OpenAiClient openAiClient;
    private final ObjectMapper objectMapper;

    public McqGeneratorService(PdfExtractorService pdfExtractorService,
                               OpenAiClient openAiClient,
                               ObjectMapper objectMapper) {
        this.pdfExtractorService = pdfExtractorService;
        this.openAiClient = openAiClient;
        this.objectMapper = objectMapper;
    }

    /**
     * Generate multiple choice questions from an uploaded PDF file.
     *
     * @param file    the uploaded PDF file
     * @param request optional parameters (number of questions, difficulty, topic)
     * @return response containing the generated MCQs
     * @throws IOException if the PDF cannot be read
     */
    public McqResponse generateFromPdf(MultipartFile file, McqRequest request) throws IOException {
        String pdfText = pdfExtractorService.extractText(file);

        log.info("Generating {} {} MCQs from PDF: {}",
                request.getNumberOfQuestions(),
                request.getDifficulty(),
                file.getOriginalFilename());

        String systemPrompt = buildSystemPrompt(request);
        String userPrompt = buildUserPrompt(pdfText, request);

        ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
                .addSystemMessage(systemPrompt)
                .addUserMessage(userPrompt)
                .temperature(0.7)
                .build();

        ChatCompletionResponse chatResponse = openAiClient.chatCompletion(chatRequest);
        String content = chatResponse.getFirstContent();

        List<McqQuestion> questions = parseQuestions(content);

        McqResponse response = new McqResponse();
        response.setSourceFileName(file.getOriginalFilename());
        response.setTotalQuestions(questions.size());
        response.setDifficulty(request.getDifficulty());
        response.setQuestions(questions);

        return response;
    }

    private String buildSystemPrompt(McqRequest request) {
        return """
                You are an expert educator who creates high-quality multiple choice questions.
                You MUST respond with ONLY a valid JSON array — no markdown, no explanation, no extra text.

                Each element in the array must have this exact structure:
                {
                  "questionNumber": 1,
                  "question": "The question text",
                  "options": ["A) Option 1", "B) Option 2", "C) Option 3", "D) Option 4"],
                  "correctAnswer": "A) Option 1",
                  "explanation": "Brief explanation of why this is correct"
                }

                Rules:
                - Each question must have exactly 4 options labeled A), B), C), D)
                - The correctAnswer must exactly match one of the options
                - Questions should be at %s difficulty level
                - Provide a brief explanation for each correct answer
                """.formatted(request.getDifficulty());
    }

    private String buildUserPrompt(String pdfText, McqRequest request) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate exactly ")
                .append(request.getNumberOfQuestions())
                .append(" multiple choice questions from the following text content.\n\n");

        if (request.getTopic() != null && !request.getTopic().isBlank()) {
            prompt.append("Focus specifically on the topic: ")
                    .append(request.getTopic())
                    .append("\n\n");
        }

        prompt.append("Text content:\n\n")
                .append(pdfText);

        return prompt.toString();
    }

    private List<McqQuestion> parseQuestions(String content) {
        try {
            // Strip markdown code fences if present
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

            return objectMapper.readValue(json, new TypeReference<List<McqQuestion>>() {});
        } catch (Exception e) {
            log.error("Failed to parse MCQ response from OpenAI: {}", content, e);
            throw new RuntimeException("Failed to parse MCQ response from OpenAI. " +
                    "The AI response was not in the expected format.", e);
        }
    }
}
