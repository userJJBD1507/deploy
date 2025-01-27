package com.project.id.project.application.controllers.documents;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.DTOs.documents.ForeignPassportDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.ForeignPassport;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.documents.DrivingLicenseService;
import com.project.id.project.infrastructure.services.documents.ForeignPassportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Foreign Passport Controller")
@RestController
@RequestMapping("/foreignPassport")
public class ForeignPassportController {

    private static final Logger logger = LoggerFactory.getLogger(ForeignPassportController.class);

    @Autowired
    private ForeignPassportService foreignPassportService;
    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Operation(
	summary = "Создание Загран Паспорта",
	description = "Позволяет добавить загран паспорт"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody ForeignPassportDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Creating foreign passport for user: {}", username);
        foreignPassportService.create(username, dto);
        logger.info("Foreign passport created successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
	summary = "Получение Загран Паспорта",
	description = "Позволяет получить загран паспорт"
    )
    @GetMapping("/get")
    public ResponseEntity<ForeignPassportDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to access foreign passport with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<ForeignPassportDTO> dto = foreignPassportService.read(id);
        return dto.map(foreignPassportDTO -> {
            logger.info("Successfully retrieved foreign passport for user: {}", username);
            return ResponseEntity.ok(foreignPassportDTO);
        }).orElseGet(() -> {
            logger.warn("Foreign passport with ID {} not found for user: {}", id, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
    }
    @Operation(
	summary = "Обновление Загран Паспорта",
	description = "Позволяет обновить загран паспорт"
    )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody ForeignPassportDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
 

        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to update foreign passport with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
        if (personalData.isEmpty()) {
            logger.warn("Personal data not found for user: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Id documentId = personalData.get().getDocuments().getId();
        logger.info("Updating foreign passport with ID {} for user: {}", id, username);
        foreignPassportService.update(id, dto, documentId);
        logger.info("Foreign passport updated successfully for user: {}", username);
        return ResponseEntity.noContent().build();
    }
    @Operation(
	summary = "Удаление Загран Паспорта",
	description = "Позволяет удалить загран паспорт"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to delete foreign passport with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        logger.info("Deleting foreign passport with ID {} for user: {}", id, username);
        foreignPassportService.delete(id);
        logger.info("Foreign passport deleted successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private boolean isIdLinkedToUsername(Id id, String username) {
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("No personal data found for user: {}", username);
            return false;
        }

        ForeignPassport foreignPassport = personalData.get().getDocuments().getForeignPassport();
        boolean isLinked = foreignPassport != null && foreignPassport.getId().equals(id);
        if (!isLinked) {
            logger.warn("Foreign passport with ID {} is not linked to user: {}", id, username);
        }
        return isLinked;
    }
    
}