package com.project.id.project.application.controllers.documents;

import com.project.id.project.application.DTOs.documents.BirthCertificateDTO;
import com.project.id.project.application.services.ApiResponse;
import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.BirthCertificate;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.documents.BirthCertificateService;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Optional;


@Tag(name = "Birth Certificate Controller")
@RestController
@RequestMapping("/birthCertificate")
public class BirthCertificateController {

    private static final Logger logger = LoggerFactory.getLogger(BirthCertificateController.class);

    @Autowired
    private BirthCertificateService birthCertificateService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Operation(
	summary = "Создание Свидетельства о рождении",
	description = "Позволяет добавить Свидетельство о рождении"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody BirthCertificateDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to create birth certificate for username: {}", username);



        birthCertificateService.create(username, dto);
        logger.info("Successfully created birth certificate for username: {}", username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
	summary = "Создание Свидетельства о рождении",
	description = "Позволяет добавить Свидетельство о рождении"
    )
    @GetMapping("/get")
    public ResponseEntity<BirthCertificateDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to read birth certificate with id: {} for username: {}", id, username);


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("Access denied for username: {} to birth certificate with id: {}", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<BirthCertificateDTO> dto = birthCertificateService.read(id);
        if (dto.isPresent()) {
            logger.info("Successfully retrieved birth certificate with id: {} for username: {}", id, username);
        } else {
            logger.warn("Birth certificate with id: {} not found for username: {}", id, username);
        }
        return dto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @Operation(
	summary = "Обновление Свидетельства о рождении",
	description = "Позволяет обновить Свидетельство о рождении"
    )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody BirthCertificateDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to update birth certificate with id: {} for username: {}", id, username);



        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("Access denied for username: {} to birth certificate with id: {}", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
        if (personalData.isEmpty()) {
            logger.warn("Personal data not found for username: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Id documentId = personalData.get().getDocuments().getId();
        birthCertificateService.update(id, dto, documentId);
        logger.info("Successfully updated birth certificate with id: {} for username: {}", id, username);
        return ResponseEntity.noContent().build();
    }
    @Operation(
	summary = "Удаление Свидетельства о рождении",
	description = "Позволяет удалить Свидетельство о рождении"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to delete birth certificate with id: {} for username: {}", id, username);



        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("Access denied for username: {} to birth certificate with id: {}", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        birthCertificateService.delete(id);
        logger.info("Successfully deleted birth certificate with id: {} for username: {}", id, username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private boolean isIdLinkedToUsername(Id id, String username) {
        logger.debug("Checking if id: {} is linked to username: {}", id, username);
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("No personal data found for username: {}", username);
            return false;
        }

        BirthCertificate birthCertificate = personalData.get().getDocuments().getBirthCertificate();
        boolean isLinked = birthCertificate != null && birthCertificate.getId().equals(id);

        logger.debug("Link check result for id: {} and username: {}: {}", id, username, isLinked);
        return isLinked;
    }
}
