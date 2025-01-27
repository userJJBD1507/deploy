package com.project.id.project.application.controllers.phonenumbers;

import com.project.id.project.application.DTOs.documents.DrivingLicenseDTO;
import com.project.id.project.application.DTOs.phonenumbers.AdditionalPhoneNumberDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;
import com.project.id.project.infrastructure.services.addresses.WorkAddressService;
import com.project.id.project.infrastructure.services.phonenumber.AdditionalPhoneNumberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Additional Phone Number Controller")
@RestController
@RequestMapping("/additionalPhoneNumber")
public class AdditionalPhoneNumberController {

    private static final Logger logger = LoggerFactory.getLogger(AdditionalPhoneNumberController.class);

    @Autowired
    private AdditionalPhoneNumberService additionalPhoneNumberService;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Operation(
	summary = "Создание дополнительного номера телефона",
	description = "Позволяет добавить дополнительный номер телефона"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody AdditionalPhoneNumberDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        logger.info("Creating additional phone number for user: {}", username);
        additionalPhoneNumberService.create(username, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
        summary = "Получение дополнительного номера телефона",
        description = "Позволяет получить дополнительный номер телефона"
        )
    @GetMapping("/get")
    public ResponseEntity<AdditionalPhoneNumberDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to access phone number with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<AdditionalPhoneNumberDTO> dto = additionalPhoneNumberService.read(id);
        return dto.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Phone number with ID {} not found for user: {}", id, username);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }
    @Operation(
        summary = "Обновление дополнительного номера телефона",
        description = "Позволяет обновить дополнительный номер телефона"
        )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody AdditionalPhoneNumberDTO additionalPhoneNumberDTO) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to update phone number with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
        if (personalData.isEmpty()) {
            logger.warn("Personal data not found for user: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Id userId = personalData.get().getId();
        logger.info("Updating additional phone number with ID {} for user: {}", id, username);
        additionalPhoneNumberService.update(id, additionalPhoneNumberDTO, userId);
        return ResponseEntity.noContent().build();
    }
    @Operation(
        summary = "Удаление дополнительного номера телефона",
        description = "Позволяет удалить дополнительный номер телефона"
        )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to delete phone number with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        logger.info("Deleting additional phone number with ID {} for user: {}", id, username);
        additionalPhoneNumberService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private boolean isIdLinkedToUsername(Id id, String username) {
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("No personal data found for user: {}", username);
            return false;
        }

        boolean isLinked = personalData.get().getAdditionalPhoneNumberList().stream()
                .anyMatch(phone -> phone.getId().equals(id));

        if (!isLinked) {
            logger.warn("Phone number with ID {} is not linked to user: {}", id, username);
        }

        return isLinked;
    }
}
