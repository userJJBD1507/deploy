package com.project.id.project.unit.services.addresses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.mappers.addresses.AdditionalAddressMapper;
import com.project.id.project.application.services.linkers.AddressesRepository;
import com.project.id.project.application.services.pdf.PdfGenerator;
import com.project.id.project.application.services.pdf.handlers.Pdf;
import com.project.id.project.application.services.pdf.handlers.PdfRepository;
import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;

@ExtendWith(MockitoExtension.class)
class AdditionalAddressServiceTest {

    @Mock
    private JpaAdditionalAddressRepository additionalAddressRepository;
    @Mock
    private PdfGenerator pdfGenerator;
    @Mock
    private StorageService storageService;
    @Mock
    private PdfRepository pdfRepository;
    @Mock
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Mock
    private AddressesRepository addressesRepository;

    @InjectMocks
    private AdditionalAddressService additionalAddressService;

    @Test
    void testCreate() throws Exception {
        // Arrange
        String username = "testUser";
        AdditionalAddressDTO dto = new AdditionalAddressDTO();
        AdditionalAddress entity = new AdditionalAddress();
        PersonalData personalData = new PersonalData();
        Addresses addresses = new Addresses();
        Id addressId = new Id();
        Pdf pdf = new Pdf();

        // Mocking the dependencies
        personalData.setAddresses(addresses);
        addresses.setId(addressId);
        addresses.setAdditionalAddress(new ArrayList<>());
        
        when(entityPersonalDataRepository.findByInvocation(username))
                .thenReturn(Optional.of(personalData));
        when(addressesRepository.findById(addressId))
                .thenReturn(Optional.of(addresses));
        when(pdfGenerator.generatePdfFromDto(dto))
                .thenReturn(new byte[]{1, 2, 3});
        when(storageService.upload((byte[]) any()))
                .thenReturn("http://s3-link.com/pdf");

        // Act
        additionalAddressService.create(username, dto);

        // Assert
        verify(entityPersonalDataRepository).findByInvocation(username);
        verify(addressesRepository).findById(addressId);
        verify(additionalAddressRepository).addAdditionalAddress(any(AdditionalAddress.class));
        verify(pdfGenerator).generatePdfFromDto(dto);
        verify(storageService).upload((byte[]) any());
        verify(pdfRepository).save(any(Pdf.class));
    }
    @Test
    public void testUpdate_ShouldUpdateAdditionalAddressAndGeneratePdf() throws IOException{
        // Arrange
        Id id = new Id();
        AdditionalAddressDTO dto = new AdditionalAddressDTO();
        Id addressesId = new Id();

        AdditionalAddress existingEntity = new AdditionalAddress();
        existingEntity.setId(id);

        Addresses addresses = new Addresses();
        Pdf pdf = new Pdf();
        pdf.setS3Link("old-s3-link");

        when(additionalAddressRepository.getAdditionalAddress(id)).thenReturn(existingEntity);
        when(addressesRepository.findById(addressesId)).thenReturn(Optional.of(addresses));
        when(pdfRepository.findByResourceId(id.toString())).thenReturn(Optional.of(pdf));
        when(pdfGenerator.generatePdfFromDto(dto)).thenReturn(new byte[0]);
        when(storageService.upload(any(byte[].class))).thenReturn("new-s3-link");

        // Act
        additionalAddressService.update(id, dto, addressesId);

        // Assert
        verify(additionalAddressRepository).updateAdditionalAddress(any(AdditionalAddress.class));
        verify(pdfRepository, times(2)).deleteById(pdf.getId());
        verify(storageService, times(2)).delete(pdf.getS3Link());
        verify(storageService).upload(any(byte[].class));
        verify(pdfRepository).save(any(Pdf.class));
    }

    @Test
    public void testUpdate_ShouldHandlePdfGenerationFailure() throws IOException {
        // Arrange
        Id id = new Id();
        AdditionalAddressDTO dto = new AdditionalAddressDTO();
        Id addressesId = new Id();

        AdditionalAddress existingEntity = new AdditionalAddress();
        existingEntity.setId(id);

        Addresses addresses = new Addresses();
        Pdf pdf = new Pdf();
        pdf.setS3Link("old-s3-link");

        when(additionalAddressRepository.getAdditionalAddress(id)).thenReturn(existingEntity);
        when(addressesRepository.findById(addressesId)).thenReturn(Optional.of(addresses));
        when(pdfRepository.findByResourceId(id.toString())).thenReturn(Optional.of(pdf));
        when(pdfGenerator.generatePdfFromDto(dto)).thenThrow(new RuntimeException("PDF generation failed"));

        // Act
        additionalAddressService.update(id, dto, addressesId);

        // Assert
        verify(additionalAddressRepository).updateAdditionalAddress(any(AdditionalAddress.class));
        verify(pdfRepository, times(2)).deleteById(pdf.getId());
        verify(pdfRepository, never()).save(any(Pdf.class));
    }


        @Test
        void testDelete() {
        // Arrange
        Id id = new Id();
        Pdf pdf = new Pdf();
        pdf.setS3Link("http://s3-link.com/pdf");

        when(pdfRepository.findByResourceId(id.toString())).thenReturn(Optional.of(pdf));

        // Act
        additionalAddressService.delete(id);

        // Assert
        verify(additionalAddressRepository).deleteAdditionalAddress(id);
        verify(pdfRepository).findByResourceId(id.toString());
        verify(storageService).delete(pdf.getS3Link());
        verify(pdfRepository).deleteById(pdf.getId());
        }
        @Test
        public void testDelete_ShouldNotDeleteIfAddressNotFound() {
            // Arrange
            Id id = new Id();
        
        
            // Act
            additionalAddressService.delete(id);
        
            // Assert
            verify(additionalAddressRepository).deleteAdditionalAddress(id);
            verify(storageService, never()).delete(any());
            verify(pdfRepository, never()).deleteById(any());
        }
        @Test
        public void testDelete_ShouldHandleS3DeletionErrorGracefully() {
        // Arrange
        Id id = new Id();

        Pdf pdf = new Pdf();
        pdf.setS3Link("test-s3-link");
        pdf.setId(new Id());

        when(pdfRepository.findByResourceId(id.toString())).thenReturn(Optional.of(pdf));
        doThrow(new RuntimeException("S3 deletion error")).when(storageService).delete(anyString());

        // Act
        additionalAddressService.delete(id);

        // Assert
        verify(additionalAddressRepository).deleteAdditionalAddress(id);
        verify(pdfRepository).findByResourceId(id.toString());
        verify(storageService).delete(pdf.getS3Link());
        }

@Test
        public void testDelete_ShouldHandleExceptionGracefully() {
        // Arrange
        Id id = new Id();


        // Act
        additionalAddressService.delete(id);

        // Assert
        verify(additionalAddressRepository).deleteAdditionalAddress(id);
        verify(storageService, never()).delete(any());
        verify(pdfRepository, never()).deleteById(any());
        }
        @Test
        public void testDelete_ShouldDeleteAdditionalAddressWithoutPdf() {
        // Arrange
        Id id = new Id();


        when(pdfRepository.findByResourceId(id.toString())).thenReturn(Optional.empty());

        // Act
        additionalAddressService.delete(id);

        // Assert
        verify(additionalAddressRepository).deleteAdditionalAddress(id);
        verify(pdfRepository).findByResourceId(id.toString());
        verify(storageService, never()).delete(any());
        verify(pdfRepository, never()).deleteById(any());
        }
        @Test
        public void testDelete_ShouldDeleteAdditionalAddressAndPdf() {
        // Arrange
        Id id = new Id();

        Pdf pdf = new Pdf();
        pdf.setS3Link("test-s3-link");
        pdf.setId(new Id());

        when(pdfRepository.findByResourceId(id.toString())).thenReturn(Optional.of(pdf));
        doNothing().when(storageService).delete(anyString());
        doNothing().when(pdfRepository).deleteById(any());

        // Act
        additionalAddressService.delete(id);

        // Assert
        verify(additionalAddressRepository).deleteAdditionalAddress(id);
        verify(pdfRepository).findByResourceId(id.toString());
        verify(storageService).delete(pdf.getS3Link());
        verify(pdfRepository).deleteById(pdf.getId());
        }

        @Test
        public void testRead_ShouldReturnAdditionalAddressDTO() {
        // Arrange
        Id id = new Id();
        AdditionalAddress entity = new AdditionalAddress();
        entity.setId(id);

        AdditionalAddressDTO dto = new AdditionalAddressDTO();

        when(additionalAddressRepository.getAdditionalAddress(id)).thenReturn(entity);

        // Act
        Optional<AdditionalAddressDTO> result = additionalAddressService.read(id);

        // Assert
        assertTrue(result.isPresent(), "Expected Optional to be present");
        assertEquals(dto, result.get(), "Expected DTO to match the entity");
        verify(additionalAddressRepository).getAdditionalAddress(id);
        }


        @Test
        public void testRead_ShouldThrowExceptionWhenErrorOccurs() {
        // Arrange
        Id id = new Id();

        when(additionalAddressRepository.getAdditionalAddress(id)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
                additionalAddressService.read(id);
        });

        assertEquals("Database error", exception.getMessage());
        verify(additionalAddressRepository).getAdditionalAddress(id);
        }
}
