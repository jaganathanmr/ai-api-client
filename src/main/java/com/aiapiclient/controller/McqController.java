package com.aiapiclient.controller;

import com.aiapiclient.dto.mcq.McqRequest;
import com.aiapiclient.dto.mcq.McqResponse;
import com.aiapiclient.exception.OpenAiApiException;
import com.aiapiclient.service.McqGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * REST controller for generating multiple choice questions from PDF files.
 *
 * <p>Upload a PDF and receive AI-generated MCQs based on the content.
 *
 * <p>Example usage with curl:
 * <pre>
 * curl -X POST http://localhost:8080/api/mcq/generate \
 *   -F "file=@document.pdf" \
 *   -F "numberOfQuestions=5" \
 *   -F "difficulty=medium"
 * </pre>
 */
@RestController
@RequestMapping("/api/mcq")
public class McqController {

    private static final Logger log = LoggerFactory.getLogger(McqController.class);

    private final McqGeneratorService mcqGeneratorService;

    public McqController(McqGeneratorService mcqGeneratorService) {
        this.mcqGeneratorService = mcqGeneratorService;
    }

    /**
     * Generate multiple choice questions from an uploaded PDF file.
     *
     * @param file              the PDF file to process
     * @param numberOfQuestions number of questions to generate (default: 5)
     * @param difficulty        difficulty level: easy, medium, hard (default: medium)
     * @param topic             optional topic focus within the PDF content
     * @return the generated MCQs
     */
    @PostMapping(value = "/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> generateMcq(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "numberOfQuestions", defaultValue = "5") int numberOfQuestions,
            @RequestParam(value = "difficulty", defaultValue = "medium") String difficulty,
            @RequestParam(value = "topic", required = false) String topic) {

        log.info("Received MCQ generation request: file={}, questions={}, difficulty={}, topic={}",
                file.getOriginalFilename(), numberOfQuestions, difficulty, topic);

        McqRequest request = new McqRequest();
        request.setNumberOfQuestions(numberOfQuestions);
        request.setDifficulty(difficulty);
        request.setTopic(topic);

        try {
            McqResponse response = mcqGeneratorService.generateFromPdf(file, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (OpenAiApiException e) {
            log.error("OpenAI API error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("error", "OpenAI API error: " + e.getMessage()));
        } catch (IOException e) {
            log.error("Failed to read PDF file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to read PDF file: " + e.getMessage()));
        }
    }
}
