package com.project.id.project.unit.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.addresses.entities.WorkAddress;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityWorkAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaWorkAddressRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JpaWorkAddressRepositoryTest {

    @Mock
    private EntityWorkAddressRepository entityWorkAddressRepository;

    @InjectMocks
    private JpaWorkAddressRepository jpaWorkAddressRepository;

    private WorkAddress workAddress;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Assuming Id is a simple object, replace with real object if needed
        workAddress = new WorkAddress(id, "Test Work Name", "Test City", "Test Region", "Test Street", 10, 5, 3, 8, 123);
    }

    @Test
    public void testAddWorkAddress() {
        // Arrange
        when(entityWorkAddressRepository.save(any(WorkAddress.class))).thenReturn(workAddress);

        // Act
        jpaWorkAddressRepository.addWorkAddress(workAddress);

        // Assert
        verify(entityWorkAddressRepository, times(1)).save(workAddress);
    }

    @Test
    public void testGetWorkAddress_Success() {
        // Arrange
        when(entityWorkAddressRepository.findById(id)).thenReturn(java.util.Optional.of(workAddress));

        // Act
        WorkAddress result = jpaWorkAddressRepository.getWorkAddress(id);

        // Assert
        assertNotNull(result);
        assertEquals(workAddress, result);
        verify(entityWorkAddressRepository, times(1)).findById(id);
    }

    @Test
    public void testGetWorkAddress_ThrowsException() {
        // Arrange
        when(entityWorkAddressRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaWorkAddressRepository.getWorkAddress(id));
        assertEquals("WorkAddress not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdateWorkAddress() {
        // Arrange
        when(entityWorkAddressRepository.save(any(WorkAddress.class))).thenReturn(workAddress);

        // Act
        jpaWorkAddressRepository.updateWorkAddress(workAddress);

        // Assert
        verify(entityWorkAddressRepository, times(1)).save(workAddress);
    }

    @Test
    public void testDeleteWorkAddress_Success() {
        // Arrange
        when(entityWorkAddressRepository.existsById(id)).thenReturn(true);

        // Act
        jpaWorkAddressRepository.deleteWorkAddress(id);

        // Assert
        verify(entityWorkAddressRepository, times(1)).delete(id);
    }

    @Test
    public void testDeleteWorkAddress_ThrowsException() {
        // Arrange
        when(entityWorkAddressRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaWorkAddressRepository.deleteWorkAddress(id));
        assertEquals("WorkAddress not found with ID: " + id, exception.getMessage());
    }
}
