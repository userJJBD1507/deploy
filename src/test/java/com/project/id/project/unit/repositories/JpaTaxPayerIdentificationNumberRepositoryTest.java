package com.project.id.project.unit.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.documents.entities.TaxPayerIdentificationNumber;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityTaxPayerIdentificationNumberRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaTaxPayerIdentificationNumberRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JpaTaxPayerIdentificationNumberRepositoryTest {

    @Mock
    private EntityTaxPayerIdentificationNumberRepository entityTaxPayerIdentificationNumberRepository;

    @InjectMocks
    private JpaTaxPayerIdentificationNumberRepository jpaTaxPayerIdentificationNumberRepository;

    private TaxPayerIdentificationNumber taxPayerIdentificationNumber;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Assuming Id is a simple object, replace with real object if needed
        taxPayerIdentificationNumber = new TaxPayerIdentificationNumber();
        taxPayerIdentificationNumber.setName("tpin");
    }

    @Test
    public void testAddTaxPayerIdentificationNumber() {
        // Arrange
        when(entityTaxPayerIdentificationNumberRepository.save(any(TaxPayerIdentificationNumber.class)))
                .thenReturn(taxPayerIdentificationNumber);

        // Act
        jpaTaxPayerIdentificationNumberRepository.addTaxPayerIdentificationNumber(taxPayerIdentificationNumber);

        // Assert
        verify(entityTaxPayerIdentificationNumberRepository, times(1)).save(taxPayerIdentificationNumber);
    }

    @Test
    public void testGetTaxPayerIdentificationNumber_Success() {
        // Arrange
        when(entityTaxPayerIdentificationNumberRepository.findById(id)).thenReturn(java.util.Optional.of(taxPayerIdentificationNumber));

        // Act
        TaxPayerIdentificationNumber result = jpaTaxPayerIdentificationNumberRepository.getTaxPayerIdentificationNumber(id);

        // Assert
        assertNotNull(result);
        assertEquals(taxPayerIdentificationNumber, result);
        verify(entityTaxPayerIdentificationNumberRepository, times(1)).findById(id);
    }

    @Test
    public void testGetTaxPayerIdentificationNumber_ThrowsException() {
        // Arrange
        when(entityTaxPayerIdentificationNumberRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaTaxPayerIdentificationNumberRepository.getTaxPayerIdentificationNumber(id));
        assertEquals("TaxPayerIdentificationNumber not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdateTaxPayerIdentificationNumber() {
        // Arrange
        when(entityTaxPayerIdentificationNumberRepository.save(any(TaxPayerIdentificationNumber.class)))
                .thenReturn(taxPayerIdentificationNumber);

        // Act
        jpaTaxPayerIdentificationNumberRepository.updateTaxPayerIdentificationNumber(taxPayerIdentificationNumber);

        // Assert
        verify(entityTaxPayerIdentificationNumberRepository, times(1)).save(taxPayerIdentificationNumber);
    }

    @Test
    public void testDeleteTaxPayerIdentificationNumber_Success() {
        // Arrange
        when(entityTaxPayerIdentificationNumberRepository.existsById(id)).thenReturn(true);

        // Act
        jpaTaxPayerIdentificationNumberRepository.deleteTaxPayerIdentificationNumber(id);

        // Assert
        verify(entityTaxPayerIdentificationNumberRepository, times(1)).delete(id);
    }

    @Test
    public void testDeleteTaxPayerIdentificationNumber_ThrowsException() {
        // Arrange
        when(entityTaxPayerIdentificationNumberRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaTaxPayerIdentificationNumberRepository.deleteTaxPayerIdentificationNumber(id));
        assertEquals("TaxPayerIdentificationNumber not found with ID: " + id, exception.getMessage());
    }
}
