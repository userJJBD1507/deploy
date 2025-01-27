package com.project.id.project.application.controllers.emails;

import com.project.id.project.application.DTOs.documents.DrivingLicenseDTO;
import com.project.id.project.application.DTOs.emails.PersonalEmailDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.emails.entities.PersonalEmail;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.addresses.WorkAddressService;
import com.project.id.project.infrastructure.services.emails.PersonalEmailService;

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

@Tag(name = "Personal Email Controller")
@RestController
@RequestMapping("/personalEmail")
public class PersonalEmailController {

    private static final Logger logger = LoggerFactory.getLogger(PersonalEmailController.class);

    @Autowired
    private PersonalEmailService personalEmailService;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
        @Operation(
	summary = "Создание электронного адреса",
	description = "Позволяет добавить электронный адрес"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody PersonalEmailDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        logger.info("Creating personal email for user: {}", username);
        personalEmailService.create(username, dto);
        logger.info("Personal email created successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
        summary = "Получение электронного адреса",
        description = "Позволяет получить электронный адрес"
        )
    @GetMapping("/get")
    public ResponseEntity<PersonalEmailDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
                                                    


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to access personal email with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalEmailDTO> dto = personalEmailService.read(id);
        return dto.map(emailDTO -> {
            logger.info("Successfully retrieved personal email for user: {}", username);
            return ResponseEntity.ok(emailDTO);
        }).orElseGet(() -> {
            logger.warn("Personal email with ID {} not found for user: {}", id, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
    }
    @Operation(
        summary = "Обновление электронного адреса",
        description = "Позволяет обновить электронный адрес"
        )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody PersonalEmailDTO personalEmailDTO) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to update personal email with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
        if (personalData.isEmpty()) {
            logger.warn("Personal data not found for user: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Id userId = personalData.get().getId();
        logger.info("Updating personal email with ID {} for user: {}", id, username);
        personalEmailService.update(id, personalEmailDTO, userId);
        logger.info("Personal email updated successfully for user: {}", username);
        return ResponseEntity.noContent().build();
    }
    @Operation(
        summary = "Удаление электронного адреса",
        description = "Позволяет удалить электронный адрес"
        )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to delete personal email with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        logger.info("Deleting personal email with ID {} for user: {}", id, username);
        personalEmailService.delete(id);
        logger.info("Personal email deleted successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private boolean isIdLinkedToUsername(Id id, String username) {
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("No personal data found for user: {}", username);
            return false;
        }

        PersonalEmail personalEmail = personalData.get().getPersonalEmail();

        boolean isLinked = personalEmail != null && personalEmail.getId().equals(id);
        if (!isLinked) {
            logger.warn("Personal email with ID {} is not linked to user: {}", id, username);
        }

        return isLinked;
    }
}
