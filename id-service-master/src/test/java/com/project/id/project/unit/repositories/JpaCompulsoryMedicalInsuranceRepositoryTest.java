package com.project.id.project.unit.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.documents.entities.CompulsoryMedicalInsurance;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityCompulsoryMedicalInsuranceRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaCompulsoryMedicalInsuranceRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JpaCompulsoryMedicalInsuranceRepositoryTest {

    @Mock
    private EntityCompulsoryMedicalInsuranceRepository entityCompulsoryMedicalInsuranceRepository;

    @InjectMocks
    private JpaCompulsoryMedicalInsuranceRepository jpaCompulsoryMedicalInsuranceRepository;

    private CompulsoryMedicalInsurance compulsoryMedicalInsurance;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Assuming Id is a simple object, replace with real object if needed
        compulsoryMedicalInsurance = new CompulsoryMedicalInsurance();
        compulsoryMedicalInsurance.setName("cmi");
    }

    @Test
    public void testAddCompulsoryMedicalInsurance() {
        // Arrange
        when(entityCompulsoryMedicalInsuranceRepository.save(any(CompulsoryMedicalInsurance.class)))
                .thenReturn(compulsoryMedicalInsurance);

        // Act
        jpaCompulsoryMedicalInsuranceRepository.addCompulsoryMedicalInsurance(compulsoryMedicalInsurance);

        // Assert
        verify(entityCompulsoryMedicalInsuranceRepository, times(1))
                .save(compulsoryMedicalInsurance);
    }

    @Test
    public void testGetCompulsoryMedicalInsurance_Success() {
        // Arrange
        when(entityCompulsoryMedicalInsuranceRepository.findById(id))
                .thenReturn(java.util.Optional.of(compulsoryMedicalInsurance));

        // Act
        CompulsoryMedicalInsurance result = jpaCompulsoryMedicalInsuranceRepository.getCompulsoryMedicalInsurance(id);

        // Assert
        assertNotNull(result);
        assertEquals(compulsoryMedicalInsurance, result);
        verify(entityCompulsoryMedicalInsuranceRepository, times(1))
                .findById(id);
    }

    @Test
    public void testGetCompulsoryMedicalInsurance_ThrowsException() {
        // Arrange
        when(entityCompulsoryMedicalInsuranceRepository.findById(id))
                .thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaCompulsoryMedicalInsuranceRepository.getCompulsoryMedicalInsurance(id));
        assertEquals("Compulsory medical insurance not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdateCompulsoryMedicalInsurance() {
        // Arrange
        when(entityCompulsoryMedicalInsuranceRepository.save(any(CompulsoryMedicalInsurance.class)))
                .thenReturn(compulsoryMedicalInsurance);

        // Act
        jpaCompulsoryMedicalInsuranceRepository.updateCompulsoryMedicalInsurance(compulsoryMedicalInsurance);

        // Assert
        verify(entityCompulsoryMedicalInsuranceRepository, times(1))
                .save(compulsoryMedicalInsurance);
    }

    @Test
    public void testDeleteCompulsoryMedicalInsurance_Success() {
        // Arrange
        when(entityCompulsoryMedicalInsuranceRepository.existsById(id))
                .thenReturn(true);

        // Act
        jpaCompulsoryMedicalInsuranceRepository.deleteCompulsoryMedicalInsurance(id);

        // Assert
        verify(entityCompulsoryMedicalInsuranceRepository, times(1))
                .delete(id);
    }

    @Test
    public void testDeleteCompulsoryMedicalInsurance_ThrowsException() {
        // Arrange
        when(entityCompulsoryMedicalInsuranceRepository.existsById(id))
                .thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaCompulsoryMedicalInsuranceRepository.deleteCompulsoryMedicalInsurance(id));
        assertEquals("Compulsory medical insurance not found with ID: " + id, exception.getMessage());
    }
}
