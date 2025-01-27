package com.project.id.project.unit.controllers.email;

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
import com.project.id.project.application.DTOs.emails.AdditionalEmailDTO;
import com.project.id.project.application.controllers.addresses.AdditionalAddressController;
import com.project.id.project.application.controllers.addresses.HomeAddressController;
import com.project.id.project.application.controllers.documents.BirthCertificateController;
import com.project.id.project.application.controllers.documents.DrivingLicenseController;
import com.project.id.project.application.controllers.emails.AdditionalEmailController;
import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.addresses.entities.HomeAddress;
import com.project.id.project.core.documents.entities.BirthCertificate;
import com.project.id.project.core.documents.entities.DrivingLicense;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;
import com.project.id.project.infrastructure.services.addresses.HomeAddressService;
import com.project.id.project.infrastructure.services.documents.BirthCertificateService;
import com.project.id.project.infrastructure.services.documents.DrivingLicenseService;
import com.project.id.project.infrastructure.services.emails.AdditionalEmailService;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;



@ExtendWith(MockitoExtension.class)
public class AdditionalEmailControllerTest {

    @Mock
    private AdditionalEmailService additionalEmailService;

    @Mock
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @InjectMocks
    private AdditionalEmailController controller;

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
        AdditionalEmailDTO dto = new AdditionalEmailDTO();

        // Act
        ResponseEntity<Void> response = controller.create(dto);

        // Assert
        verify(additionalEmailService).create(USERNAME, dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void read_ShouldReturnDto_WhenIdLinkedToUser() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        AdditionalEmailDTO dto = new AdditionalEmailDTO();
        when(entityPersonalDataRepository.findByInvocation(USERNAME))
                .thenReturn(Optional.of(createMockPersonalDataWithEmail(id)));
        when(additionalEmailService.read(id)).thenReturn(Optional.of(dto));

        // Act
        ResponseEntity<AdditionalEmailDTO> response = controller.read(id);

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
        ResponseEntity<AdditionalEmailDTO> response = controller.read(id);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void update_ShouldCallServiceAndReturnNoContent_WhenAuthorized() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        AdditionalEmailDTO dto = new AdditionalEmailDTO();
        PersonalData personalData = createMockPersonalDataWithEmail(id);
        when(entityPersonalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = controller.update(id, dto);

        // Assert
        verify(additionalEmailService).update(id, dto, personalData.getId());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void update_ShouldReturnNotFound_WhenPersonalDataNotFound() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        AdditionalEmailDTO dto = new AdditionalEmailDTO();
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
                .thenReturn(Optional.of(createMockPersonalDataWithEmail(id)));

        // Act
        ResponseEntity<Void> response = controller.delete(id);

        // Assert
        verify(additionalEmailService).delete(id);
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
    private PersonalData createMockPersonalDataWithEmail(Id id) {
        AdditionalEmail additionalEmail = new AdditionalEmail();
        additionalEmail.setId(id);

        List<AdditionalEmail> emailList = new ArrayList<>();
        emailList.add(additionalEmail);

        PersonalData personalData = new PersonalData();
        personalData.setAdditionalEmailList(emailList);
        personalData.setId(new Id(UUID.randomUUID()));

        return personalData;
    }
}
