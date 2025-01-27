package com.project.id.project.application.controllers.phonenumbers;

import com.project.id.project.application.DTOs.documents.DrivingLicenseDTO;
import com.project.id.project.application.DTOs.phonenumbers.PhoneNumberDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.phones.entities.PhoneNumber;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.addresses.WorkAddressService;
import com.project.id.project.infrastructure.services.phonenumber.PhoneNumberService;

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

@Tag(name = "Phone Number Controller")
@RestController
@RequestMapping("/phoneNumber")
public class PhoneNumberController {

    private static final Logger logger = LoggerFactory.getLogger(PhoneNumberController.class);

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;


    @Operation(
	summary = "Создание номера телефона",
	description = "Позволяет добавить номер телефона"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody PhoneNumberDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        logger.info("Creating phone number for user: {}", username);
        phoneNumberService.create(username, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
        summary = "Получение номера телефона",
        description = "Позволяет получить номер телефона"
        )
    @GetMapping("/get")
    public ResponseEntity<PhoneNumberDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to access phone number with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PhoneNumberDTO> dto = phoneNumberService.read(id);
        return dto.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Phone number with ID {} not found for user: {}", id, username);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }
    @Operation(
        summary = "Обновление номера телефона",
        description = "Позволяет обновить номер телефона"
        )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody PhoneNumberDTO phoneNumberDTO) {
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
        logger.info("Updating phone number with ID {} for user: {}", id, username);
        phoneNumberService.update(id, phoneNumberDTO, userId);
        return ResponseEntity.noContent().build();
    }
    @Operation(
        summary = "Удаление номера телефона",
        description = "Позволяет удалить номер телефона"
        )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to delete phone number with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        logger.info("Deleting phone number with ID {} for user: {}", id, username);
        phoneNumberService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private boolean isIdLinkedToUsername(Id id, String username) {
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("No personal data found for user: {}", username);
            return false;
        }

        PhoneNumber phoneNumber = personalData.get().getPhoneNumber();

        boolean isLinked = phoneNumber != null && phoneNumber.getId().equals(id);
        if (!isLinked) {
            logger.warn("Phone number with ID {} is not linked to user: {}", id, username);
        }

        return isLinked;
    }
}
