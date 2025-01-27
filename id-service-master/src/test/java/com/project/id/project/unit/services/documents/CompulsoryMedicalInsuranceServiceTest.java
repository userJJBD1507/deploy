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
import com.project.id.project.application.DTOs.documents.CompulsoryMedicalInsuranceDTO;
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
import com.project.id.project.core.documents.entities.CompulsoryMedicalInsurance;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaBirthCertificateRepository;
import com.project.id.project.infrastructure.repositories.JpaCompulsoryMedicalInsuranceRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;
import com.project.id.project.infrastructure.services.documents.BirthCertificateService;
import com.project.id.project.infrastructure.services.documents.CompulsoryMedicalInsuranceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CompulsoryMedicalInsuranceServiceTest {

    @Mock
    private JpaCompulsoryMedicalInsuranceRepository compulsoryMedicalInsuranceRepository;

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
    private CompulsoryMedicalInsuranceService compulsoryMedicalInsuranceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() throws Exception {
        // Arrange
        String username = "user123";
        CompulsoryMedicalInsuranceDTO dto = new CompulsoryMedicalInsuranceDTO();

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(new Documents());

        Documents documents = new Documents();

        when(entityPersonalDataRepository.findByInvocation(username)).thenReturn(Optional.of(personalData));
        when(documentsRepository.findById(any(Id.class))).thenReturn(Optional.of(documents));
        when(pdfGenerator.generatePdfFromDto(dto)).thenReturn(new byte[0]);
        when(storageService.upload(any(byte[].class))).thenReturn("s3Link");

        // Act
        compulsoryMedicalInsuranceService.create(username, dto);

        // Assert
        verify(compulsoryMedicalInsuranceRepository, times(2)).addCompulsoryMedicalInsurance(any(CompulsoryMedicalInsurance.class));
    }

    @Test
    public void testCreateThrowsException() {
        String username = "testUser";
        CompulsoryMedicalInsuranceDTO dto = new CompulsoryMedicalInsuranceDTO();

        when(entityPersonalDataRepository.findByInvocation(username)).thenThrow(new RuntimeException("Personal data not found"));

        assertDoesNotThrow(() -> compulsoryMedicalInsuranceService.create(username, dto));
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        Id id = new Id();
        CompulsoryMedicalInsuranceDTO dto = new CompulsoryMedicalInsuranceDTO();
        CompulsoryMedicalInsurance existingEntity = new CompulsoryMedicalInsurance();
        CompulsoryMedicalInsurance updatedEntity = new CompulsoryMedicalInsurance();
        Documents documents = new Documents();
        Pdf existingPdf = new Pdf();

        when(compulsoryMedicalInsuranceRepository.getCompulsoryMedicalInsurance(id)).thenReturn(existingEntity);
        when(documentsRepository.findById(any(Id.class))).thenReturn(Optional.of(documents));
        when(pdfRepository.findByResourceId(id.toString())).thenReturn(Optional.of(existingPdf));

        when(storageService.upload(any(byte[].class))).thenReturn("s3-link");

        compulsoryMedicalInsuranceService.update(id, dto, new Id());

        verify(compulsoryMedicalInsuranceRepository, times(1)).addCompulsoryMedicalInsurance(any(CompulsoryMedicalInsurance.class));
    }

    @Test
    public void testUpdateThrowsException() {
        Id id = new Id();
        CompulsoryMedicalInsuranceDTO dto = new CompulsoryMedicalInsuranceDTO();

        when(compulsoryMedicalInsuranceRepository.getCompulsoryMedicalInsurance(id)).thenThrow(new RuntimeException("Compulsory medical insurance not found"));

        assertDoesNotThrow(() -> compulsoryMedicalInsuranceService.update(id, dto, new Id()));
    }

    @Test
    public void testDeleteSuccess() {
        Id id = new Id();
        Documents documents = new Documents();
        Pdf pdf = new Pdf();
        pdf.setS3Link("s3-link");

        when(documentsRepository.getByCompulsoryMedicalInsurance(id)).thenReturn(documents);
        when(pdfRepository.findByResourceId(id.toString())).thenReturn(Optional.of(pdf));
        doNothing().when(storageService).delete(any(String.class));

        compulsoryMedicalInsuranceService.delete(id);

        verify(compulsoryMedicalInsuranceRepository, times(1)).deleteCompulsoryMedicalInsurance(id);
        verify(pdfRepository, times(1)).deleteById(any(Id.class));
    }

    @Test
    public void testDeleteThrowsException() {
        Id id = new Id();

        when(documentsRepository.getByCompulsoryMedicalInsurance(id)).thenThrow(new RuntimeException("Documents not found"));

        assertDoesNotThrow(() -> compulsoryMedicalInsuranceService.delete(id));
    }

    @Test
    public void testReadSuccess() {
        Id id = new Id();
        CompulsoryMedicalInsurance compulsoryMedicalInsurance = new CompulsoryMedicalInsurance();

        when(compulsoryMedicalInsuranceRepository.getCompulsoryMedicalInsurance(id)).thenReturn(compulsoryMedicalInsurance);

        Optional<CompulsoryMedicalInsuranceDTO> result = compulsoryMedicalInsuranceService.read(id);

        assertTrue(result.isPresent());
        verify(compulsoryMedicalInsuranceRepository, times(1)).getCompulsoryMedicalInsurance(id);
    }
}
