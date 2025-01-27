package com.project.id.project.application.controllers.documents;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.DTOs.documents.PassportDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.Passport;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.documents.ForeignPassportService;
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

@Tag(name = "Passport Controller")
@RestController
@RequestMapping("/passport")
public class PassportController {

    private static final Logger logger = LoggerFactory.getLogger(PassportController.class);

    @Autowired
    private PassportService passportService;
    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Operation(
	summary = "Создание паспорта",
	description = "Позволяет добавить паспорт"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody PassportDTO dto) {
                                        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                                        String username = jwt.getClaimAsString("preferred_username");

        logger.info("Creating passport for user: {}", username);
        passportService.create(username, dto);
        logger.info("Passport created successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
	summary = "Получение паспорта",
	description = "Позволяет получить паспорт"
    )
    @GetMapping("/get")
    public ResponseEntity<PassportDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to access passport with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PassportDTO> passportDTO = passportService.read(id);
        return passportDTO.map(dto -> {
            logger.info("Successfully retrieved passport for user: {}", username);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> {
            logger.warn("Passport with ID {} not found for user: {}", id, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
    }
    @Operation(
	summary = "Обновление паспорта",
	description = "Позволяет обновить паспорт"
    )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody PassportDTO passportDTO) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to update passport with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
        if (personalData.isEmpty()) {
            logger.warn("Personal data not found for user: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Id documentId = personalData.get().getDocuments().getId();
        logger.info("Updating passport with ID {} for user: {}", id, username);
        passportService.update(id, passportDTO, documentId);
        logger.info("Passport updated successfully for user: {}", username);
        return ResponseEntity.noContent().build();
    }
    @Operation(
	summary = "Удаление паспорта",
	description = "Позволяет удалить паспорт"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to delete passport with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        logger.info("Deleting passport with ID {} for user: {}", id, username);
        passportService.delete(id);
        logger.info("Passport deleted successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private boolean isIdLinkedToUsername(Id id, String username) {
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("No personal data found for user: {}", username);
            return false;
        }

        Passport passport = personalData.get().getDocuments().getPassport();
        boolean isLinked = passport != null && passport.getId().equals(id);
        if (!isLinked) {
            logger.warn("Passport with ID {} is not linked to user: {}", id, username);
        }
        return isLinked;
    }
}