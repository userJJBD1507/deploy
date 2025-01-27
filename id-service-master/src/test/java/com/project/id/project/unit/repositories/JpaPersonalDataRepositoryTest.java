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
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaPersonalDataRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JpaPersonalDataRepositoryTest {

    @Mock
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @InjectMocks
    private JpaPersonalDataRepository jpaPersonalDataRepository;

    private PersonalData personalData;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Replace with actual ID object creation
        personalData = new PersonalData();
        personalData.setName("name");
    }

    @Test
    public void testAddPersonalData() {
        // Arrange
        when(entityPersonalDataRepository.save(any(PersonalData.class)))
                .thenReturn(personalData);

        // Act
        jpaPersonalDataRepository.addPersonalData(personalData);

        // Assert
        verify(entityPersonalDataRepository, times(1))
                .save(personalData);
    }

    @Test
    public void testGetPersonalData_Success() {
        // Arrange
        when(entityPersonalDataRepository.findById(id))
                .thenReturn(Optional.of(personalData));

        // Act
        PersonalData result = jpaPersonalDataRepository.getPersonalData(id);

        // Assert
        assertNotNull(result);
        assertEquals(personalData, result);
        verify(entityPersonalDataRepository, times(1))
                .findById(id);
    }

    @Test
    public void testGetPersonalData_ThrowsException() {
        // Arrange
        when(entityPersonalDataRepository.findById(id))
                .thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaPersonalDataRepository.getPersonalData(id));
        assertEquals("Personal data not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testDeletePersonalData_Success() {
        // Arrange
        when(entityPersonalDataRepository.existsById(id))
                .thenReturn(true);

        // Act
        jpaPersonalDataRepository.deletePersonalData(id);

        // Assert
        verify(entityPersonalDataRepository, times(1))
                .delete(id);
    }

    @Test
    public void testDeletePersonalData_ThrowsException() {
        // Arrange
        when(entityPersonalDataRepository.existsById(id))
                .thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaPersonalDataRepository.deletePersonalData(id));
        assertEquals("Personal data not found with ID: " + id, exception.getMessage());
    }
}
