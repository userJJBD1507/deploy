package com.project.id.project.application.controllers.documents;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.DTOs.documents.VoluntaryHealthInsuranceDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.VoluntaryHealthInsurance;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.documents.PassportService;
import com.project.id.project.infrastructure.services.documents.VoluntaryHealthInsuranceService;

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



@Tag(name = "Voluntary Health Insurance Controller")
@RestController
@RequestMapping("/voluntaryHealthInsurance")
public class VoluntaryHealthInsuranceController {

    private static final Logger logger = LoggerFactory.getLogger(VoluntaryHealthInsuranceController.class);

    @Autowired
    private VoluntaryHealthInsuranceService voluntaryHealthInsuranceService;
    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Operation(
	summary = "Создание ДМС",
	description = "Позволяет добавить ДМС"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody VoluntaryHealthInsuranceDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        logger.info("Creating Voluntary Health Insurance for user: {}", username);
        voluntaryHealthInsuranceService.create(username, dto);
        logger.info("Voluntary Health Insurance created successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
	summary = "Получение ДМС",
	description = "Позволяет получить ДМС"
    )
    @GetMapping("/get")
    public ResponseEntity<VoluntaryHealthInsuranceDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to access Voluntary Health Insurance with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<VoluntaryHealthInsuranceDTO> dto = voluntaryHealthInsuranceService.read(id);
        return dto.map(insuranceDTO -> {
            logger.info("Successfully retrieved Voluntary Health Insurance for user: {}", username);
            return ResponseEntity.ok(insuranceDTO);
        }).orElseGet(() -> {
            logger.warn("Voluntary Health Insurance with ID {} not found for user: {}", id, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
    }
    @Operation(
	summary = "Обновление ДМС",
	description = "Позволяет обновить ДМС"
    )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody VoluntaryHealthInsuranceDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to update Voluntary Health Insurance with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
        if (personalData.isEmpty()) {
            logger.warn("Personal data not found for user: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Id documentId = personalData.get().getDocuments().getId();
        logger.info("Updating Voluntary Health Insurance with ID {} for user: {}", id, username);
        voluntaryHealthInsuranceService.update(id, dto, documentId);
        logger.info("Voluntary Health Insurance updated successfully for user: {}", username);
        return ResponseEntity.noContent().build();
    }
    @Operation(
	summary = "Удаление ДМС",
	description = "Позволяет удалить ДМС"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to delete Voluntary Health Insurance with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        logger.info("Deleting Voluntary Health Insurance with ID {} for user: {}", id, username);
        voluntaryHealthInsuranceService.delete(id);
        logger.info("Voluntary Health Insurance deleted successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private boolean isIdLinkedToUsername(Id id, String username) {
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("No personal data found for user: {}", username);
            return false;
        }

        VoluntaryHealthInsurance insurance = personalData.get().getDocuments().getVoluntaryHealthInsurance();

        boolean isLinked = insurance != null && insurance.getId().equals(id);
        if (!isLinked) {
            logger.warn("Voluntary Health Insurance with ID {} is not linked to user: {}", id, username);
        }
        return isLinked;
    }
}