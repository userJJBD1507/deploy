package com.project.id.project.unit.services.documents;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.DTOs.documents.BirthCertificateDTO;
import com.project.id.project.application.mappers.addresses.AdditionalAddressMapper;
import com.project.id.project.application.mappers.documents.BirthCertificateMapper;
import com.project.id.project.application.services.linkers.AddressesRepository;
import com.project.id.project.application.services.linkers.DocumentsRepository;
import com.project.id.project.application.services.pdf.PdfGenerator;
import com.project.id.project.application.services.pdf.handlers.Pdf;
import com.project.id.project.application.services.pdf.handlers.PdfRepository;
import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.documents.entities.BirthCertificate;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaBirthCertificateRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;
import com.project.id.project.infrastructure.services.documents.BirthCertificateService;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BirthCertificateServiceTest {

    @Mock
    private JpaBirthCertificateRepository birthCertificateRepository;

    @Mock
    private PdfGenerator pdfGenerator;

    @Mock
    private StorageService storageService;

    @Mock
    private PdfRepository pdfRepository;

    @Mock
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @Mock
    private DocumentsRepository documentsRepository;

    @InjectMocks
    private BirthCertificateService birthCertificateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() throws Exception {
        // Arrange
        String username = "user123";
        BirthCertificateDTO dto = new BirthCertificateDTO();

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(new Documents());

        Documents documents = new Documents();

        when(entityPersonalDataRepository.findByInvocation(username)).thenReturn(Optional.of(personalData));
        when(documentsRepository.findById(any(Id.class))).thenReturn(Optional.of(documents));
        when(pdfGenerator.generatePdfFromDto(dto)).thenReturn(new byte[0]);
        when(storageService.upload(any(byte[].class))).thenReturn("s3Link");

        // Act
        birthCertificateService.create(username, dto);

        // Assert
        verify(birthCertificateRepository, times(2)).addBirthCertificate(any(BirthCertificate.class));
    }
    
    

    @Test
    public void testCreateThrowsException() {
        String username = "testUser";
        BirthCertificateDTO dto = new BirthCertificateDTO();

        when(entityPersonalDataRepository.findByInvocation(username)).thenThrow(new RuntimeException("Personal data not found"));

        assertDoesNotThrow(() -> birthCertificateService.create(username, dto));
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        Id id = new Id();
        BirthCertificateDTO dto = new BirthCertificateDTO();
        BirthCertificate existingEntity = new BirthCertificate();
        BirthCertificate updatedEntity = new BirthCertificate();
        Documents documents = new Documents();
        Pdf existingPdf = new Pdf();

        when(birthCertificateRepository.getBirthCertificate(id)).thenReturn(existingEntity);
        when(documentsRepository.findById(any(Id.class))).thenReturn(Optional.of(documents));
        when(pdfRepository.findByResourceId(id.toString())).thenReturn(Optional.of(existingPdf));

        when(storageService.upload(any(byte[].class))).thenReturn("s3-link");

        birthCertificateService.update(id, dto, new Id());

        verify(birthCertificateRepository, times(1)).addBirthCertificate(any(BirthCertificate.class));
    }

    @Test
    public void testUpdateThrowsException() {
        Id id = new Id();
        BirthCertificateDTO dto = new BirthCertificateDTO();

        when(birthCertificateRepository.getBirthCertificate(id)).thenThrow(new RuntimeException("Birth certificate not found"));

        assertDoesNotThrow(() -> birthCertificateService.update(id, dto, new Id()));
    }

    @Test
    public void testDeleteSuccess() {
        Id id = new Id();
        Documents documents = new Documents();
        Pdf pdf = new Pdf();
        pdf.setS3Link("s3-link");

        when(documentsRepository.getByBirthCertificate(id)).thenReturn(documents);
        when(pdfRepository.findByResourceId(id.toString())).thenReturn(Optional.of(pdf));
        doNothing().when(storageService).delete(any(String.class));

        birthCertificateService.delete(id);

        verify(birthCertificateRepository, times(1)).deleteBirthCertificate(id);
        verify(pdfRepository, times(1)).deleteById(any(Id.class));
    }


    @Test
    public void testDeleteThrowsException() {
        Id id = new Id();

        when(documentsRepository.getByBirthCertificate(id)).thenThrow(new RuntimeException("Documents not found"));

        assertDoesNotThrow(() -> birthCertificateService.delete(id));
    }

    @Test
    public void testReadSuccess() {
        Id id = new Id();
        BirthCertificate birthCertificate = new BirthCertificate();

        when(birthCertificateRepository.getBirthCertificate(id)).thenReturn(birthCertificate);

        Optional<BirthCertificateDTO> result = birthCertificateService.read(id);

        assertTrue(result.isPresent());
        verify(birthCertificateRepository, times(1)).getBirthCertificate(id);
    }
    
}
