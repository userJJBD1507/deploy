package com.project.id.project.unit.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.documents.entities.Passport;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityPassportRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaPassportRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JpaPassportRepositoryTest {

    @Mock
    private EntityPassportRepository entityPassportRepository;

    @InjectMocks
    private JpaPassportRepository jpaPassportRepository;

    private Passport passport;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Assuming Id is a simple object, replace with real object if needed
        passport = new Passport();
        passport.setName("passport");
    }

    @Test
    public void testAddPassport() {
        // Arrange
        when(entityPassportRepository.save(any(Passport.class))).thenReturn(passport);

        // Act
        jpaPassportRepository.addPassport(passport);

        // Assert
        verify(entityPassportRepository, times(1)).save(passport);
    }

    @Test
    public void testGetPassport_Success() {
        // Arrange
        when(entityPassportRepository.findById(id)).thenReturn(java.util.Optional.of(passport));

        // Act
        Passport result = jpaPassportRepository.getPassport(id);

        // Assert
        assertNotNull(result);
        assertEquals(passport, result);
        verify(entityPassportRepository, times(1)).findById(id);
    }

    @Test
    public void testGetPassport_ThrowsException() {
        // Arrange
        when(entityPassportRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaPassportRepository.getPassport(id));
        assertEquals("Passport not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdatePassport() {
        // Arrange
        when(entityPassportRepository.save(any(Passport.class))).thenReturn(passport);

        // Act
        jpaPassportRepository.updatePassport(passport);

        // Assert
        verify(entityPassportRepository, times(1)).save(passport);
    }

    @Test
    public void testDeletePassport_Success() {
        // Arrange
        when(entityPassportRepository.existsById(id)).thenReturn(true);

        // Act
        jpaPassportRepository.deletePassport(id);

        // Assert
        verify(entityPassportRepository, times(1)).delete(id);
    }

    @Test
    public void testDeletePassport_ThrowsException() {
        // Arrange
        when(entityPassportRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaPassportRepository.deletePassport(id));
        assertEquals("Passport not found with ID: " + id, exception.getMessage());
    }
}
