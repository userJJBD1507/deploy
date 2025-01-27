package com.project.id.project.application.controllers.addresses;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.DTOs.address.WorkAddressDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.WorkAddress;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.addresses.WorkAddressService;
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




@Tag(name = "Work Address Controller")
@RestController
@RequestMapping("/workAddress")
public class WorkAddressController {

    private static final Logger logger = LoggerFactory.getLogger(WorkAddressController.class);

    @Autowired
    private WorkAddressService workAddressService;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Operation(
	summary = "Создание рабочего адреса",
	description = "Позволяет добавить рабочий адрес"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody WorkAddressDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to create work address for username: {}", username);


        workAddressService.create(username, dto);
        logger.info("Successfully created work address for username: {}", username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
	summary = "Получение рабочего адреса",
	description = "Позволяет получить рабочий адрес"
    )
    @GetMapping("/get")
    public ResponseEntity<WorkAddressDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to read work address with id: {} for username: {}", id, username);


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("Access denied for username: {} to work address with id: {}", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<WorkAddressDTO> dto = workAddressService.read(id);
        if (dto.isPresent()) {
            logger.info("Successfully retrieved work address with id: {} for username: {}", id, username);
        } else {
            logger.warn("Work address with id: {} not found for username: {}", id, username);
        }
        return dto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @Operation(
	summary = "Обновление рабочего адреса",
	description = "Позволяет обновить рабочий адрес"
    )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody WorkAddressDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to update work address with id: {} for username: {}", id, username);



        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("Access denied for username: {} to work address with id: {}", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
        if (personalData.isEmpty()) {
            logger.warn("Personal data not found for username: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Id addressesId = personalData.get().getAddresses().getId();
        workAddressService.update(id, dto, addressesId);
        logger.info("Successfully updated work address with id: {} for username: {}", id, username);
        return ResponseEntity.noContent().build();
    }
    @Operation(
	summary = "Удаление рабочего адреса",
	description = "Позволяет удалить рабочий адрес"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to delete work address with id: {} for username: {}", id, username);



        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("Access denied for username: {} to work address with id: {}", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        workAddressService.delete(id);
        logger.info("Successfully deleted work address with id: {} for username: {}", id, username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private boolean isIdLinkedToUsername(Id id, String username) {
        logger.debug("Checking if id: {} is linked to username: {}", id, username);
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("No personal data found for username: {}", username);
            return false;
        }

        WorkAddress workAddress = personalData.get().getAddresses().getWorkAddress();
        boolean isLinked = workAddress != null && workAddress.getId().equals(id);

        logger.debug("Link check result for id: {} and username: {}: {}", id, username, isLinked);
        return isLinked;
    }
}
