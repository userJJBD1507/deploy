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
import com.project.id.project.core.emails.entities.PersonalEmail;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaPersonalEmailRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JpaPersonalEmailRepositoryTest {

    @Mock
    private EntityPersonalEmailRepository entityPersonalEmailRepository;

    @InjectMocks
    private JpaPersonalEmailRepository jpaPersonalEmailRepository;

    private PersonalEmail personalEmail;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Assuming Id is a simple object, replace with real object if needed
        personalEmail = new PersonalEmail(id, "test@example.com");
    }

    @Test
    public void testAddPersonalEmail() {
        // Arrange
        when(entityPersonalEmailRepository.save(any(PersonalEmail.class))).thenReturn(personalEmail);

        // Act
        jpaPersonalEmailRepository.addPersonalEmail(personalEmail);

        // Assert
        verify(entityPersonalEmailRepository, times(1)).save(personalEmail);
    }

    @Test
    public void testGetPersonalEmail_Success() {
        // Arrange
        when(entityPersonalEmailRepository.findById(id)).thenReturn(java.util.Optional.of(personalEmail));

        // Act
        PersonalEmail result = jpaPersonalEmailRepository.getPersonalEmail(id);

        // Assert
        assertNotNull(result);
        assertEquals(personalEmail, result);
        verify(entityPersonalEmailRepository, times(1)).findById(id);
    }

    @Test
    public void testGetPersonalEmail_ThrowsException() {
        // Arrange
        when(entityPersonalEmailRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaPersonalEmailRepository.getPersonalEmail(id));
        assertEquals("Personal email not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdatePersonalEmail() {
        // Arrange
        when(entityPersonalEmailRepository.save(any(PersonalEmail.class))).thenReturn(personalEmail);

        // Act
        jpaPersonalEmailRepository.updatePersonalEmail(personalEmail);

        // Assert
        verify(entityPersonalEmailRepository, times(1)).save(personalEmail);
    }

    @Test
    public void testDeletePersonalEmail_Success() {
        // Arrange
        when(entityPersonalEmailRepository.existsById(id)).thenReturn(true);

        // Act
        jpaPersonalEmailRepository.deletePersonalEmail(id);

        // Assert
        verify(entityPersonalEmailRepository, times(1)).delete(id);
    }

    @Test
    public void testDeletePersonalEmail_ThrowsException() {
        // Arrange
        when(entityPersonalEmailRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaPersonalEmailRepository.deletePersonalEmail(id));
        assertEquals("Personal email not found with ID: " + id, exception.getMessage());
    }
}
