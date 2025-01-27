package com.project.id.project.unit.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.documents.entities.BirthCertificate;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityBirthCertificateRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaBirthCertificateRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class JpaBirthCertificateRepositoryTest {

    @Mock
    private EntityBirthCertificateRepository entityBirthCertificateRepository;

    @InjectMocks
    private JpaBirthCertificateRepository jpaBirthCertificateRepository;

    private BirthCertificate birthCertificate;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Assuming Id is a simple object, replace with real object if needed
        birthCertificate = new BirthCertificate();
        birthCertificate.setName("birth certificate");
    }

    @Test
    public void testAddBirthCertificate() {
        // Arrange
        when(entityBirthCertificateRepository.save(any(BirthCertificate.class)))
                .thenReturn(birthCertificate);

        // Act
        jpaBirthCertificateRepository.addBirthCertificate(birthCertificate);

        // Assert
        verify(entityBirthCertificateRepository, times(1))
                .save(birthCertificate);
    }

    @Test
    public void testGetBirthCertificate_Success() {
        // Arrange
        when(entityBirthCertificateRepository.findById(id))
                .thenReturn(java.util.Optional.of(birthCertificate));

        // Act
        BirthCertificate result = jpaBirthCertificateRepository.getBirthCertificate(id);

        // Assert
        assertNotNull(result);
        assertEquals(birthCertificate, result);
        verify(entityBirthCertificateRepository, times(1))
                .findById(id);
    }

    @Test
    public void testGetBirthCertificate_ThrowsException() {
        // Arrange
        when(entityBirthCertificateRepository.findById(id))
                .thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaBirthCertificateRepository.getBirthCertificate(id));
        assertEquals("Birth certificate not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdateBirthCertificate() {
        // Arrange
        when(entityBirthCertificateRepository.save(any(BirthCertificate.class)))
                .thenReturn(birthCertificate);

        // Act
        jpaBirthCertificateRepository.updateBirthCertificate(birthCertificate);

        // Assert
        verify(entityBirthCertificateRepository, times(1))
                .save(birthCertificate);
    }

    @Test
    public void testDeleteBirthCertificate_Success() {
        // Arrange
        when(entityBirthCertificateRepository.existsById(id))
                .thenReturn(true);

        // Act
        jpaBirthCertificateRepository.deleteBirthCertificate(id);

        // Assert
        verify(entityBirthCertificateRepository, times(1))
                .delete(id);
    }

    @Test
    public void testDeleteBirthCertificate_ThrowsException() {
        // Arrange
        when(entityBirthCertificateRepository.existsById(id))
                .thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaBirthCertificateRepository.deleteBirthCertificate(id));
        assertEquals("Birth certificate not found with ID: " + id, exception.getMessage());
    }
}
