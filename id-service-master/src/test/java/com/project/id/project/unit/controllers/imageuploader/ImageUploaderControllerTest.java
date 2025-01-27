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
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;


@ExtendWith(MockitoExtension.class)
public class ImageUploaderControllerTest {

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
    void birthCertificateUploadImage_ShouldUploadAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String filename = "birthCertificate.jpg";
        when(storageService.upload(file)).thenReturn(filename);
        PersonalData personalData = createMockPersonalDataWithBirthCertificate();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.birthCertificateUploadImage(file);

        // Assert
        verify(storageService).upload(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo uploaded and assigned successfully", response.getBody());
    }

    @Test
    void birthCertificateUploadImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("birthCertificate.jpg");
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.birthCertificateUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void birthCertificateUploadImage_ShouldReturnBadRequest_WhenNoBirthCertificateDocument() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("birthCertificate.jpg");
        PersonalData personalData = createMockPersonalDataWithoutBirthCertificate();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.birthCertificateUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User does not have a birth certificate document", response.getBody());
    }

    @Test
    void birthCertificateUpdateImage_ShouldUpdateAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String newFilename = "newBirthCertificate.jpg";
        when(storageService.upload(file)).thenReturn(newFilename);
        PersonalData personalData = createMockPersonalDataWithBirthCertificate();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.birthCertificateUpdateImage(file);

        // Assert
        verify(storageService).upload(file);
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo updated successfully", response.getBody());
    }

    @Test
    void birthCertificateUpdateImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.birthCertificateUpdateImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void birthCertificateDelete_ShouldDeleteAndReturnNoContent() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithBirthCertificate();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.delete();

        // Assert
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void birthCertificateDelete_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.delete();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void birthCertificateDelete_ShouldReturnBadRequest_WhenNoBirthCertificate() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithoutBirthCertificate();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.delete();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Вспомогательный методы для создания mock-объектов
    private PersonalData createMockPersonalDataWithBirthCertificate() {
        BirthCertificate birthCertificate = new BirthCertificate();
        birthCertificate.setPhoto("oldPhoto.jpg");

        Documents documents = new Documents();
        documents.setBirthCertificate(birthCertificate);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }

    private PersonalData createMockPersonalDataWithoutBirthCertificate() {
        Documents documents = new Documents();
        documents.setBirthCertificate(null);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }









    @Test
    void compulsoryMedicalInsuranceUploadImage_ShouldUploadAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String filename = "compulsoryMedicalInsurance.jpg";
        when(storageService.upload(file)).thenReturn(filename);
        PersonalData personalData = createMockPersonalDataWithCompulsoryMedicalInsurance();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.compulsoryMedicalInsuranceUploadImage(file);

        // Assert
        verify(storageService).upload(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo uploaded and assigned successfully", response.getBody());
    }

    @Test
    void compulsoryMedicalInsuranceUploadImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("compulsoryMedicalInsurance.jpg");
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.compulsoryMedicalInsuranceUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void compulsoryMedicalInsuranceUploadImage_ShouldReturnBadRequest_WhenNoCompulsoryMedicalInsuranceDocument() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("compulsoryMedicalInsurance.jpg");
        PersonalData personalData = createMockPersonalDataWithoutCompulsoryMedicalInsurance();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.compulsoryMedicalInsuranceUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User does not have compulsory medical insurance document", response.getBody());
    }

    @Test
    void compulsoryMedicalInsuranceUpdateImage_ShouldUpdateAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String newFilename = "newCompulsoryMedicalInsurance.jpg";
        when(storageService.upload(file)).thenReturn(newFilename);
        PersonalData personalData = createMockPersonalDataWithCompulsoryMedicalInsurance();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.compulsoryMedicalInsuranceUpdateImage(file);

        // Assert
        verify(storageService).upload(file);
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo updated successfully", response.getBody());
    }

    @Test
    void compulsoryMedicalInsuranceUpdateImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.compulsoryMedicalInsuranceUpdateImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void compulsoryMedicalInsuranceDelete_ShouldDeleteAndReturnNoContent() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithCompulsoryMedicalInsurance();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.deleteCompulsoryMedicalInsurance();

        // Assert
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void compulsoryMedicalInsuranceDelete_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.deleteCompulsoryMedicalInsurance();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void compulsoryMedicalInsuranceDelete_ShouldReturnBadRequest_WhenNoCompulsoryMedicalInsurance() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithoutCompulsoryMedicalInsurance();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.deleteCompulsoryMedicalInsurance();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Вспомогательные методы для создания mock-объектов
    private PersonalData createMockPersonalDataWithCompulsoryMedicalInsurance() {
        CompulsoryMedicalInsurance compulsoryMedicalInsurance = new CompulsoryMedicalInsurance();
        compulsoryMedicalInsurance.setPhoto("oldPhoto.jpg");

        Documents documents = new Documents();
        documents.setCompulsoryMedicalInsurance(compulsoryMedicalInsurance);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }

    private PersonalData createMockPersonalDataWithoutCompulsoryMedicalInsurance() {
        Documents documents = new Documents();
        documents.setCompulsoryMedicalInsurance(null);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }








    @Test
    void drivingLicenseUploadImage_ShouldUploadAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String filename = "drivingLicense.jpg";
        when(storageService.upload(file)).thenReturn(filename);
        PersonalData personalData = createMockPersonalDataWithDrivingLicense();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.drivingLicenseUploadImage(file);

        // Assert
        verify(storageService).upload(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo uploaded and assigned successfully", response.getBody());
    }

    @Test
    void drivingLicenseUploadImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("drivingLicense.jpg");
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.drivingLicenseUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void drivingLicenseUploadImage_ShouldReturnBadRequest_WhenNoDrivingLicenseDocument() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("drivingLicense.jpg");
        PersonalData personalData = createMockPersonalDataWithoutDrivingLicense();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.drivingLicenseUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User does not have a driving license document", response.getBody());
    }

    @Test
    void drivingLicenseUpdateImage_ShouldUpdateAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String newFilename = "newDrivingLicense.jpg";
        when(storageService.upload(file)).thenReturn(newFilename);
        PersonalData personalData = createMockPersonalDataWithDrivingLicense();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.drivingLicenseUpdateImage(file);

        // Assert
        verify(storageService).upload(file);
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo updated successfully", response.getBody());
    }

    @Test
    void drivingLicenseUpdateImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.drivingLicenseUpdateImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void drivingLicenseDelete_ShouldDeleteAndReturnNoContent() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithDrivingLicense();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.deleteDrivingLicense();

        // Assert
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void drivingLicenseDelete_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.deleteDrivingLicense();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void drivingLicenseDelete_ShouldReturnBadRequest_WhenNoDrivingLicense() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithoutDrivingLicense();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.deleteDrivingLicense();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Вспомогательные методы для создания mock-объектов
    private PersonalData createMockPersonalDataWithDrivingLicense() {
        DrivingLicense drivingLicense = new DrivingLicense();
        drivingLicense.setPhoto("oldPhoto.jpg");

        Documents documents = new Documents();
        documents.setDrivingLicense(drivingLicense);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }

    private PersonalData createMockPersonalDataWithoutDrivingLicense() {
        Documents documents = new Documents();
        documents.setDrivingLicense(null);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }

















    @Test
    void foreignPassportUploadImage_ShouldUploadAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String filename = "foreignPassport.jpg";
        when(storageService.upload(file)).thenReturn(filename);
        PersonalData personalData = createMockPersonalDataWithForeignPassport();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.foreignPassportUploadImage(file);

        // Assert
        verify(storageService).upload(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo uploaded and assigned successfully", response.getBody());
    }

    @Test
    void foreignPassportUploadImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("foreignPassport.jpg");
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.foreignPassportUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void foreignPassportUploadImage_ShouldReturnBadRequest_WhenNoForeignPassportDocument() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("foreignPassport.jpg");
        PersonalData personalData = createMockPersonalDataWithoutForeignPassport();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.foreignPassportUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User does not have a foreign passport document", response.getBody());
    }

    @Test
    void foreignPassportUpdateImage_ShouldUpdateAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String newFilename = "newForeignPassport.jpg";
        when(storageService.upload(file)).thenReturn(newFilename);
        PersonalData personalData = createMockPersonalDataWithForeignPassport();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.foreignPassportUpdateImage(file);

        // Assert
        verify(storageService).upload(file);
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo updated successfully", response.getBody());
    }

    @Test
    void foreignPassportUpdateImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.foreignPassportUpdateImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void foreignPassportDeleteImage_ShouldDeleteAndReturnNoContent() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithForeignPassport();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.foreignPassportDeleteImage();

        // Assert
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void foreignPassportDeleteImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.foreignPassportDeleteImage();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void foreignPassportDeleteImage_ShouldReturnBadRequest_WhenNoForeignPassport() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithoutForeignPassport();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.foreignPassportDeleteImage();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Вспомогательные методы для создания mock-объектов
    private PersonalData createMockPersonalDataWithForeignPassport() {
        ForeignPassport foreignPassport = new ForeignPassport();
        foreignPassport.setPhoto("oldPhoto.jpg");

        Documents documents = new Documents();
        documents.setForeignPassport(foreignPassport);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }

    private PersonalData createMockPersonalDataWithoutForeignPassport() {
        Documents documents = new Documents();
        documents.setForeignPassport(null);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }









    @Test
    void insuranceNumberUploadImage_ShouldUploadAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String filename = "insuranceNumber.jpg";
        when(storageService.upload(file)).thenReturn(filename);
        PersonalData personalData = createMockPersonalDataWithInsuranceNumber();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.insuranceNumberUploadImage(file);

        // Assert
        verify(storageService).upload(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo uploaded and assigned successfully", response.getBody());
    }

    @Test
    void insuranceNumberUploadImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("insuranceNumber.jpg");
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.insuranceNumberUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void insuranceNumberUploadImage_ShouldReturnBadRequest_WhenNoInsuranceNumberDocument() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("insuranceNumber.jpg");
        PersonalData personalData = createMockPersonalDataWithoutInsuranceNumber();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.insuranceNumberUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User does not have an insurance number document", response.getBody());
    }

    @Test
    void insuranceNumberUpdateImage_ShouldUpdateAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String newFilename = "newInsuranceNumber.jpg";
        when(storageService.upload(file)).thenReturn(newFilename);
        PersonalData personalData = createMockPersonalDataWithInsuranceNumber();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.insuranceNumberUpdateImage(file);

        // Assert
        verify(storageService).upload(file);
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo updated successfully", response.getBody());
    }

    @Test
    void insuranceNumberUpdateImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.insuranceNumberUpdateImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void insuranceNumberDeleteImage_ShouldDeleteAndReturnNoContent() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithInsuranceNumber();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.insuranceNumberDeleteImage();

        // Assert
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void insuranceNumberDeleteImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.insuranceNumberDeleteImage();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void insuranceNumberDeleteImage_ShouldReturnBadRequest_WhenNoInsuranceNumber() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithoutInsuranceNumber();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.insuranceNumberDeleteImage();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Вспомогательные методы для создания mock-объектов
    private PersonalData createMockPersonalDataWithInsuranceNumber() {
        InsuranceNumberOfIndividualPersonalAccount insuranceNumber = new InsuranceNumberOfIndividualPersonalAccount();
        insuranceNumber.setPhoto("oldPhoto.jpg");

        Documents documents = new Documents();
        documents.setInsuranceNumberOfIndividualPersonalAccount(insuranceNumber);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }

    private PersonalData createMockPersonalDataWithoutInsuranceNumber() {
        Documents documents = new Documents();
        documents.setInsuranceNumberOfIndividualPersonalAccount(null);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }










    @Test
    void passportUploadImage_ShouldUploadAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String filename = "passport.jpg";
        when(storageService.upload(file)).thenReturn(filename);
        PersonalData personalData = createMockPersonalDataWithPassport();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.passportUploadImage(file);

        // Assert
        verify(storageService).upload(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo uploaded and assigned successfully", response.getBody());
    }

    @Test
    void passportUploadImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("passport.jpg");
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.passportUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void passportUploadImage_ShouldReturnBadRequest_WhenNoPassportDocument() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("passport.jpg");
        PersonalData personalData = createMockPersonalDataWithoutPassport();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.passportUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User does not have a passport document", response.getBody());
    }

    @Test
    void passportUpdateImage_ShouldUpdateAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String newFilename = "newPassport.jpg";
        when(storageService.upload(file)).thenReturn(newFilename);
        PersonalData personalData = createMockPersonalDataWithPassport();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.passportUpdateImage(file);

        // Assert
        verify(storageService).upload(file);
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo updated successfully", response.getBody());
    }

    @Test
    void passportUpdateImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.passportUpdateImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void passportDeleteImage_ShouldDeleteAndReturnNoContent() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithPassport();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.passportDeleteImage();

        // Assert
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void passportDeleteImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.passportDeleteImage();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void passportDeleteImage_ShouldReturnBadRequest_WhenNoPassport() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithoutPassport();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.passportDeleteImage();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Вспомогательные методы для создания mock-объектов
    private PersonalData createMockPersonalDataWithPassport() {
        Passport passport = new Passport();
        passport.setPhoto("oldPhoto.jpg");

        Documents documents = new Documents();
        documents.setPassport(passport);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }

    private PersonalData createMockPersonalDataWithoutPassport() {
        Documents documents = new Documents();
        documents.setPassport(null);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }









    @Test
    void taxPayerIdentificationNumberUploadImage_ShouldUploadAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String filename = "tin.jpg";
        when(storageService.upload(file)).thenReturn(filename);
        PersonalData personalData = createMockPersonalDataWithTIN();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.taxPayerIdentificationNumberUploadImage(file);

        // Assert
        verify(storageService).upload(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo uploaded and assigned successfully", response.getBody());
    }

    @Test
    void taxPayerIdentificationNumberUploadImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("tin.jpg");
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.taxPayerIdentificationNumberUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void taxPayerIdentificationNumberUploadImage_ShouldReturnBadRequest_WhenNoTINDocument() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("tin.jpg");
        PersonalData personalData = createMockPersonalDataWithoutTIN();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.taxPayerIdentificationNumberUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User does not have a tax payer identification number document", response.getBody());
    }

    @Test
    void taxPayerIdentificationNumberUpdateImage_ShouldUpdateAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String newFilename = "newTIN.jpg";
        when(storageService.upload(file)).thenReturn(newFilename);
        PersonalData personalData = createMockPersonalDataWithTIN();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.taxPayerIdentificationNumberUpdateImage(file);

        // Assert
        verify(storageService).upload(file);
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo updated successfully", response.getBody());
    }

    @Test
    void taxPayerIdentificationNumberUpdateImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.taxPayerIdentificationNumberUpdateImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void taxPayerIdentificationNumberDeleteImage_ShouldDeleteAndReturnNoContent() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithTIN();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.taxPayerIdentificationNumberDeleteImage();

        // Assert
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void taxPayerIdentificationNumberDeleteImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.taxPayerIdentificationNumberDeleteImage();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void taxPayerIdentificationNumberDeleteImage_ShouldReturnBadRequest_WhenNoTIN() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithoutTIN();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.taxPayerIdentificationNumberDeleteImage();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Вспомогательные методы для создания mock-объектов
    private PersonalData createMockPersonalDataWithTIN() {
        TaxPayerIdentificationNumber tin = new TaxPayerIdentificationNumber();
        tin.setPhoto("oldTIN.jpg");

        Documents documents = new Documents();
        documents.setTaxPayerIdentificationNumber(tin);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }

    private PersonalData createMockPersonalDataWithoutTIN() {
        Documents documents = new Documents();
        documents.setTaxPayerIdentificationNumber(null);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }










    @Test
    void voluntaryHealthInsuranceUploadImage_ShouldUploadAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String filename = "voluntary_health_insurance.jpg";
        when(storageService.upload(file)).thenReturn(filename);
        PersonalData personalData = createMockPersonalDataWithVoluntaryHealthInsurance();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.voluntaryHealthInsuranceUploadImage(file);

        // Assert
        verify(storageService).upload(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo uploaded and assigned successfully", response.getBody());
    }

    @Test
    void voluntaryHealthInsuranceUploadImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("voluntary_health_insurance.jpg");
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.voluntaryHealthInsuranceUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void voluntaryHealthInsuranceUploadImage_ShouldReturnBadRequest_WhenNoVoluntaryHealthInsuranceDocument() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(storageService.upload(file)).thenReturn("voluntary_health_insurance.jpg");
        PersonalData personalData = createMockPersonalDataWithoutVoluntaryHealthInsurance();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.voluntaryHealthInsuranceUploadImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User does not have a voluntary health insurance document", response.getBody());
    }

    @Test
    void voluntaryHealthInsuranceUpdateImage_ShouldUpdateAndReturnSuccess() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String newFilename = "new_voluntary_health_insurance.jpg";
        when(storageService.upload(file)).thenReturn(newFilename);
        PersonalData personalData = createMockPersonalDataWithVoluntaryHealthInsurance();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.voluntaryHealthInsuranceUpdateImage(file);

        // Assert
        verify(storageService).upload(file);
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Photo updated successfully", response.getBody());
    }

    @Test
    void voluntaryHealthInsuranceUpdateImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<String> response = controller.voluntaryHealthInsuranceUpdateImage(file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No documents associated with the user", response.getBody());
    }

    @Test
    void voluntaryHealthInsuranceDeleteImage_ShouldDeleteAndReturnNoContent() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithVoluntaryHealthInsurance();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.voluntaryHealthInsuranceDeleteImage();

        // Assert
        verify(storageService).delete(anyString());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void voluntaryHealthInsuranceDeleteImage_ShouldReturnBadRequest_WhenNoDocumentsAssociated() throws Exception {
        // Arrange
        PersonalData personalData = new PersonalData();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.voluntaryHealthInsuranceDeleteImage();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void voluntaryHealthInsuranceDeleteImage_ShouldReturnBadRequest_WhenNoVoluntaryHealthInsurance() throws Exception {
        // Arrange
        PersonalData personalData = createMockPersonalDataWithoutVoluntaryHealthInsurance();
        when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.voluntaryHealthInsuranceDeleteImage();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Вспомогательные методы для создания mock-объектов
    private PersonalData createMockPersonalDataWithVoluntaryHealthInsurance() {
        VoluntaryHealthInsurance voluntaryHealthInsurance = new VoluntaryHealthInsurance();
        voluntaryHealthInsurance.setPhoto("old_voluntary_health_insurance.jpg");

        Documents documents = new Documents();
        documents.setVoluntaryHealthInsurance(voluntaryHealthInsurance);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }

    private PersonalData createMockPersonalDataWithoutVoluntaryHealthInsurance() {
        Documents documents = new Documents();
        documents.setVoluntaryHealthInsurance(null);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }









    // @Test
    // void personalDataUploadImage_ShouldUploadAndReturnSuccess() throws Exception {
    //     // Arrange
    //     MultipartFile file = mock(MultipartFile.class);
    //     String filename = "personal_data_avatar.jpg";
    //     when(storageService.upload(file)).thenReturn(filename);
    //     PersonalData personalData = createMockPersonalData();
    //     when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

    //     // Act
    //     ResponseEntity<String> response = controller.personalDataUploadImage(file);

    //     // Assert
    //     verify(storageService).upload(file);
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals("Photo uploaded and assigned successfully", response.getBody());
    // }

    // @Test
    // void personalDataUploadImage_ShouldReturnInternalServerError_WhenExceptionOccurs() throws Exception {
    //     // Arrange
    //     MultipartFile file = mock(MultipartFile.class);
    //     when(storageService.upload(file)).thenThrow(new RuntimeException("Upload failed"));

    //     // Act
    //     ResponseEntity<String> response = controller.personalDataUploadImage(file);

    //     // Assert
    //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    //     assertEquals("Failed to upload file: Upload failed", response.getBody());
    // }

    // @Test
    // void personalDataUpdateImage_ShouldUpdateAndReturnSuccess() throws Exception {
    //     // Arrange
    //     MultipartFile file = mock(MultipartFile.class);
    //     String newFilename = "new_personal_data_avatar.jpg";
    //     when(storageService.upload(file)).thenReturn(newFilename);
    //     PersonalData personalData = createMockPersonalData();
    //     when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

    //     // Act
    //     ResponseEntity<String> response = controller.personalDataUpdateImage(file);

    //     // Assert
    //     verify(storageService).upload(file);
    //     verify(storageService).delete(anyString());
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals("Photo updated successfully", response.getBody());
    // }

    // @Test
    // void personalDataUpdateImage_ShouldReturnInternalServerError_WhenExceptionOccurs() throws Exception {
    //     // Arrange
    //     MultipartFile file = mock(MultipartFile.class);
    //     when(storageService.upload(file)).thenThrow(new RuntimeException("Upload failed"));

    //     // Act
    //     ResponseEntity<String> response = controller.personalDataUpdateImage(file);

    //     // Assert
    //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    //     assertEquals("Failed to update photo: Upload failed", response.getBody());
    // }

    // @Test
    // void personalDataDeleteImage_ShouldDeleteAndReturnNoContent() throws Exception {
    //     // Arrange
    //     PersonalData personalData = createMockPersonalData();
    //     when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

    //     // Act
    //     ResponseEntity<Void> response = controller.personalDataDeleteImage();

    //     // Assert
    //     verify(storageService).delete(anyString());
    //     assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    // }

    // @Test
    // void personalDataDeleteImage_ShouldReturnInternalServerError_WhenExceptionOccurs() throws Exception {
    //     // Arrange
    //     PersonalData personalData = createMockPersonalData();
    //     when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));
    //     doThrow(new RuntimeException("Delete failed")).when(storageService).delete(anyString());

    //     // Act
    //     ResponseEntity<Void> response = controller.personalDataDeleteImage();

    //     // Assert
    //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    // }

    // @Test
    // void personalDataDeleteImage_ShouldReturnBadRequest_WhenNoAvatar() throws Exception {
    //     // Arrange
    //     PersonalData personalData = new PersonalData();
    //     when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

    //     // Act
    //     ResponseEntity<Void> response = controller.personalDataDeleteImage();

    //     // Assert
    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    // }

    // @Test
    // void personalDataDeleteImage_ShouldReturnBadRequest_WhenUserNotFound() throws Exception {
    //     // Arrange
    //     when(personalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.empty());

    //     // Act
    //     ResponseEntity<Void> response = controller.personalDataDeleteImage();

    //     // Assert
    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    // }

    // // Вспомогательные методы для создания mock-объектов
    // private PersonalData createMockPersonalData() {
    //     PersonalData personalData = new PersonalData();
    //     personalData.setAvatar("old_avatar.jpg");
    //     personalData.setId(new Id(UUID.randomUUID()));
    //     return personalData;
    // }
}
