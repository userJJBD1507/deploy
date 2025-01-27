package com.project.id.project.unit.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.project.id.project.application.services.pdf.PdfGenerator;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PdfGeneratorTest {

    private final PdfGenerator pdfGenerator = new PdfGenerator();

    static class TestDto {
        private String name = "John Doe";
        private int age = 30;
        private String address = null;

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getAddress() {
            return address;
        }
    }

    @Test
    void generatePdfFromDto_ShouldGeneratePdfWithCorrectContent() throws IOException {
        // Arrange
        TestDto dto = new TestDto();

        // Act
        byte[] pdfBytes = pdfGenerator.generatePdfFromDto(dto);

        // Assert
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        // Verify PDF content
        try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(new ByteArrayInputStream(pdfBytes)))) {
            String pageContent = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1));

            assertNotNull(pageContent);
            assertTrue(pageContent.contains("name: John Doe"));
            assertTrue(pageContent.contains("age: 30"));
            assertTrue(pageContent.contains("address: null"));
        }
    }

    @Test
    void generatePdfFromDto_ShouldHandleEmptyDto() throws IOException {
        // Arrange
        Object emptyDto = new Object();

        // Act
        byte[] pdfBytes = pdfGenerator.generatePdfFromDto(emptyDto);

        // Assert
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        // Verify PDF content
        try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(new ByteArrayInputStream(pdfBytes)))) {
            String pageContent = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1));
            assertNotNull(pageContent);
            assertEquals("", pageContent.trim());
        }
    }
}
