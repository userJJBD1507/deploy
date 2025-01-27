package com.project.id.project.application.controllers.documents;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.DTOs.documents.DrivingLicenseDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.DrivingLicense;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.documents.CompulsoryMedicalInsuranceService;
import com.project.id.project.infrastructure.services.documents.DrivingLicenseService;

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


//@RestController
//@RequestMapping("/drivingLicense")
//public class DrivingLicenseController {
//
//    @Autowired
//    private DrivingLicenseService drivingLicenseService;
//    @Autowired
//    private EntityPersonalDataRepository entityPersonalDataRepository;
//
//    @PostMapping("/create")
//    public ResponseEntity<Void> create(@RequestParam("username") String username,
//                                       @RequestBody DrivingLicenseDTO dto) {
//        if (!UserChecker.isUserAuthorized(username)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//        drivingLicenseService.create(username, dto);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @GetMapping("/get")
//    public ResponseEntity<DrivingLicenseDTO> read(@RequestParam("username") String username,
//                                                  @RequestParam("id") Id id) {
//        if (!UserChecker.isUserAuthorized(username)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        if (!isIdLinkedToUsername(id, username)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        Optional<DrivingLicenseDTO> dto = drivingLicenseService.read(id);
//        return dto.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<Void> update(@RequestParam("username") String username,
//                                       @RequestParam("id") Id id,
//                                       @RequestBody DrivingLicenseDTO dto) {
//        if (!UserChecker.isUserAuthorized(username)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        if (!isIdLinkedToUsername(id, username)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
//        if (personalData.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//
//        Id documentId = personalData.get().getDocuments().getId();
//
//        drivingLicenseService.update(id, dto, documentId);
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/delete")
//    public ResponseEntity<Void> delete(@RequestParam("username") String username,
//                                       @RequestParam("id") Id id) {
//        if (!UserChecker.isUserAuthorized(username)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        if (!isIdLinkedToUsername(id, username)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        drivingLicenseService.delete(id);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//    private boolean isIdLinkedToUsername(Id id, String username) {
//        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);
//
//        if (personalData.isEmpty()) {
//            return false;
//        }
//
//        DrivingLicense drivingLicense = personalData.get().getDocuments().getDrivingLicense();
//
//        return drivingLicense != null && drivingLicense.getId().equals(id);
//    }
//}


@Tag(name = "Driving License Controller")
@RestController
@RequestMapping("/drivingLicense")
public class DrivingLicenseController {

    private static final Logger logger = LoggerFactory.getLogger(DrivingLicenseController.class);

    @Autowired
    private DrivingLicenseService drivingLicenseService;
    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Operation(
	summary = "Создание ВУ",
	description = "Позволяет добавить ВУ"
    )
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody DrivingLicenseDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Attempting to create driving license for username: {}", username);
 
        drivingLicenseService.create(username, dto);
        logger.info("Successfully created driving license for username: {}", username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
	summary = "Получение ВУ",
	description = "Позволяет получить ВУ"
    )
    @GetMapping("/get")
    public ResponseEntity<DrivingLicenseDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Fetching driving license for username: {} and id: {}", username, id);


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("ID {} is not linked to username: {}", id, username);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<DrivingLicenseDTO> dto = drivingLicenseService.read(id);
        if (dto.isPresent()) {
            logger.info("Successfully fetched driving license for id: {}", id);
            return ResponseEntity.ok(dto.get());
        } else {
            logger.warn("Driving license not found for id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @Operation(
	summary = "Обновление ВУ",
	description = "Позволяет обновить ВУ"
    )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id,
                                       @RequestBody DrivingLicenseDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Updating driving license for username: {} and id: {}", username, id);
 

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

        drivingLicenseService.update(id, dto, documentId);
        logger.info("Successfully updated driving license for id: {}", id);
        return ResponseEntity.noContent().build();
    }
    @Operation(
	summary = "Удаление ВУ",
	description = "Позволяет удалить ВУ"
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        logger.info("Attempting to delete driving license for username: {} and id: {}", username, id);
 

        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("ID {} is not linked to username: {}", id, username);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        drivingLicenseService.delete(id);
        logger.info("Successfully deleted driving license for id: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    private boolean isIdLinkedToUsername(Id id, String username) {
        logger.debug("Checking if id: {} is linked to username: {}", id, username);
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.debug("Personal data not found for username: {}", username);
            return false;
        }

        DrivingLicense drivingLicense = personalData.get().getDocuments().getDrivingLicense();

        boolean isLinked = drivingLicense != null && drivingLicense.getId().equals(id);
        logger.debug("ID {} linked to username {}: {}", id, username, isLinked);
        return isLinked;
    }
}
