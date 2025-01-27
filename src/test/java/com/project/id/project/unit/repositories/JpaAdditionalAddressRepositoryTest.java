package com.project.id.project.unit.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.EmptyResultDataAccessException;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class JpaAdditionalAddressRepositoryTest {

    @Mock
    private EntityAdditionalAddressRepository entityAdditionalAddressRepository;

    @InjectMocks
    private JpaAdditionalAddressRepository jpaAdditionalAddressRepository;

    private AdditionalAddress additionalAddress;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id();
        additionalAddress = new AdditionalAddress(id, "Test Name", "Test City", "Test Region", "Test Street", 10, 5, 3, 8, 123);
    }

    @Test
    public void testAddAdditionalAddress() {
        // Arrange
        when(entityAdditionalAddressRepository.save(any(AdditionalAddress.class))).thenReturn(additionalAddress);

        // Act
        jpaAdditionalAddressRepository.addAdditionalAddress(additionalAddress);

        // Assert
        verify(entityAdditionalAddressRepository, times(1)).save(additionalAddress);
    }

    @Test
    public void testGetAdditionalAddress_Success() {
        // Arrange
        when(entityAdditionalAddressRepository.findById(id)).thenReturn(java.util.Optional.of(additionalAddress));

        // Act
        AdditionalAddress result = jpaAdditionalAddressRepository.getAdditionalAddress(id);

        // Assert
        assertNotNull(result);
        assertEquals(additionalAddress, result);
        verify(entityAdditionalAddressRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAdditionalAddress_ThrowsException() {
        // Arrange
        when(entityAdditionalAddressRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaAdditionalAddressRepository.getAdditionalAddress(id));
        assertEquals("Address not found", exception.getMessage());
    }

    @Test
    public void testUpdateAdditionalAddress() {
        // Arrange
        when(entityAdditionalAddressRepository.save(any(AdditionalAddress.class))).thenReturn(additionalAddress);

        // Act
        jpaAdditionalAddressRepository.updateAdditionalAddress(additionalAddress);

        // Assert
        verify(entityAdditionalAddressRepository, times(1)).save(additionalAddress);
    }

    @Test
    public void testDeleteAdditionalAddress_Success() {
        // Arrange
        when(entityAdditionalAddressRepository.existsById(id)).thenReturn(true);

        // Act
        jpaAdditionalAddressRepository.deleteAdditionalAddress(id);

        // Assert
        verify(entityAdditionalAddressRepository, times(1)).delete(id);
    }

    @Test
    public void testDeleteAdditionalAddress_ThrowsException() {
        // Arrange
        when(entityAdditionalAddressRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaAdditionalAddressRepository.deleteAdditionalAddress(id));
        assertEquals("Address not found", exception.getMessage());
    }
}
