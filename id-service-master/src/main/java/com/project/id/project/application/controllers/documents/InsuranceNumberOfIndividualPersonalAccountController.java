package com.project.id.project.application.controllers.documents;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.DTOs.documents.InsuranceNumberOfIndividualPersonalAccountDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.InsuranceNumberOfIndividualPersonalAccount;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.documents.ForeignPassportService;
import com.project.id.project.infrastructure.services.documents.InsuranceNumberOfIndividualPersonalAccountService;

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


@Tag(name = "Insurance Number Of Individual Personal Account Controller")
@RestController
@RequestMapping("/insuranceNumberOfIndividualPersonalAccount")
public class InsuranceNumberOfIndividualPersonalAccountController {

    private static final Logger logger = LoggerFactory.getLogger(InsuranceNumberOfIndividualPersonalAccountController.class);

    @Autowired
    private InsuranceNumberOfIndividualPersonalAccountService individualPersonalAccountService;
    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Operation(
	summary = "Создание СНИЛС",
	description = "Позволяет добавить СНИЛС"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody InsuranceNumberOfIndividualPersonalAccountDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        logger.info("Creating insurance number for user: {}", username);
        individualPersonalAccountService.create(username, dto);
        logger.info("Insurance number created successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
        summary = "Получение СНИЛС",
        description = "Позволяет получить СНИЛС"
    )
    @GetMapping("/get")
    public ResponseEntity<InsuranceNumberOfIndividualPersonalAccountDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to access insurance number with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<InsuranceNumberOfIndividualPersonalAccountDTO> dto = individualPersonalAccountService.read(id);
        return dto.map(insuranceDTO -> {
            logger.info("Successfully retrieved insurance number for user: {}", username);
            return ResponseEntity.ok(insuranceDTO);
        }).orElseGet(() -> {
            logger.warn("Insurance number with ID {} not found for user: {}", id, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
    }
    @Operation(
        summary = "Обновление СНИЛС",
        description = "Позволяет обновить СНИЛС"
        )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody InsuranceNumberOfIndividualPersonalAccountDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to update insurance number with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
        if (personalData.isEmpty()) {
            logger.warn("Personal data not found for user: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Id documentId = personalData.get().getDocuments().getId();
        logger.info("Updating insurance number with ID {} for user: {}", id, username);
        individualPersonalAccountService.update(id, dto, documentId);
        logger.info("Insurance number updated successfully for user: {}", username);
        return ResponseEntity.noContent().build();
    }
    @Operation(
        summary = "Удаление СНИЛС",
        description = "Позволяет удалить СНИЛС"
        )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to delete insurance number with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        logger.info("Deleting insurance number with ID {} for user: {}", id, username);
        individualPersonalAccountService.delete(id);
        logger.info("Insurance number deleted successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private boolean isIdLinkedToUsername(Id id, String username) {
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("No personal data found for user: {}", username);
            return false;
        }

        InsuranceNumberOfIndividualPersonalAccount insuranceNumber = personalData.get()
                .getDocuments().getInsuranceNumberOfIndividualPersonalAccount();

        boolean isLinked = insuranceNumber != null && insuranceNumber.getId().equals(id);
        if (!isLinked) {
            logger.warn("Insurance number with ID {} is not linked to user: {}", id, username);
        }
        return isLinked;
    }
}