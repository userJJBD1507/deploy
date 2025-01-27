package com.project.id.project.unit.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.documents.entities.ForeignPassport;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityForeignPassportRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaForeignPassportRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JpaForeignPassportRepositoryTest {

    @Mock
    private EntityForeignPassportRepository entityForeignPassportRepository;

    @InjectMocks
    private JpaForeignPassportRepository jpaForeignPassportRepository;

    private ForeignPassport foreignPassport;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Assuming Id is a simple object, replace with real object if needed
        foreignPassport = new ForeignPassport();
        foreignPassport.setName("foreign passport");
    }

    @Test
    public void testAddForeignPassport() {
        // Arrange
        when(entityForeignPassportRepository.save(any(ForeignPassport.class)))
                .thenReturn(foreignPassport);

        // Act
        jpaForeignPassportRepository.addForeignPassport(foreignPassport);

        // Assert
        verify(entityForeignPassportRepository, times(1))
                .save(foreignPassport);
    }

    @Test
    public void testGetForeignPassport_Success() {
        // Arrange
        when(entityForeignPassportRepository.findById(id))
                .thenReturn(java.util.Optional.of(foreignPassport));

        // Act
        ForeignPassport result = jpaForeignPassportRepository.getForeignPassport(id);

        // Assert
        assertNotNull(result);
        assertEquals(foreignPassport, result);
        verify(entityForeignPassportRepository, times(1))
                .findById(id);
    }

    @Test
    public void testGetForeignPassport_ThrowsException() {
        // Arrange
        when(entityForeignPassportRepository.findById(id))
                .thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaForeignPassportRepository.getForeignPassport(id));
        assertEquals("Foreign passport not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdateForeignPassport() {
        // Arrange
        when(entityForeignPassportRepository.save(any(ForeignPassport.class)))
                .thenReturn(foreignPassport);

        // Act
        jpaForeignPassportRepository.updateForeignPassport(foreignPassport);

        // Assert
        verify(entityForeignPassportRepository, times(1))
                .save(foreignPassport);
    }

    @Test
    public void testDeleteForeignPassport_Success() {
        // Arrange
        when(entityForeignPassportRepository.existsById(id))
                .thenReturn(true);

        // Act
        jpaForeignPassportRepository.deleteForeignPassport(id);

        // Assert
        verify(entityForeignPassportRepository, times(1))
                .delete(id);
    }

    @Test
    public void testDeleteForeignPassport_ThrowsException() {
        // Arrange
        when(entityForeignPassportRepository.existsById(id))
                .thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaForeignPassportRepository.deleteForeignPassport(id));
        assertEquals("Foreign passport not found with ID: " + id, exception.getMessage());
    }
}
