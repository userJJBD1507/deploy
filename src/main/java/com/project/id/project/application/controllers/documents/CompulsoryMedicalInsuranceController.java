package com.project.id.project.application.controllers.documents;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.DTOs.documents.CompulsoryMedicalInsuranceDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.CompulsoryMedicalInsurance;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.documents.BirthCertificateService;
import com.project.id.project.infrastructure.services.documents.CompulsoryMedicalInsuranceService;

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


@Tag(name = "Compulsory Medical Insurance Controller")
@RestController
@RequestMapping("/compulsoryMedicalInsurance")
public class CompulsoryMedicalInsuranceController {

    private static final Logger logger = LoggerFactory.getLogger(CompulsoryMedicalInsuranceController.class);

    @Autowired
    private CompulsoryMedicalInsuranceService compulsoryMedicalInsuranceService;
    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Operation(
	summary = "Создание ОМС",
	description = "Позволяет добавить ОМС"
    )
      @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CompulsoryMedicalInsuranceDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to create CompulsoryMedicalInsurance for username: {}", username);

   

        compulsoryMedicalInsuranceService.create(username, dto);
        logger.info("Successfully created CompulsoryMedicalInsurance for username: {}", username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
	summary = "Получение ОМС",
	description = "Позволяет получить ОМС"
    )
    @GetMapping("/get")
    public ResponseEntity<CompulsoryMedicalInsuranceDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to read CompulsoryMedicalInsurance for username: {}, id: {}", username, id);



        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("ID {} is not linked to username: {}", id, username);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<CompulsoryMedicalInsuranceDTO> dto = compulsoryMedicalInsuranceService.read(id);
        if (dto.isPresent()) {
            logger.info("Successfully retrieved CompulsoryMedicalInsurance for id: {}", id);
            return ResponseEntity.ok(dto.get());
        } else {
            logger.warn("CompulsoryMedicalInsurance not found for id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(
	summary = "Обновление ОМС",
	description = "Позволяет обновить ОМС"
    )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody CompulsoryMedicalInsuranceDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to update CompulsoryMedicalInsurance for username: {}, id: {}", username, id);


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("ID {} is not linked to username: {}", id, username);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
        if (personalData.isEmpty()) {
            logger.warn("Personal data not found for username: {}", username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Id documentId = personalData.get().getDocuments().getId();

        compulsoryMedicalInsuranceService.update(id, dto, documentId);
        logger.info("Successfully updated CompulsoryMedicalInsurance for id: {}", id);
        return ResponseEntity.noContent().build();
    }
    @Operation(
	summary = "Удаление ОМС",
	description = "Позволяет удалить ОМС"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Received request to delete CompulsoryMedicalInsurance for username: {}, id: {}", username, id);


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("ID {} is not linked to username: {}", id, username);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        compulsoryMedicalInsuranceService.delete(id);
        logger.info("Successfully deleted CompulsoryMedicalInsurance for id: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private boolean isIdLinkedToUsername(Id id, String username) {
        logger.debug("Checking if ID {} is linked to username: {}", id, username);

        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("Personal data not found for username: {}", username);
            return false;
        }

        CompulsoryMedicalInsurance cmi = personalData.get().getDocuments().getCompulsoryMedicalInsurance();

        boolean isLinked = cmi != null && cmi.getId().equals(id);
        logger.debug("ID {} linked to username {}: {}", id, username, isLinked);
        return isLinked;
    }
}
