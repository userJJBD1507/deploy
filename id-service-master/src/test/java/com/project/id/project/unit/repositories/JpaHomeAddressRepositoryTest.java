package com.project.id.project.unit.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.addresses.entities.HomeAddress;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityHomeAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaHomeAddressRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JpaHomeAddressRepositoryTest {

    @Mock
    private EntityHomeAddressRepository entityHomeAddressRepository;

    @InjectMocks
    private JpaHomeAddressRepository jpaHomeAddressRepository;

    private HomeAddress homeAddress;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Assuming Id is a simple object, replace with real object if needed
        homeAddress = new HomeAddress(id, "Test Home Name", "Test City", "Test Region", "Test Street", 10, 5, 3, 8, 123);
    }

    @Test
    public void testAddHomeAddress() {
        // Arrange
        when(entityHomeAddressRepository.save(any(HomeAddress.class))).thenReturn(homeAddress);

        // Act
        jpaHomeAddressRepository.addHomeAddress(homeAddress);

        // Assert
        verify(entityHomeAddressRepository, times(1)).save(homeAddress);
    }

    @Test
    public void testGetHomeAddress_Success() {
        // Arrange
        when(entityHomeAddressRepository.findById(id)).thenReturn(java.util.Optional.of(homeAddress));

        // Act
        HomeAddress result = jpaHomeAddressRepository.getHomeAddress(id);

        // Assert
        assertNotNull(result);
        assertEquals(homeAddress, result);
        verify(entityHomeAddressRepository, times(1)).findById(id);
    }

    @Test
    public void testGetHomeAddress_ThrowsException() {
        // Arrange
        when(entityHomeAddressRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaHomeAddressRepository.getHomeAddress(id));
        assertEquals("Home address not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdateHomeAddress() {
        // Arrange
        when(entityHomeAddressRepository.save(any(HomeAddress.class))).thenReturn(homeAddress);

        // Act
        jpaHomeAddressRepository.updateHomeAddress(homeAddress);

        // Assert
        verify(entityHomeAddressRepository, times(1)).save(homeAddress);
    }

    @Test
    public void testDeleteHomeAddress_Success() {
        // Arrange
        when(entityHomeAddressRepository.existsById(id)).thenReturn(true);

        // Act
        jpaHomeAddressRepository.deleteHomeAddress(id);

        // Assert
        verify(entityHomeAddressRepository, times(1)).delete(id);
    }

    @Test
    public void testDeleteHomeAddress_ThrowsException() {
        // Arrange
        when(entityHomeAddressRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaHomeAddressRepository.deleteHomeAddress(id));
        assertEquals("Home address not found with ID: " + id, exception.getMessage());
    }
}
