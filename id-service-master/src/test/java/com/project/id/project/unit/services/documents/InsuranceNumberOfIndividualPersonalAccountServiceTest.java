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
import com.project.id.project.application.DTOs.documents.InsuranceNumberOfIndividualPersonalAccountDTO;
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
import com.project.id.project.core.documents.entities.InsuranceNumberOfIndividualPersonalAccount;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaBirthCertificateRepository;
import com.project.id.project.infrastructure.repositories.JpaInsuranceNumberOfIndividualPersonalAccountRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;
import com.project.id.project.infrastructure.services.documents.BirthCertificateService;
import com.project.id.project.infrastructure.services.documents.InsuranceNumberOfIndividualPersonalAccountService;

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
public class InsuranceNumberOfIndividualPersonalAccountServiceTest {

    @Mock
    private JpaInsuranceNumberOfIndividualPersonalAccountRepository insuranceRepository;

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
    private InsuranceNumberOfIndividualPersonalAccountService insuranceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() throws Exception {
        // Arrange
        String username = "user123";
        InsuranceNumberOfIndividualPersonalAccountDTO dto = new InsuranceNumberOfIndividualPersonalAccountDTO();

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(new Documents());

        Documents documents = new Documents();

        when(entityPersonalDataRepository.findByInvocation(username)).thenReturn(Optional.of(personalData));
        when(documentsRepository.findById(any(Id.class))).thenReturn(Optional.of(documents));
        when(pdfGenerator.generatePdfFromDto(dto)).thenReturn(new byte[0]);
        when(storageService.upload(any(byte[].class))).thenReturn("s3Link");

        // Act
        insuranceService.create(username, dto);

        // Assert
        verify(insuranceRepository, times(2)).addInsuranceNumberOfIndividualPersonalAccount(any(InsuranceNumberOfIndividualPersonalAccount.class));
    }

    @Test
    public void testCreateThrowsException() {
        String username = "testUser";
        InsuranceNumberOfIndividualPersonalAccountDTO dto = new InsuranceNumberOfIndividualPersonalAccountDTO();

        when(entityPersonalDataRepository.findByInvocation(username)).thenThrow(new RuntimeException("Personal data not found"));

        assertDoesNotThrow(() -> insuranceService.create(username, dto));
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        Id id = new Id();
        InsuranceNumberOfIndividualPersonalAccountDTO dto = new InsuranceNumberOfIndividualPersonalAccountDTO();
        InsuranceNumberOfIndividualPersonalAccount existingEntity = new InsuranceNumberOfIndividualPersonalAccount();
        InsuranceNumberOfIndividualPersonalAccount updatedEntity = new InsuranceNumberOfIndividualPersonalAccount();
        Documents documents = new Documents();
        Pdf existingPdf = new Pdf();

        when(insuranceRepository.getInsuranceNumberOfIndividualPersonalAccount(id)).thenReturn(existingEntity);
        when(documentsRepository.findById(any(Id.class))).thenReturn(Optional.of(documents));
        when(pdfRepository.findByResourceId(id.toString())).thenReturn(Optional.of(existingPdf));

        when(storageService.upload(any(byte[].class))).thenReturn("s3-link");

        insuranceService.update(id, dto, new Id());

        verify(insuranceRepository, times(1)).addInsuranceNumberOfIndividualPersonalAccount(any(InsuranceNumberOfIndividualPersonalAccount.class));
    }

    @Test
    public void testUpdateThrowsException() {
        Id id = new Id();
        InsuranceNumberOfIndividualPersonalAccountDTO dto = new InsuranceNumberOfIndividualPersonalAccountDTO();

        when(insuranceRepository.getInsuranceNumberOfIndividualPersonalAccount(id)).thenThrow(new RuntimeException("InsuranceNumberOfIndividualPersonalAccount not found"));

        assertDoesNotThrow(() -> insuranceService.update(id, dto, new Id()));
    }

    @Test
    public void testDeleteSuccess() {
        Id id = new Id();
        Documents documents = new Documents();
        Pdf pdf = new Pdf();
        pdf.setS3Link("s3-link");

        when(documentsRepository.getByInsuranceNumberOfIndividualPersonalAccount(id)).thenReturn(documents);
        when(pdfRepository.findByResourceId(id.toString())).thenReturn(Optional.of(pdf));
        doNothing().when(storageService).delete(any(String.class));

        insuranceService.delete(id);

        verify(insuranceRepository, times(1)).deleteInsuranceNumberOfIndividualPersonalAccount(id);
        verify(pdfRepository, times(1)).deleteById(any(Id.class));
    }

    @Test
    public void testDeleteThrowsException() {
        Id id = new Id();

        when(documentsRepository.getByInsuranceNumberOfIndividualPersonalAccount(id)).thenThrow(new RuntimeException("Documents not found"));

        assertDoesNotThrow(() -> insuranceService.delete(id));
    }

    @Test
    public void testReadSuccess() {
        Id id = new Id();
        InsuranceNumberOfIndividualPersonalAccount insuranceAccount = new InsuranceNumberOfIndividualPersonalAccount();

        when(insuranceRepository.getInsuranceNumberOfIndividualPersonalAccount(id)).thenReturn(insuranceAccount);

        Optional<InsuranceNumberOfIndividualPersonalAccountDTO> result = insuranceService.read(id);

        assertTrue(result.isPresent());
        verify(insuranceRepository, times(1)).getInsuranceNumberOfIndividualPersonalAccount(id);
    }
}
