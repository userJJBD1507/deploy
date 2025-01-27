package com.project.id.project.unit.controllers.addresses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.DTOs.address.HomeAddressDTO;
import com.project.id.project.application.DTOs.address.WorkAddressDTO;
import com.project.id.project.application.controllers.addresses.AdditionalAddressController;
import com.project.id.project.application.controllers.addresses.HomeAddressController;
import com.project.id.project.application.controllers.addresses.WorkAddressController;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.addresses.entities.HomeAddress;
import com.project.id.project.core.addresses.entities.WorkAddress;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;
import com.project.id.project.infrastructure.services.addresses.HomeAddressService;
import com.project.id.project.infrastructure.services.addresses.WorkAddressService;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;


@ExtendWith(MockitoExtension.class)
public class WorkAddressControllerTest {

    @Mock
    private WorkAddressService workAddressService;

    @Mock
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @InjectMocks
    private WorkAddressController workAddressController;

    @Mock
    private Logger logger;

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
        WorkAddressDTO dto = new WorkAddressDTO();

        // Act
        ResponseEntity<Void> response = workAddressController.create(dto);

        // Assert
        verify(workAddressService).create(USERNAME, dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void read_ShouldReturnDto_WhenIdLinkedToUser() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        WorkAddressDTO dto = new WorkAddressDTO();
        when(entityPersonalDataRepository.findByInvocation(USERNAME))
                .thenReturn(Optional.of(createMockPersonalDataWithWorkId(id)));
        when(workAddressService.read(id)).thenReturn(Optional.of(dto));

        // Act
        ResponseEntity<WorkAddressDTO> response = workAddressController.read(id);

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
        ResponseEntity<WorkAddressDTO> response = workAddressController.read(id);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void update_ShouldCallServiceAndReturnNoContent_WhenAuthorized() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        WorkAddressDTO dto = new WorkAddressDTO();
        PersonalData personalData = createMockPersonalDataWithWorkId(id);
        when(entityPersonalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = workAddressController.update(id, dto);

        // Assert
        verify(workAddressService).update(id, dto, personalData.getAddresses().getId());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void update_ShouldReturnNotFound_WhenPersonalDataNotFound() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        WorkAddressDTO dto = new WorkAddressDTO();
        when(entityPersonalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = workAddressController.update(id, dto);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void delete_ShouldCallServiceAndReturnNoContent_WhenAuthorized() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        when(entityPersonalDataRepository.findByInvocation(USERNAME))
                .thenReturn(Optional.of(createMockPersonalDataWithWorkId(id)));

        // Act
        ResponseEntity<Void> response = workAddressController.delete(id);

        // Assert
        verify(workAddressService).delete(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void delete_ShouldReturnForbidden_WhenIdNotLinkedToUser() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        when(entityPersonalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = workAddressController.delete(id);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    // Вспомогательный метод для создания mock-объектов
    private PersonalData createMockPersonalDataWithWorkId(Id id) {
        WorkAddress workAddress = new WorkAddress();
        workAddress.setId(id);

        Addresses addresses = new Addresses();
        addresses.setWorkAddress(workAddress);

        PersonalData personalData = new PersonalData();
        personalData.setAddresses(addresses);

        return personalData;
    }
}
