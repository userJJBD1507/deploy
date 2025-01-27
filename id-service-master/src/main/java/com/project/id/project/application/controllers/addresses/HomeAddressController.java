package com.project.id.project.application.controllers.addresses;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.DTOs.address.HomeAddressDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.HomeAddress;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.addresses.HomeAddressService;
import com.project.id.project.infrastructure.services.documents.PassportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Home Address Controller")
@RestController
@RequestMapping("/homeAddress")
public class HomeAddressController {

    private static final Logger logger = LoggerFactory.getLogger(HomeAddressController.class);

    @Autowired
    private HomeAddressService homeAddressService;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Operation(
	summary = "Создание домашнего адреса",
	description = "Позволяет добавить домашний адрес"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody HomeAddressDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to create home address for username: {}", username);

        homeAddressService.create(username, dto);
        logger.info("Successfully created home address for username: {}", username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
	summary = "Получение домашнего адреса",
	description = "Позволяет получить домашний адрес"
    )
    @GetMapping("/get")
    public ResponseEntity<HomeAddressDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to read home address with id: {} for username: {}", id, username);

        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("Access denied for username: {} to home address with id: {}", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<HomeAddressDTO> dto = homeAddressService.read(id);
        if (dto.isPresent()) {
            logger.info("Successfully retrieved home address with id: {} for username: {}", id, username);
        } else {
            logger.warn("Home address with id: {} not found for username: {}", id, username);
        }
        return dto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @Operation(
	summary = "Обновление домашнего адреса",
	description = "Позволяет обновить домашний адрес"
    )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody HomeAddressDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to update home address with id: {} for username: {}", id, username);

        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("Access denied for username: {} to home address with id: {}", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
        if (personalData.isEmpty()) {
            logger.warn("Personal data not found for username: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Id addressesId = personalData.get().getAddresses().getId();
        homeAddressService.update(id, dto, addressesId);
        logger.info("Successfully updated home address with id: {} for username: {}", id, username);
        return ResponseEntity.noContent().build();
    }
    @Operation(
	summary = "Удаление домашнего адреса",
	description = "Позволяет удалить домашний адрес"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to delete home address with id: {} for username: {}", id, username);

        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("Access denied for username: {} to home address with id: {}", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        homeAddressService.delete(id);
        logger.info("Successfully deleted home address with id: {} for username: {}", id, username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private boolean isIdLinkedToUsername(Id id, String username) {
        logger.debug("Checking if id: {} is linked to username: {}", id, username);
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("No personal data found for username: {}", username);
            return false;
        }

        HomeAddress homeAddress = personalData.get().getAddresses().getHomeAddress();
        boolean isLinked = homeAddress != null && homeAddress.getId().equals(id);

        logger.debug("Link check result for id: {} and username: {}: {}", id, username, isLinked);
        return isLinked;
    }
}
