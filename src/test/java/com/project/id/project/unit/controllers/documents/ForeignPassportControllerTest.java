package com.project.id.project.unit.controllers.documents;

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
import com.project.id.project.application.DTOs.documents.ForeignPassportDTO;
import com.project.id.project.application.controllers.addresses.AdditionalAddressController;
import com.project.id.project.application.controllers.addresses.HomeAddressController;
import com.project.id.project.application.controllers.documents.BirthCertificateController;
import com.project.id.project.application.controllers.documents.ForeignPassportController;
import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.addresses.entities.HomeAddress;
import com.project.id.project.core.documents.entities.BirthCertificate;
import com.project.id.project.core.documents.entities.ForeignPassport;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;
import com.project.id.project.infrastructure.services.addresses.HomeAddressService;
import com.project.id.project.infrastructure.services.documents.BirthCertificateService;
import com.project.id.project.infrastructure.services.documents.ForeignPassportService;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;


@ExtendWith(MockitoExtension.class)
class ForeignPassportControllerTest {

    @Mock
    private ForeignPassportService foreignPassportService;

    @Mock
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @InjectMocks
    private ForeignPassportController foreignPassportController;

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
        ForeignPassportDTO dto = new ForeignPassportDTO();

        // Act
        ResponseEntity<Void> response = foreignPassportController.create(dto);

        // Assert
        verify(foreignPassportService).create(USERNAME, dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void read_ShouldReturnDto_WhenIdLinkedToUser() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        ForeignPassportDTO dto = new ForeignPassportDTO();
        when(entityPersonalDataRepository.findByInvocation(USERNAME))
                .thenReturn(Optional.of(createMockPersonalDataWithDocumentId(id)));
        when(foreignPassportService.read(id)).thenReturn(Optional.of(dto));

        // Act
        ResponseEntity<ForeignPassportDTO> response = foreignPassportController.read(id);

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
        ResponseEntity<ForeignPassportDTO> response = foreignPassportController.read(id);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void update_ShouldCallServiceAndReturnNoContent_WhenAuthorized() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        ForeignPassportDTO dto = new ForeignPassportDTO();
        PersonalData personalData = createMockPersonalDataWithDocumentId(id);
        when(entityPersonalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = foreignPassportController.update(id, dto);

        // Assert
        verify(foreignPassportService).update(id, dto, personalData.getDocuments().getId());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void update_ShouldReturnNotFound_WhenPersonalDataNotFound() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        ForeignPassportDTO dto = new ForeignPassportDTO();
        when(entityPersonalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = foreignPassportController.update(id, dto);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void delete_ShouldCallServiceAndReturnNoContent_WhenAuthorized() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        when(entityPersonalDataRepository.findByInvocation(USERNAME))
                .thenReturn(Optional.of(createMockPersonalDataWithDocumentId(id)));

        // Act
        ResponseEntity<Void> response = foreignPassportController.delete(id);

        // Assert
        verify(foreignPassportService).delete(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void delete_ShouldReturnForbidden_WhenIdNotLinkedToUser() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        when(entityPersonalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = foreignPassportController.delete(id);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    // Вспомогательный метод для создания mock-объектов
    private PersonalData createMockPersonalDataWithDocumentId(Id id) {
        ForeignPassport foreignPassport = new ForeignPassport();
        foreignPassport.setId(id);

        Documents documents = new Documents();
        documents.setForeignPassport(foreignPassport);

        PersonalData personalData = new PersonalData();
        personalData.setDocuments(documents);

        return personalData;
    }
}
