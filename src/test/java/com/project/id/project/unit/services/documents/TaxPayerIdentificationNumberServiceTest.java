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
import com.project.id.project.application.DTOs.documents.TaxPayerIdentificationNumberDTO;
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
import com.project.id.project.core.documents.entities.TaxPayerIdentificationNumber;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaBirthCertificateRepository;
import com.project.id.project.infrastructure.repositories.JpaTaxPayerIdentificationNumberRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;
import com.project.id.project.infrastructure.services.documents.BirthCertificateService;
import com.project.id.project.infrastructure.services.documents.TaxPayerIdentificationNumberService;

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
public class TaxPayerIdentificationNumberServiceTest {

    @Mock
    private JpaTaxPayerIdentificationNumberRepository taxPayerIdentificationNumberRepository;

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
    private TaxPayerIdentificationNumberService taxPayerIdentificationNumberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() throws Exception {
        // Arrange
        String username = "user123";
        TaxPayerIdentificationNumberDTO dto = new TaxPayerIdentificationNumberDTO();
        PersonalData personalData = new PersonalData();
        personalData.setDocuments(new Documents());
        Documents documents = new Documents();

        when(entityPersonalDataRepository.findByInvocation(username)).thenReturn(Optional.of(personalData));
        when(documentsRepository.findById(any(Id.class))).thenReturn(Optional.of(documents));
        when(pdfGenerator.generatePdfFromDto(dto)).thenReturn(new byte[0]);
        when(storageService.upload(any(byte[].class))).thenReturn("s3Link");

        // Act
        taxPayerIdentificationNumberService.create(username, dto);

        // Assert
        verify(taxPayerIdentificationNumberRepository, times(2)).addTaxPayerIdentificationNumber(any(TaxPayerIdentificationNumber.class));
    }

    @Test
    public void testCreateThrowsException() {
        String username = "testUser";
        TaxPayerIdentificationNumberDTO dto = new TaxPayerIdentificationNumberDTO();

        when(entityPersonalDataRepository.findByInvocation(username)).thenThrow(new RuntimeException("Personal data not found"));

        assertDoesNotThrow(() -> taxPayerIdentificationNumberService.create(username, dto));
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        Id id = new Id();
        TaxPayerIdentificationNumberDTO dto = new TaxPayerIdentificationNumberDTO();
        TaxPayerIdentificationNumber existingEntity = new TaxPayerIdentificationNumber();
        TaxPayerIdentificationNumber updatedEntity = new TaxPayerIdentificationNumber();
        Documents documents = new Documents();
        Pdf existingPdf = new Pdf();

        when(taxPayerIdentificationNumberRepository.getTaxPayerIdentificationNumber(id)).thenReturn(existingEntity);
        when(documentsRepository.findById(any(Id.class))).thenReturn(Optional.of(documents));
        when(pdfRepository.findByResourceId(id.toString())).thenReturn(Optional.of(existingPdf));

        when(storageService.upload(any(byte[].class))).thenReturn("s3-link");

        taxPayerIdentificationNumberService.update(id, dto, new Id());

        verify(taxPayerIdentificationNumberRepository, times(1)).addTaxPayerIdentificationNumber(any(TaxPayerIdentificationNumber.class));
    }

    @Test
    public void testUpdateThrowsException() {
        Id id = new Id();
        TaxPayerIdentificationNumberDTO dto = new TaxPayerIdentificationNumberDTO();

        when(taxPayerIdentificationNumberRepository.getTaxPayerIdentificationNumber(id)).thenThrow(new RuntimeException("TaxPayerIdentificationNumber not found"));

        assertDoesNotThrow(() -> taxPayerIdentificationNumberService.update(id, dto, new Id()));
    }

    @Test
    public void testDeleteSuccess() {
        Id id = new Id();
        Documents documents = new Documents();
        Pdf pdf = new Pdf();
        pdf.setS3Link("s3-link");

        when(documentsRepository.getByTaxPayerIdentificationNumber(id)).thenReturn(documents);
        when(pdfRepository.findByResourceId(id.toString())).thenReturn(Optional.of(pdf));
        doNothing().when(storageService).delete(any(String.class));

        taxPayerIdentificationNumberService.delete(id);

        verify(taxPayerIdentificationNumberRepository, times(1)).deleteTaxPayerIdentificationNumber(id);
        verify(pdfRepository, times(1)).deleteById(any(Id.class));
    }

    @Test
    public void testDeleteThrowsException() {
        Id id = new Id();

        when(documentsRepository.getByTaxPayerIdentificationNumber(id)).thenThrow(new RuntimeException("Documents not found"));

        assertDoesNotThrow(() -> taxPayerIdentificationNumberService.delete(id));
    }

    @Test
    public void testReadSuccess() {
        Id id = new Id();
        TaxPayerIdentificationNumber taxPayerIdentificationNumber = new TaxPayerIdentificationNumber();

        when(taxPayerIdentificationNumberRepository.getTaxPayerIdentificationNumber(id)).thenReturn(taxPayerIdentificationNumber);

        Optional<TaxPayerIdentificationNumberDTO> result = taxPayerIdentificationNumberService.read(id);

        assertTrue(result.isPresent());
        verify(taxPayerIdentificationNumberRepository, times(1)).getTaxPayerIdentificationNumber(id);
    }
}
