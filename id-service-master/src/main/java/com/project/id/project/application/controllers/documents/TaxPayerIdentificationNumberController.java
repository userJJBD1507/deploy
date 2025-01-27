package com.project.id.project.application.controllers.documents;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.DTOs.documents.TaxPayerIdentificationNumberDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.TaxPayerIdentificationNumber;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.documents.PassportService;
import com.project.id.project.infrastructure.services.documents.TaxPayerIdentificationNumberService;

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



@Tag(name = "Taxpayer Identification Number Controller")
@RestController
@RequestMapping("/taxPayerIdentificationNumber")
public class TaxPayerIdentificationNumberController {

    private static final Logger logger = LoggerFactory.getLogger(TaxPayerIdentificationNumberController.class);

    @Autowired
    private TaxPayerIdentificationNumberService taxPayerIdentificationNumberService;
    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Operation(
	summary = "Создание ИНН",
	description = "Позволяет добавить ИНН"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody TaxPayerIdentificationNumberDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        logger.info("Creating Tax Payer Identification Number for user: {}", username);
        taxPayerIdentificationNumberService.create(username, dto);
        logger.info("Tax Payer Identification Number created successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
        summary = "Получение ИНН",
        description = "Позволяет получить ИНН"
        )
    @GetMapping("/get")
    public ResponseEntity<TaxPayerIdentificationNumberDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
 

        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to access Tax Payer Identification Number with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<TaxPayerIdentificationNumberDTO> dto = taxPayerIdentificationNumberService.read(id);
        return dto.map(tinDTO -> {
            logger.info("Successfully retrieved Tax Payer Identification Number for user: {}", username);
            return ResponseEntity.ok(tinDTO);
        }).orElseGet(() -> {
            logger.warn("Tax Payer Identification Number with ID {} not found for user: {}", id, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
    }
    @Operation(
        summary = "Обновление ИНН",
        description = "Позволяет обновить ИНН"
        )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody TaxPayerIdentificationNumberDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to update Tax Payer Identification Number with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
        if (personalData.isEmpty()) {
            logger.warn("Personal data not found for user: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Id documentId = personalData.get().getDocuments().getId();
        logger.info("Updating Tax Payer Identification Number with ID {} for user: {}", id, username);
        taxPayerIdentificationNumberService.update(id, dto, documentId);
        logger.info("Tax Payer Identification Number updated successfully for user: {}", username);
        return ResponseEntity.noContent().build();
    }
    @Operation(
        summary = "Удаление ИНН",
        description = "Позволяет удалить ИНН"
        )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to delete Tax Payer Identification Number with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        logger.info("Deleting Tax Payer Identification Number with ID {} for user: {}", id, username);
        taxPayerIdentificationNumberService.delete(id);
        logger.info("Tax Payer Identification Number deleted successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private boolean isIdLinkedToUsername(Id id, String username) {
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("No personal data found for user: {}", username);
            return false;
        }

        TaxPayerIdentificationNumber taxPayerIdentificationNumber = personalData.get()
                .getDocuments().getTaxPayerIdentificationNumber();

        boolean isLinked = taxPayerIdentificationNumber != null && taxPayerIdentificationNumber.getId().equals(id);
        if (!isLinked) {
            logger.warn("Tax Payer Identification Number with ID {} is not linked to user: {}", id, username);
        }
        return isLinked;
    }
}