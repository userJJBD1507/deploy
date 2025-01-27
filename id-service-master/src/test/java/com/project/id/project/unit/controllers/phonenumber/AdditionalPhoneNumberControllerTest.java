package com.project.id.project.unit.controllers.phonenumber;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.DTOs.address.HomeAddressDTO;
import com.project.id.project.application.DTOs.documents.BirthCertificateDTO;
import com.project.id.project.application.DTOs.documents.DrivingLicenseDTO;
import com.project.id.project.application.DTOs.phonenumbers.AdditionalPhoneNumberDTO;
import com.project.id.project.application.controllers.addresses.AdditionalAddressController;
import com.project.id.project.application.controllers.addresses.HomeAddressController;
import com.project.id.project.application.controllers.documents.BirthCertificateController;
import com.project.id.project.application.controllers.documents.DrivingLicenseController;
import com.project.id.project.application.controllers.phonenumbers.AdditionalPhoneNumberController;
import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.addresses.entities.HomeAddress;
import com.project.id.project.core.documents.entities.BirthCertificate;
import com.project.id.project.core.documents.entities.DrivingLicense;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.phones.entities.AdditionalPhoneNumber;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;
import com.project.id.project.infrastructure.services.addresses.HomeAddressService;
import com.project.id.project.infrastructure.services.documents.BirthCertificateService;
import com.project.id.project.infrastructure.services.documents.DrivingLicenseService;
import com.project.id.project.infrastructure.services.phonenumber.AdditionalPhoneNumberService;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;


@ExtendWith(MockitoExtension.class)
public class AdditionalPhoneNumberControllerTest {

    @Mock
    private AdditionalPhoneNumberService additionalPhoneNumberService;

    @Mock
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @InjectMocks
    private AdditionalPhoneNumberController controller;

    private final String USERNAME = "testUser";

    @BeforeEach
    void setUpSecurityContext() {
        Jwt jwt = Mockito.mock(Jwt.class);
        when(jwt.getClaimAsString("preferred_username")).thenReturn(USERNAME);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void create_ShouldCallServiceAndReturnCreatedStatus() {
        // Arrange
        AdditionalPhoneNumberDTO dto = new AdditionalPhoneNumberDTO();

        // Act
        ResponseEntity<Void> response = controller.create(dto);

        // Assert
        verify(additionalPhoneNumberService).create(USERNAME, dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void read_ShouldReturnDto_WhenIdLinkedToUser() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        AdditionalPhoneNumberDTO dto = new AdditionalPhoneNumberDTO();
        when(entityPersonalDataRepository.findByInvocation(USERNAME))
                .thenReturn(Optional.of(createMockPersonalDataWithPhoneNumber(id)));
        when(additionalPhoneNumberService.read(id)).thenReturn(Optional.of(dto));

        // Act
        ResponseEntity<AdditionalPhoneNumberDTO> response = controller.read(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void read_ShouldReturnForbidden_WhenIdNotLinkedToUser() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        when(entityPersonalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<AdditionalPhoneNumberDTO> response = controller.read(id);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void update_ShouldCallServiceAndReturnNoContent_WhenAuthorized() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        AdditionalPhoneNumberDTO dto = new AdditionalPhoneNumberDTO();
        PersonalData personalData = createMockPersonalDataWithPhoneNumber(id);
        when(entityPersonalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.update(id, dto);

        // Assert
        verify(additionalPhoneNumberService).update(id, dto, personalData.getId());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void update_ShouldReturnForbidden_WhenIdNotLinkedToUser() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        AdditionalPhoneNumberDTO dto = new AdditionalPhoneNumberDTO();
        when(entityPersonalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = controller.update(id, dto);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void delete_ShouldCallServiceAndReturnNoContent_WhenAuthorized() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        when(entityPersonalDataRepository.findByInvocation(USERNAME))
                .thenReturn(Optional.of(createMockPersonalDataWithPhoneNumber(id)));

        // Act
        ResponseEntity<Void> response = controller.delete(id);

        // Assert
        verify(additionalPhoneNumberService).delete(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void delete_ShouldReturnForbidden_WhenIdNotLinkedToUser() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        when(entityPersonalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = controller.delete(id);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    // Вспомогательный метод для создания mock-объектов
    private PersonalData createMockPersonalDataWithPhoneNumber(Id id) {
        AdditionalPhoneNumber additionalPhoneNumber = new AdditionalPhoneNumber();
        additionalPhoneNumber.setId(id);

        List<AdditionalPhoneNumber> phoneNumberList = new ArrayList<>();
        phoneNumberList.add(additionalPhoneNumber);

        PersonalData personalData = new PersonalData();
        personalData.setAdditionalPhoneNumberList(phoneNumberList);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }
}
