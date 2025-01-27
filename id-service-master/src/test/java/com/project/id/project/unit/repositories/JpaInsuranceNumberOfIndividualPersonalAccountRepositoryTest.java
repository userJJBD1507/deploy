package com.project.id.project.unit.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.documents.entities.InsuranceNumberOfIndividualPersonalAccount;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityInsuranceNumberOfIndividualPersonalAccountRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaInsuranceNumberOfIndividualPersonalAccountRepository;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JpaInsuranceNumberOfIndividualPersonalAccountRepositoryTest {

    @Mock
    private EntityInsuranceNumberOfIndividualPersonalAccountRepository entityInsuranceNumberOfIndividualPersonalAccountRepository;

    @InjectMocks
    private JpaInsuranceNumberOfIndividualPersonalAccountRepository jpaInsuranceNumberRepository;

    private InsuranceNumberOfIndividualPersonalAccount insuranceNumber;
    private Id id;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        id = new Id(); // Assuming Id is a simple object, replace with real object if needed
        insuranceNumber = new InsuranceNumberOfIndividualPersonalAccount();
        insuranceNumber.setName("inoipa");
    }

    @Test
    public void testAddInsuranceNumber() {
        // Arrange
        when(entityInsuranceNumberOfIndividualPersonalAccountRepository.save(any(InsuranceNumberOfIndividualPersonalAccount.class)))
                .thenReturn(insuranceNumber);

        // Act
        jpaInsuranceNumberRepository.addInsuranceNumberOfIndividualPersonalAccount(insuranceNumber);

        // Assert
        verify(entityInsuranceNumberOfIndividualPersonalAccountRepository, times(1))
                .save(insuranceNumber);
    }

    @Test
    public void testGetInsuranceNumber_Success() {
        // Arrange
        when(entityInsuranceNumberOfIndividualPersonalAccountRepository.findById(id))
                .thenReturn(java.util.Optional.of(insuranceNumber));

        // Act
        InsuranceNumberOfIndividualPersonalAccount result = jpaInsuranceNumberRepository.getInsuranceNumberOfIndividualPersonalAccount(id);

        // Assert
        assertNotNull(result);
        assertEquals(insuranceNumber, result);
        verify(entityInsuranceNumberOfIndividualPersonalAccountRepository, times(1))
                .findById(id);
    }

    @Test
    public void testGetInsuranceNumber_ThrowsException() {
        // Arrange
        when(entityInsuranceNumberOfIndividualPersonalAccountRepository.findById(id))
                .thenReturn(java.util.Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaInsuranceNumberRepository.getInsuranceNumberOfIndividualPersonalAccount(id));
        assertEquals("Insurance number not found with ID: " + id, exception.getMessage());
    }

    @Test
    public void testUpdateInsuranceNumber() {
        // Arrange
        when(entityInsuranceNumberOfIndividualPersonalAccountRepository.save(any(InsuranceNumberOfIndividualPersonalAccount.class)))
                .thenReturn(insuranceNumber);

        // Act
        jpaInsuranceNumberRepository.updateInsuranceNumberOfIndividualPersonalAccount(insuranceNumber);

        // Assert
        verify(entityInsuranceNumberOfIndividualPersonalAccountRepository, times(1))
                .save(insuranceNumber);
    }

    @Test
    public void testDeleteInsuranceNumber_Success() {
        // Arrange
        when(entityInsuranceNumberOfIndividualPersonalAccountRepository.existsById(id))
                .thenReturn(true);

        // Act
        jpaInsuranceNumberRepository.deleteInsuranceNumberOfIndividualPersonalAccount(id);

        // Assert
        verify(entityInsuranceNumberOfIndividualPersonalAccountRepository, times(1))
                .delete(id);
    }

    @Test
    public void testDeleteInsuranceNumber_ThrowsException() {
        // Arrange
        when(entityInsuranceNumberOfIndividualPersonalAccountRepository.existsById(id))
                .thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> jpaInsuranceNumberRepository.deleteInsuranceNumberOfIndividualPersonalAccount(id));
        assertEquals("Insurance number not found with ID: " + id, exception.getMessage());
    }
}
