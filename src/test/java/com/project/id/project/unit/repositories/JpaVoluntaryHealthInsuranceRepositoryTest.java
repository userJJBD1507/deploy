package com.project.id.project.unit.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.documents.entities.VoluntaryHealthInsurance;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityVoluntaryHealthInsuranceRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaVoluntaryHealthInsuranceRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JpaVoluntaryHealthInsuranceRepositoryTest {

    @Mock
    private EntityVoluntaryHealthInsuranceRepository entityVoluntaryHealthInsuranceRepository;

    @InjectMocks
    private JpaVoluntaryHealthInsuranceRepository jpaVoluntaryHealthInsuranceRepository;

    private VoluntaryHealthInsurance voluntaryHealthInsurance;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Assuming Id is a simple object, replace with real object if needed
        voluntaryHealthInsurance = new VoluntaryHealthInsurance();
        voluntaryHealthInsurance.setName("Test insurance");
    }

    @Test
    public void testAddVoluntaryHealthInsurance() {
        // Arrange
        when(entityVoluntaryHealthInsuranceRepository.save(any(VoluntaryHealthInsurance.class))).thenReturn(voluntaryHealthInsurance);

        // Act
        jpaVoluntaryHealthInsuranceRepository.addVoluntaryHealthInsurance(voluntaryHealthInsurance);

        // Assert
        verify(entityVoluntaryHealthInsuranceRepository, times(1)).save(voluntaryHealthInsurance);
    }

    @Test
    public void testGetVoluntaryHealthInsurance_Success() {
        // Arrange
        when(entityVoluntaryHealthInsuranceRepository.findById(id)).thenReturn(java.util.Optional.of(voluntaryHealthInsurance));

        // Act
        VoluntaryHealthInsurance result = jpaVoluntaryHealthInsuranceRepository.getVoluntaryHealthInsurance(id);

        // Assert
        assertNotNull(result);
        assertEquals(voluntaryHealthInsurance, result);
        verify(entityVoluntaryHealthInsuranceRepository, times(1)).findById(id);
    }

    @Test
    public void testGetVoluntaryHealthInsurance_ThrowsException() {
        // Arrange
        when(entityVoluntaryHealthInsuranceRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaVoluntaryHealthInsuranceRepository.getVoluntaryHealthInsurance(id));
        assertEquals("VoluntaryHealthInsurance not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdateVoluntaryHealthInsurance() {
        // Arrange
        when(entityVoluntaryHealthInsuranceRepository.save(any(VoluntaryHealthInsurance.class))).thenReturn(voluntaryHealthInsurance);

        // Act
        jpaVoluntaryHealthInsuranceRepository.updateVoluntaryHealthInsurance(voluntaryHealthInsurance);

        // Assert
        verify(entityVoluntaryHealthInsuranceRepository, times(1)).save(voluntaryHealthInsurance);
    }

    @Test
    public void testDeleteVoluntaryHealthInsurance_Success() {
        // Arrange
        when(entityVoluntaryHealthInsuranceRepository.existsById(id)).thenReturn(true);

        // Act
        jpaVoluntaryHealthInsuranceRepository.deleteVoluntaryHealthInsurance(id);

        // Assert
        verify(entityVoluntaryHealthInsuranceRepository, times(1)).delete(id);
    }

    @Test
    public void testDeleteVoluntaryHealthInsurance_ThrowsException() {
        // Arrange
        when(entityVoluntaryHealthInsuranceRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaVoluntaryHealthInsuranceRepository.deleteVoluntaryHealthInsurance(id));
        assertEquals("VoluntaryHealthInsurance not found with ID: " + id, exception.getMessage());
    }
}
