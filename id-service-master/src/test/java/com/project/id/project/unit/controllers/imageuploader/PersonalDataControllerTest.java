package com.project.id.project.unit.controllers.imageuploader;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.multipart.MultipartFile;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.controllers.addresses.AdditionalAddressController;
import com.project.id.project.application.controllers.personal.PersonalDataController;
import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.application.services.upload.ImageUploaderController;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.documents.entities.BirthCertificate;
import com.project.id.project.core.documents.entities.CompulsoryMedicalInsurance;
import com.project.id.project.core.documents.entities.DrivingLicense;
import com.project.id.project.core.documents.entities.ForeignPassport;
import com.project.id.project.core.documents.entities.InsuranceNumberOfIndividualPersonalAccount;
import com.project.id.project.core.documents.entities.Passport;
import com.project.id.project.core.documents.entities.TaxPayerIdentificationNumber;
import com.project.id.project.core.documents.entities.VoluntaryHealthInsurance;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.PersonalDataRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;


@ExtendWith(MockitoExtension.class)
public class PersonalDataControllerTest {

    @Mock
    private StorageService storageService;

    @Mock
    private EntityPersonalDataRepository personalDataRepository;

    @InjectMocks
    private ImageUploaderController controller;

    private final String USERNAME = "testUser";

    @BeforeEach
    void setUpSecurityContext() {
        Jwt jwt = Mockito.mock(Jwt.class);
        when(jwt.getClaimAsString("preferred_username")).thenReturn(USERNAME);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void personalDataUploadImage_ShouldUploadAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String filename = "personal_data_avatar.jpg";
        when(storageService.upload(file)).thenReturn(filename);
        PersonalData personalData = createMockPersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.personalDataUploadImage(file);

        // Assert
        verify(storageService).upload(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo uploaded and assigned successfully", response.getBody());
    }

    @Test
    void personalDataUploadImage_ShouldReturnInternalServerError_WhenExceptionOccurs() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenThrow(new RuntimeException("Upload failed"));

        // Act
        ResponseEntity<String> response = controller.personalDataUploadImage(file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to upload file: Upload failed", response.getBody());
    }

    @Test
    void personalDataUpdateImage_ShouldUpdateAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String newFilename = "new_personal_data_avatar.jpg";
        when(storageService.upload(file)).thenReturn(newFilename);
        PersonalData personalData = createMockPersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.personalDataUpdateImage(file);

        // Assert
        verify(storageService).upload(file);
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo updated successfully", response.getBody());
    }

    @Test
    void personalDataUpdateImage_ShouldReturnInternalServerError_WhenExceptionOccurs() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);

        // Act
        ResponseEntity<String> response = controller.personalDataUpdateImage(file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to update photo: User not found", response.getBody());
    }

    @Test
    void personalDataDeleteImage_ShouldDeleteAndReturnNoContent() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.personalDataDeleteImage();

        // Assert
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void personalDataDeleteImage_ShouldReturnInternalServerError_WhenExceptionOccurs() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));
        doThrow(new RuntimeException("Delete failed")).when(storageService).delete(anyString());

        // Act
        ResponseEntity<Void> response = controller.personalDataDeleteImage();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void personalDataDeleteImage_ShouldReturnBadRequest_WhenNoAvatar() throws Exception {
        // Arrange
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.personalDataDeleteImage();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void personalDataDeleteImage_ShouldReturnInternalError_WhenUserNotFound() throws Exception {
        // Arrange
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = controller.personalDataDeleteImage();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // Вспомогательные методы для создания mock-объектов
    private PersonalData createMockPersonalData() {
        PersonalData personalData = new PersonalData();
        personalData.setAvatar("old_avatar.jpg");
        personalData.setId(new Id(UUID.randomUUID()));
        return personalData;
    }
}
