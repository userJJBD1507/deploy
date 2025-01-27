package com.project.id.project.unit.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.phones.entities.AdditionalPhoneNumber;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalPhoneNumberRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalPhoneNumberRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JpaAdditionalPhoneNumberRepositoryTest {

    @Mock
    private EntityAdditionalPhoneNumberRepository entityAdditionalPhoneNumberRepository;

    @InjectMocks
    private JpaAdditionalPhoneNumberRepository jpaAdditionalPhoneNumberRepository;

    private AdditionalPhoneNumber additionalPhoneNumber;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Assuming Id is a simple object, replace with real object if needed
        additionalPhoneNumber = new AdditionalPhoneNumber(id, "1234567890");
    }

    @Test
    public void testAddAdditionalPhoneNumber() {
        // Arrange
        when(entityAdditionalPhoneNumberRepository.save(any(AdditionalPhoneNumber.class))).thenReturn(additionalPhoneNumber);

        // Act
        jpaAdditionalPhoneNumberRepository.addAdditionalPhoneNumber(additionalPhoneNumber);

        // Assert
        verify(entityAdditionalPhoneNumberRepository, times(1)).save(additionalPhoneNumber);
    }

    @Test
    public void testGetAdditionalPhoneNumber_Success() {
        // Arrange
        when(entityAdditionalPhoneNumberRepository.findById(id)).thenReturn(java.util.Optional.of(additionalPhoneNumber));

        // Act
        AdditionalPhoneNumber result = jpaAdditionalPhoneNumberRepository.getAdditionalPhoneNumber(id);

        // Assert
        assertNotNull(result);
        assertEquals(additionalPhoneNumber, result);
        verify(entityAdditionalPhoneNumberRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAdditionalPhoneNumber_ThrowsException() {
        // Arrange
        when(entityAdditionalPhoneNumberRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaAdditionalPhoneNumberRepository.getAdditionalPhoneNumber(id));
        assertEquals("Phone number not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdateAdditionalPhoneNumber() {
        // Arrange
        when(entityAdditionalPhoneNumberRepository.save(any(AdditionalPhoneNumber.class))).thenReturn(additionalPhoneNumber);

        // Act
        jpaAdditionalPhoneNumberRepository.updateAdditionalPhoneNumber(additionalPhoneNumber);

        // Assert
        verify(entityAdditionalPhoneNumberRepository, times(1)).save(additionalPhoneNumber);
    }

    @Test
    public void testDeleteAdditionalPhoneNumber_Success() {
        // Arrange
        when(entityAdditionalPhoneNumberRepository.existsById(id)).thenReturn(true);

        // Act
        jpaAdditionalPhoneNumberRepository.deleteAdditionalPhoneNumber(id);

        // Assert
        verify(entityAdditionalPhoneNumberRepository, times(1)).delete(id);
    }

    @Test
    public void testDeleteAdditionalPhoneNumber_ThrowsException() {
        // Arrange
        when(entityAdditionalPhoneNumberRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaAdditionalPhoneNumberRepository.deleteAdditionalPhoneNumber(id));
        assertEquals("Phone number not found with ID: " + id, exception.getMessage());
    }
}
