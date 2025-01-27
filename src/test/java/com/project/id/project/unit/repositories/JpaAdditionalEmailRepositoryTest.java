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
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JpaAdditionalEmailRepositoryTest {

    @Mock
    private EntityAdditionalEmailRepository entityAdditionalEmailRepository;

    @InjectMocks
    private JpaAdditionalEmailRepository jpaAdditionalEmailRepository;

    private AdditionalEmail additionalEmail;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Assuming Id is a simple object, replace with real object if needed
        additionalEmail = new AdditionalEmail(id, "test@example.com");
    }

    @Test
    public void testAddAdditionalEmail() {
        // Arrange
        when(entityAdditionalEmailRepository.save(any(AdditionalEmail.class))).thenReturn(additionalEmail);

        // Act
        jpaAdditionalEmailRepository.addAdditionalEmail(additionalEmail);

        // Assert
        verify(entityAdditionalEmailRepository, times(1)).save(additionalEmail);
    }

    @Test
    public void testGetAdditionalEmail_Success() {
        // Arrange
        when(entityAdditionalEmailRepository.findById(id)).thenReturn(java.util.Optional.of(additionalEmail));

        // Act
        AdditionalEmail result = jpaAdditionalEmailRepository.getAdditionalEmail(id);

        // Assert
        assertNotNull(result);
        assertEquals(additionalEmail, result);
        verify(entityAdditionalEmailRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAdditionalEmail_ThrowsException() {
        // Arrange
        when(entityAdditionalEmailRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaAdditionalEmailRepository.getAdditionalEmail(id));
        assertEquals("Email not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdateAdditionalEmail() {
        // Arrange
        when(entityAdditionalEmailRepository.save(any(AdditionalEmail.class))).thenReturn(additionalEmail);

        // Act
        jpaAdditionalEmailRepository.updateAdditionalEmail(additionalEmail);

        // Assert
        verify(entityAdditionalEmailRepository, times(1)).save(additionalEmail);
    }

    @Test
    public void testDeleteAdditionalEmail_Success() {
        // Arrange
        when(entityAdditionalEmailRepository.existsById(id)).thenReturn(true);

        // Act
        jpaAdditionalEmailRepository.deleteAdditionalEmail(id);

        // Assert
        verify(entityAdditionalEmailRepository, times(1)).delete(id);
    }

    @Test
    public void testDeleteAdditionalEmail_ThrowsException() {
        // Arrange
        when(entityAdditionalEmailRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaAdditionalEmailRepository.deleteAdditionalEmail(id));
        assertEquals("Email not found with ID: " + id, exception.getMessage());
    }
}
