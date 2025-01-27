package com.project.id.project.unit.controllers.addresses;
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
import com.project.id.project.application.controllers.addresses.AdditionalAddressController;
import com.project.id.project.application.controllers.addresses.HomeAddressController;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.addresses.entities.HomeAddress;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;
import com.project.id.project.infrastructure.services.addresses.HomeAddressService;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;


@ExtendWith(MockitoExtension.class)
public class HomeAddressControllerTest {

    @Mock
    private HomeAddressService homeAddressService;

    @Mock
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @InjectMocks
    private HomeAddressController homeAddressController;

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
        HomeAddressDTO dto = new HomeAddressDTO();

        // Act
        ResponseEntity<Void> response = homeAddressController.create(dto);

        // Assert
        verify(homeAddressService).create(USERNAME, dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void read_ShouldReturnDto_WhenIdLinkedToUser() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        HomeAddressDTO dto = new HomeAddressDTO();
        when(entityPersonalDataRepository.findByInvocation(USERNAME))
                .thenReturn(Optional.of(createMockPersonalDataWithHomeId(id)));
        when(homeAddressService.read(id)).thenReturn(Optional.of(dto));

        // Act
        ResponseEntity<HomeAddressDTO> response = homeAddressController.read(id);

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
        ResponseEntity<HomeAddressDTO> response = homeAddressController.read(id);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void update_ShouldCallServiceAndReturnNoContent_WhenAuthorized() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        HomeAddressDTO dto = new HomeAddressDTO();
        PersonalData personalData = createMockPersonalDataWithHomeId(id);
        when(entityPersonalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.of(personalData));

        // Act
        ResponseEntity<Void> response = homeAddressController.update(id, dto);

        // Assert
        verify(homeAddressService).update(id, dto, personalData.getAddresses().getId());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void update_ShouldReturnNotFound_WhenPersonalDataNotFound() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        HomeAddressDTO dto = new HomeAddressDTO();
        when(entityPersonalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = homeAddressController.update(id, dto);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void delete_ShouldCallServiceAndReturnNoContent_WhenAuthorized() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        when(entityPersonalDataRepository.findByInvocation(USERNAME))
                .thenReturn(Optional.of(createMockPersonalDataWithHomeId(id)));

        // Act
        ResponseEntity<Void> response = homeAddressController.delete(id);

        // Assert
        verify(homeAddressService).delete(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void delete_ShouldReturnForbidden_WhenIdNotLinkedToUser() {
        // Arrange
        Id id = new Id(UUID.randomUUID());
        when(entityPersonalDataRepository.findByInvocation(USERNAME)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = homeAddressController.delete(id);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    // Вспомогательный метод для создания mock-объектов
    private PersonalData createMockPersonalDataWithHomeId(Id id) {
        HomeAddress homeAddress = new HomeAddress();
        homeAddress.setId(id);

        Addresses addresses = new Addresses();
        addresses.setHomeAddress(homeAddress);

        PersonalData personalData = new PersonalData();
        personalData.setAddresses(addresses);

        return personalData;
    }
}
