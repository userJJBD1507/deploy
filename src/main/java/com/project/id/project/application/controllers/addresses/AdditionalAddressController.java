package com.project.id.project.application.controllers.addresses;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;
import com.project.id.project.infrastructure.services.documents.PassportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Map;

@Tag(name = "Additional Address Controller")
@RestController
@RequestMapping("/additionalAddress")
public class AdditionalAddressController {

    private static final Logger logger = LoggerFactory.getLogger(AdditionalAddressController.class);

    @Autowired
    private AdditionalAddressService additionalAddressService;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Operation(
	summary = "Создание дополнительного адреса",
	description = "Позволяет добавить дополнительный адрес"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody AdditionalAddressDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        logger.info("Received request to create additional address for username: {}", username);
        additionalAddressService.create(username, dto);
        logger.info("Successfully created additional address for username: {}", username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
	summary = "Получение дополнительного адреса",
	description = "Позволяет получить дополнительный адрес"
    )
    @GetMapping("/get")
    public ResponseEntity<AdditionalAddressDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to read additional address with id: {} for username: {}", id, username);

        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("Access denied for username: {} to additional address with id: {}", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<AdditionalAddressDTO> dto = additionalAddressService.read(id);
        if (dto.isPresent()) {
            logger.info("Successfully retrieved additional address with id: {} for username: {}", id, username);
        } else {
            logger.warn("Additional address with id: {} not found for username: {}", id, username);
        }
        return dto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @Operation(
	summary = "Обновление дополнительного адреса",
	description = "Позволяет обновить дополнительный адрес"
    )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody AdditionalAddressDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to update additional address with id: {} for username: {}", id, username);
        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("Access denied for username: {} to additional address with id: {}", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
        if (personalData.isEmpty()) {
            logger.warn("Personal data not found for username: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Id addressesId = personalData.get().getAddresses().getId();
        additionalAddressService.update(id, dto, addressesId);
        logger.info("Successfully updated additional address with id: {} for username: {}", id, username);
        return ResponseEntity.noContent().build();
    }
    @Operation(
	summary = "Удаление дополнительного адреса",
	description = "Позволяет удалить дополнительный адрес"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to delete additional address with id: {} for username: {}", id, username);

        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("Access denied for username: {} to additional address with id: {}", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        additionalAddressService.delete(id);
        logger.info("Successfully deleted additional address with id: {} for username: {}", id, username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private boolean isIdLinkedToUsername(Id id, String username) {
        logger.debug("Checking if id: {} is linked to username: {}", id, username);
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("No personal data found for username: {}", username);
            return false;
        }

        List<AdditionalAddress> additionalAddresses = personalData.get().getAddresses().getAdditionalAddress();
        boolean isLinked = additionalAddresses.stream()
                .anyMatch(address -> address.getId().equals(id));

        logger.debug("Link check result for id: {} and username: {}: {}", id, username, isLinked);
        return isLinked;
    }
}
