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
import com.project.id.project.core.phones.entities.PhoneNumber;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityPhoneNumberRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaPhoneNumberRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JpaPhoneNumberRepositoryTest {

    @Mock
    private EntityPhoneNumberRepository entityPhoneNumberRepository;

    @InjectMocks
    private JpaPhoneNumberRepository jpaPhoneNumberRepository;

    private PhoneNumber phoneNumber;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Assuming Id is a simple object, replace with real object if needed
        phoneNumber = new PhoneNumber(id, "1234567890");
    }

    @Test
    public void testAddPhoneNumber() {
        // Arrange
        when(entityPhoneNumberRepository.save(any(PhoneNumber.class))).thenReturn(phoneNumber);

        // Act
        jpaPhoneNumberRepository.addPhoneNumber(phoneNumber);

        // Assert
        verify(entityPhoneNumberRepository, times(1)).save(phoneNumber);
    }

    @Test
    public void testGetPhoneNumber_Success() {
        // Arrange
        when(entityPhoneNumberRepository.findById(id)).thenReturn(java.util.Optional.of(phoneNumber));

        // Act
        PhoneNumber result = jpaPhoneNumberRepository.getPhoneNumber(id);

        // Assert
        assertNotNull(result);
        assertEquals(phoneNumber, result);
        verify(entityPhoneNumberRepository, times(1)).findById(id);
    }

    @Test
    public void testGetPhoneNumber_ThrowsException() {
        // Arrange
        when(entityPhoneNumberRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaPhoneNumberRepository.getPhoneNumber(id));
        assertEquals("Phone number not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdatePhoneNumber() {
        // Arrange
        when(entityPhoneNumberRepository.save(any(PhoneNumber.class))).thenReturn(phoneNumber);

        // Act
        jpaPhoneNumberRepository.updatePhoneNumber(phoneNumber);

        // Assert
        verify(entityPhoneNumberRepository, times(1)).save(phoneNumber);
    }

    @Test
    public void testDeletePhoneNumber_Success() {
        // Arrange
        when(entityPhoneNumberRepository.existsById(id)).thenReturn(true);

        // Act
        jpaPhoneNumberRepository.deletePhoneNumber(id);

        // Assert
        verify(entityPhoneNumberRepository, times(1)).delete(id);
    }

    @Test
    public void testDeletePhoneNumber_ThrowsException() {
        // Arrange
        when(entityPhoneNumberRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaPhoneNumberRepository.deletePhoneNumber(id));
        assertEquals("Phone number not found with ID: " + id, exception.getMessage());
    }
}
