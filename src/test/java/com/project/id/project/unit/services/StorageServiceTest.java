package com.project.id.project.unit.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.project.id.project.application.services.s3storage.StorageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageServiceTest {

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private StorageService storageService;

    private final String bucketName = "test-bucket";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        storageService.BUCKET_NAME = bucketName;
    }

    @Test
    void upload_ShouldUploadFileAndReturnFilename() {
        // Arrange
        byte[] data = "test data".getBytes();
        ArgumentCaptor<PutObjectRequest> captor = ArgumentCaptor.forClass(PutObjectRequest.class);

        // Act
        String filename = storageService.upload(data);

        // Assert
        verify(amazonS3).putObject(captor.capture());
        PutObjectRequest request = captor.getValue();
        assertEquals(bucketName, request.getBucketName());
        assertTrue(request.getKey().startsWith("file"));
        assertNotNull(request.getInputStream());
        assertNotNull(filename);
    }

    @Test
    void uploadWithExistingFilename_ShouldUploadFileWithProvidedFilename() {
        // Arrange
        String filename = "test-file.pdf";
        byte[] data = "test data".getBytes();
        ArgumentCaptor<PutObjectRequest> captor = ArgumentCaptor.forClass(PutObjectRequest.class);

        // Act
        String result = storageService.uploadWithExistingFilename(data, filename);

        // Assert
        verify(amazonS3).putObject(captor.capture());
        PutObjectRequest request = captor.getValue();
        assertEquals(bucketName, request.getBucketName());
        assertEquals(filename, request.getKey());
        assertEquals(result, filename);
    }

    @Test
    void upload_MultipartFile_ShouldUploadFileAndReturnFilename() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream("test data".getBytes()));
        when(file.getSize()).thenReturn(9L);

        // Act
        String filename = storageService.upload(file);

        // Assert
        assertNotNull(filename);
        verify(amazonS3).putObject(any(PutObjectRequest.class));
    }

    @Test
    void delete_ShouldDeleteFile() {
        // Arrange
        String filename = "test-file.pdf";

        // Act
        storageService.delete(filename);

        // Assert
        verify(amazonS3).deleteObject(bucketName, filename);
    }

    @Test
    void get_ShouldReturnFileData() throws Exception {
        // Arrange
        String filename = "test-file.pdf";
        byte[] fileContent = "test data".getBytes();
        S3Object s3Object = mock(S3Object.class);
        when(s3Object.getObjectContent()).thenReturn(new S3ObjectInputStream(new ByteArrayInputStream(fileContent), null));
        when(amazonS3.getObject(bucketName, filename)).thenReturn(s3Object);

        // Act
        byte[] result = storageService.get(filename);

        // Assert
        assertNotNull(result);
        assertArrayEquals(fileContent, result);
    }

    @Test
    void get_ShouldReturnNullWhenFileNotFound() {
        // Arrange
        String filename = "nonexistent-file.pdf";
        when(amazonS3.getObject(bucketName, filename)).thenThrow(new RuntimeException("File not found"));

        // Act
        byte[] result = storageService.get(filename);

        // Assert
        assertNull(result);
    }
}
