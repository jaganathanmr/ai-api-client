package com.aiapiclient.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service for extracting text content from PDF files.
 */
@Service
public class PdfExtractorService {

    private static final Logger log = LoggerFactory.getLogger(PdfExtractorService.class);
    private static final int MAX_TEXT_LENGTH = 50000;

    /**
     * Extract text from an uploaded PDF file.
     *
     * @param file the uploaded PDF file
     * @return the extracted text content
     * @throws IOException if the file cannot be read or is not a valid PDF
     */
    public String extractText(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("PDF file is required");
        }

        String filename = file.getOriginalFilename();
        if (filename != null && !filename.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("File must be a PDF");
        }

        log.debug("Extracting text from PDF: {}", filename);

        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            if (text == null || text.isBlank()) {
                throw new IllegalArgumentException(
                        "Could not extract text from PDF. The file may be scanned/image-based.");
            }

            // Truncate if too long to fit in OpenAI context
            if (text.length() > MAX_TEXT_LENGTH) {
                log.warn("PDF text truncated from {} to {} characters", text.length(), MAX_TEXT_LENGTH);
                text = text.substring(0, MAX_TEXT_LENGTH);
            }

            log.debug("Extracted {} characters from PDF", text.length());
            return text;
        }
    }
}
