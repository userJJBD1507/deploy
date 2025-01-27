package com.project.id.project.application.services.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

@Component
public class PdfGenerator {
    public byte[] generatePdfFromDto(Object dto) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(dto);
                document.add(new Paragraph(field.getName() + ": " + (value != null ? value.toString() : "null")));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        document.close();

        return outputStream.toByteArray();
    }
}
