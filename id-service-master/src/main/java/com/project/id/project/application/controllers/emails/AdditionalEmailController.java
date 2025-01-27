package com.project.id.project.application.controllers.emails;

import com.project.id.project.application.DTOs.documents.DrivingLicenseDTO;
import com.project.id.project.application.DTOs.emails.AdditionalEmailDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.addresses.WorkAddressService;
import com.project.id.project.infrastructure.services.emails.AdditionalEmailService;

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



@Tag(name = "Additional Email Controller")
@RestController
@RequestMapping("/additionalEmail")
public class AdditionalEmailController {

    private static final Logger logger = LoggerFactory.getLogger(AdditionalEmailController.class);

    @Autowired
    private AdditionalEmailService additionalEmailService;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Operation(
	summary = "Создание дополнительного электронного адреса",
	description = "Позволяет добавить электронный адрес"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody AdditionalEmailDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        logger.info("Creating additional email for user: {}", username);
        additionalEmailService.create(username, dto);
        logger.info("Additional email created successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
        summary = "Получение дополнительного электронного адреса",
        description = "Позволяет получить электронный адрес"
        )
    @GetMapping("/get")
    public ResponseEntity<AdditionalEmailDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to access additional email with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<AdditionalEmailDTO> dto = additionalEmailService.read(id);
        return dto.map(emailDTO -> {
            logger.info("Successfully retrieved additional email for user: {}", username);
            return ResponseEntity.ok(emailDTO);
        }).orElseGet(() -> {
            logger.warn("Additional email with ID {} not found for user: {}", id, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
    }
    @Operation(
        summary = "Обновление дополнительного электронного адреса",
        description = "Позволяет обновить электронный адрес"
        )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody AdditionalEmailDTO additionalEmailDTO) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to update additional email with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
        if (personalData.isEmpty()) {
            logger.warn("Personal data not found for user: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Id userId = personalData.get().getId();
        logger.info("Updating additional email with ID {} for user: {}", id, username);
        additionalEmailService.update(id, additionalEmailDTO, userId);
        logger.info("Additional email updated successfully for user: {}", username);
        return ResponseEntity.noContent().build();
    }
    @Operation(
        summary = "Удаление дополнительного электронного адреса",
        description = "Позволяет удалить электронный адрес"
        )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to delete additional email with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        logger.info("Deleting additional email with ID {} for user: {}", id, username);
        additionalEmailService.delete(id);
        logger.info("Additional email deleted successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private boolean isIdLinkedToUsername(Id id, String username) {
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("No personal data found for user: {}", username);
            return false;
        }

        boolean isLinked = personalData.get().getAdditionalEmailList().stream()
                .anyMatch(email -> email.getId().equals(id));

        if (!isLinked) {
            logger.warn("Additional email with ID {} is not linked to user: {}", id, username);
        }

        return isLinked;
    }
}