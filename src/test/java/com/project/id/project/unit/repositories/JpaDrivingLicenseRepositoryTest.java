package com.project.id.project.unit.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.documents.entities.DrivingLicense;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityDrivingLicenseRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaDrivingLicenseRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JpaDrivingLicenseRepositoryTest {

    @Mock
    private EntityDrivingLicenseRepository entityDrivingLicenseRepository;

    @InjectMocks
    private JpaDrivingLicenseRepository jpaDrivingLicenseRepository;

    private DrivingLicense drivingLicense;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Assuming Id is a simple object, replace with real object if needed
        drivingLicense = new DrivingLicense();
        drivingLicense.setName("driving license");
    }

    @Test
    public void testAddDrivingLicense() {
        // Arrange
        when(entityDrivingLicenseRepository.save(any(DrivingLicense.class)))
                .thenReturn(drivingLicense);

        // Act
        jpaDrivingLicenseRepository.addDrivingLicense(drivingLicense);

        // Assert
        verify(entityDrivingLicenseRepository, times(1))
                .save(drivingLicense);
    }

    @Test
    public void testGetDrivingLicense_Success() {
        // Arrange
        when(entityDrivingLicenseRepository.findById(id))
                .thenReturn(java.util.Optional.of(drivingLicense));

        // Act
        DrivingLicense result = jpaDrivingLicenseRepository.getDrivingLicense(id);

        // Assert
        assertNotNull(result);
        assertEquals(drivingLicense, result);
        verify(entityDrivingLicenseRepository, times(1))
                .findById(id);
    }

    @Test
    public void testGetDrivingLicense_ThrowsException() {
        // Arrange
        when(entityDrivingLicenseRepository.findById(id))
                .thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaDrivingLicenseRepository.getDrivingLicense(id));
        assertEquals("Driving license not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdateDrivingLicense() {
        // Arrange
        when(entityDrivingLicenseRepository.save(any(DrivingLicense.class)))
                .thenReturn(drivingLicense);

        // Act
        jpaDrivingLicenseRepository.updateDrivingLicense(drivingLicense);

        // Assert
        verify(entityDrivingLicenseRepository, times(1))
                .save(drivingLicense);
    }

    @Test
    public void testDeleteDrivingLicense_Success() {
        // Arrange
        when(entityDrivingLicenseRepository.existsById(id))
                .thenReturn(true);

        // Act
        jpaDrivingLicenseRepository.deleteDrivingLicense(id);

        // Assert
        verify(entityDrivingLicenseRepository, times(1))
                .delete(id);
    }

    @Test
    public void testDeleteDrivingLicense_ThrowsException() {
        // Arrange
        when(entityDrivingLicenseRepository.existsById(id))
                .thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaDrivingLicenseRepository.deleteDrivingLicense(id));
        assertEquals("Driving license not found with ID: " + id, exception.getMessage());
    }
}
