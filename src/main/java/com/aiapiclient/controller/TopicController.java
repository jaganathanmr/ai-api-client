package com.aiapiclient.controller;

import com.aiapiclient.dto.topic.TopicResponse;
import com.aiapiclient.exception.OpenAiApiException;
import com.aiapiclient.service.TopicExtractorService;
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
 * REST controller for extracting topics and sub-topics from PDF files.
 *
 * <p>Upload a PDF and receive AI-identified topics and sub-topics.
 *
 * <p>Example usage with curl:
 * <pre>
 * curl -X POST http://localhost:8080/api/topic/extract \
 *   -F "file=@document.pdf"
 * </pre>
 */
@RestController
@RequestMapping("/api/topic")
public class TopicController {

    private static final Logger log = LoggerFactory.getLogger(TopicController.class);

    private final TopicExtractorService topicExtractorService;

    public TopicController(TopicExtractorService topicExtractorService) {
        this.topicExtractorService = topicExtractorService;
    }

    /**
     * Extract topics and sub-topics from an uploaded PDF file.
     *
     * @param file the PDF file to process
     * @return the extracted topics and sub-topics
     */
    @PostMapping(value = "/extract", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> extractTopics(@RequestParam("file") MultipartFile file) {

        log.info("Received topic extraction request: file={}", file.getOriginalFilename());

        try {
            TopicResponse response = topicExtractorService.extractTopics(file);
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
