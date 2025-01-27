package com.project.id.project.application.controllers.personal;

import com.project.id.project.application.DTOs.personal.IdsDTO;
import com.project.id.project.application.DTOs.personal.PersonalDataDTO;
import com.project.id.project.core.Id;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.personal.PersonalDataService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Personal Data Controller")
@RestController
@RequestMapping("/personalData")
public class PersonalDataController {

    private static final Logger logger = LoggerFactory.getLogger(PersonalDataController.class);

    @Autowired
    private PersonalDataService personalDataService;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @PersistenceContext
    public EntityManager entityManager;
        @Operation(
	summary = "Получение персональной информации",
	description = "Позволяет получить персональную информацию"
    )
     @GetMapping("/get")
    public ResponseEntity<PersonalDataDTO> read(@RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to access personal data with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<PersonalDataDTO> dto = personalDataService.read(id);
        return dto.map(data -> {
            logger.info("Successfully retrieved personal data for user: {}", username);
            return ResponseEntity.ok(data);
        }).orElseGet(() -> {
            logger.warn("Personal data with ID {} not found for user: {}", id, username);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        });
    }
    @Operation(
        summary = "Обновление персональной информации",
        description = "Позволяет обновить персональную информацию"
        )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("id") Id id, @RequestBody PersonalDataDTO dto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to update personal data with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        logger.info("Updating personal data with ID {} for user: {}", id, username);
        personalDataService.update(id, dto);
        logger.info("Personal data updated successfully for user: {}", username);
        return ResponseEntity.noContent().build();
    }
    @Operation(
        summary = "Удаление персональной информации",
        description = "Позволяет удалить персональную информацию"
        )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete( @RequestParam("id") Id id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        if (!isIdLinkedToUsername(id, username)) {
            logger.warn("User {} tried to delete personal data with ID {} that is not linked to them", username, id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        logger.info("Deleting personal data with ID {} for user: {}", id, username);
        personalDataService.delete(id);
        logger.info("Personal data deleted successfully for user: {}", username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // @Operation(
    //     summary = "Получение всех доступных id данных пользователя",
    //     description = "Позволяет получить все доступные id данных пользователя"
    //     )
    // @GetMapping("/getAllId")
    // public IdsDTO getAllIds() {
    //     Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     String username = jwt.getClaimAsString("preferred_username");

    //     String userId = entityPersonalDataRepository.findByInvocation(username).get().getId().id().toString();

    //     List<UUID> docsList = entityManager.createNativeQuery(
    //         "SELECT e.id, en.birth_certificate_id, en.compulsory_med_insurance_id, en.driving_license_id, " +
    //         "en.foreign_passport_id, en.insurance_number_of_personal_acc_id, en.passport_id, " +
    //         "en.tax_payer_ident_number_id, en.voluntary_health_insurance_id " +
    //         "FROM personal_data_table e " +
    //         "INNER JOIN all_documents_table en ON e.id = en.personal_data_id " +
    //         "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
    //         .setParameter("userId", userId)
    //         .getResultList();

    //     List<UUID> mainAddressesList = entityManager.createNativeQuery(
    //         "SELECT en.id " +
    //         "FROM addresses_table en " +
    //         "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
    //         "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
    //         .setParameter("userId", userId)
    //         .getResultList();

    //     List<UUID> additionalAddressesList = entityManager.createNativeQuery(
    //         "SELECT e1.id " +
    //         "FROM additional_address_table e1 " +
    //         "INNER JOIN addresses_table en ON en.id = e1.addresses_id " +
    //         "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
    //         "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
    //         .setParameter("userId", userId)
    //         .getResultList();

    //     List<UUID> workAddressesList = entityManager.createNativeQuery(
    //         "SELECT e2.id " +
    //         "FROM work_address_table e2 " +
    //         "INNER JOIN addresses_table en ON en.id = e2.addresses_id " +
    //         "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
    //         "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
    //         .setParameter("userId", userId)
    //         .getResultList();

    //     List<UUID> homeAddressesList = entityManager.createNativeQuery(
    //         "SELECT e3.id " +
    //         "FROM home_address_table e3 " +
    //         "INNER JOIN addresses_table en ON en.id = e3.addresses_id " +
    //         "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
    //         "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
    //         .setParameter("userId", userId)
    //         .getResultList();

    //     List<UUID> personalEmailList = entityManager.createNativeQuery(
    //         "SELECT e1.id " +
    //         "FROM personal_email_table e1 " +
    //         "INNER JOIN personal_data_table e ON e.id = e1.personal_data_id " +
    //         "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
    //         .setParameter("userId", userId)
    //         .getResultList();

    //     List<UUID> additionalEmailsList = entityManager.createNativeQuery(
    //         "SELECT e2.id " +
    //         "FROM additional_emails_table e2 " +
    //         "INNER JOIN personal_data_table e ON e.id = e2.personal_data_id " +
    //         "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
    //         .setParameter("userId", userId)
    //         .getResultList();

    //     List<UUID> additionalPhoneNumbersList = entityManager.createNativeQuery(
    //         "SELECT e3.id " +
    //         "FROM additional_phone_numbers_table e3 " +
    //         "INNER JOIN personal_data_table e ON e.id = e3.personal_data_id " +
    //         "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
    //         .setParameter("userId", userId)
    //         .getResultList();

    //     List<UUID> phoneNumbersList = entityManager.createNativeQuery(
    //         "SELECT e4.id " +
    //         "FROM phone_numbers_table e4 " +
    //         "INNER JOIN personal_data_table e ON e.id = e4.personal_data_id " +
    //         "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
    //         .setParameter("userId", userId)
    //         .getResultList();

    //     String personalId = docsList.isEmpty() ? null : docsList.get(0).toString();
    //     String birthCertificateId = docsList.size() > 1 ? docsList.get(1).toString() : null;
    //     String compulsoryMedInsuranceId = docsList.size() > 2 ? docsList.get(2).toString() : null;
    //     String drivingLicenseId = docsList.size() > 3 ? docsList.get(3).toString() : null;
    //     String foreignPassportId = docsList.size() > 4 ? docsList.get(4).toString() : null;
    //     String insuranceNumberOfPersonalAccId = docsList.size() > 5 ? docsList.get(5).toString() : null;
    //     String passportId = docsList.size() > 6 ? docsList.get(6).toString() : null;
    //     String taxPayerIdentNumberId = docsList.size() > 7 ? docsList.get(7).toString() : null;
    //     String voluntaryHealthInsuranceId = docsList.size() > 8 ? docsList.get(8).toString() : null;

    //     String addressId = mainAddressesList.isEmpty() ? null : mainAddressesList.get(0).toString();
    //     List<String> additionalAddressId = new ArrayList<>();
    //     for (UUID address : additionalAddressesList) {
    //         additionalAddressId.add(address.toString());
    //     }
    //     String workAddressId = workAddressesList.isEmpty() ? null : workAddressesList.get(0).toString();
    //     String homeAddressId = homeAddressesList.isEmpty() ? null : homeAddressesList.get(0).toString();

    //     String personalEmailId = personalEmailList.isEmpty() ? null : personalEmailList.get(0).toString();
    //     List<String> additionalEmailId = new ArrayList<>();
    //     for (UUID email : additionalEmailsList) {
    //         additionalEmailId.add(email.toString());
    //     }
    //     List<String> additionalPhoneNumberId = new ArrayList<>();
    //     for (UUID phoneNumber : additionalPhoneNumbersList) {
    //         additionalPhoneNumberId.add(phoneNumber.toString());
    //     }
    //     String phoneNumberId = phoneNumbersList.isEmpty() ? null : phoneNumbersList.get(0).toString();

    //     return new IdsDTO(
    //         personalId, birthCertificateId, compulsoryMedInsuranceId, drivingLicenseId,
    //         foreignPassportId, insuranceNumberOfPersonalAccId, passportId,
    //         taxPayerIdentNumberId, voluntaryHealthInsuranceId, addressId,
    //         additionalAddressId, workAddressId, homeAddressId, personalEmailId,
    //         additionalEmailId, additionalPhoneNumberId, phoneNumberId
    //     );
    // }


    
       @GetMapping("/getAllId")
    public IdsDTO getAllIds() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        String userId = entityPersonalDataRepository.findByInvocation(username).get().getId().id().toString();

        List<UUID> birthCertificateList = entityManager.createNativeQuery(
            "SELECT en.birth_certificate_id FROM all_documents_table en " +
            "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();
    
        List<UUID> compulsoryMedInsuranceList = entityManager.createNativeQuery(
            "SELECT en.compulsory_med_insurance_id FROM all_documents_table en " +
            "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();
    
        List<UUID> drivingLicenseList = entityManager.createNativeQuery(
            "SELECT en.driving_license_id FROM all_documents_table en " +
            "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();

        List<UUID> foreignPassportList = entityManager.createNativeQuery(
            "SELECT en.foreign_passport_id FROM all_documents_table en " +
            "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();
    
        List<UUID> insuranceNumberOfPersonalAccList = entityManager.createNativeQuery(
            "SELECT en.insurance_number_of_personal_acc_id FROM all_documents_table en " +
            "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();
    
        List<UUID> passportList = entityManager.createNativeQuery(
            "SELECT en.passport_id FROM all_documents_table en " +
            "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();
    
        List<UUID> taxPayerIdentNumberList = entityManager.createNativeQuery(
            "SELECT en.tax_payer_ident_number_id FROM all_documents_table en " +
            "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();
    
        List<UUID> voluntaryHealthInsuranceList = entityManager.createNativeQuery(
            "SELECT en.voluntary_health_insurance_id FROM all_documents_table en " +
            "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();
        List<UUID> mainAddressesList = entityManager.createNativeQuery(
            "SELECT en.id " +
            "FROM addresses_table en " +
            "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();

        List<UUID> additionalAddressesList = entityManager.createNativeQuery(
            "SELECT e1.id " +
            "FROM additional_address_table e1 " +
            "INNER JOIN addresses_table en ON en.id = e1.addresses_id " +
            "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();

        List<UUID> workAddressesList = entityManager.createNativeQuery(
            "SELECT e2.id " +
            "FROM work_address_table e2 " +
            "INNER JOIN addresses_table en ON en.id = e2.addresses_id " +
            "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();

        List<UUID> homeAddressesList = entityManager.createNativeQuery(
            "SELECT e3.id " +
            "FROM home_address_table e3 " +
            "INNER JOIN addresses_table en ON en.id = e3.addresses_id " +
            "INNER JOIN personal_data_table e ON e.id = en.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();

        List<UUID> personalEmailList = entityManager.createNativeQuery(
            "SELECT e1.id " +
            "FROM personal_email_table e1 " +
            "INNER JOIN personal_data_table e ON e.id = e1.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();

        List<UUID> additionalEmailsList = entityManager.createNativeQuery(
            "SELECT e2.id " +
            "FROM additional_emails_table e2 " +
            "INNER JOIN personal_data_table e ON e.id = e2.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();

        List<UUID> additionalPhoneNumbersList = entityManager.createNativeQuery(
            "SELECT e3.id " +
            "FROM additional_phone_numbers_table e3 " +
            "INNER JOIN personal_data_table e ON e.id = e3.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();

        List<UUID> phoneNumbersList = entityManager.createNativeQuery(
            "SELECT e4.id " +
            "FROM phone_numbers_table e4 " +
            "INNER JOIN personal_data_table e ON e.id = e4.personal_data_id " +
            "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
            .setParameter("userId", userId)
            .getResultList();

        List<UUID> personalIdList = entityManager.createNativeQuery(
        "SELECT e.id FROM personal_data_table e " +
        "WHERE e.id = CAST(:userId AS UUID)", UUID.class)
        .setParameter("userId", userId)
        .getResultList();

        String personalId = (personalIdList != null && !personalIdList.isEmpty() && personalIdList.get(0) != null) ? personalIdList.get(0).toString() : null;
        String birthCertificateId = (birthCertificateList != null && !birthCertificateList.isEmpty() && birthCertificateList.get(0) != null) ? birthCertificateList.get(0).toString() : null;
        String compulsoryMedInsuranceId = (compulsoryMedInsuranceList != null && !compulsoryMedInsuranceList.isEmpty() && compulsoryMedInsuranceList.get(0) != null) ? compulsoryMedInsuranceList.get(0).toString() : null;
        String drivingLicenseId = (drivingLicenseList != null && !drivingLicenseList.isEmpty() && drivingLicenseList.get(0) != null) ? drivingLicenseList.get(0).toString() : null;
        String foreignPassportId = (foreignPassportList != null && !foreignPassportList.isEmpty() && foreignPassportList.get(0) != null) ? foreignPassportList.get(0).toString() : null;
        String insuranceNumberOfPersonalAccId = (insuranceNumberOfPersonalAccList != null && !insuranceNumberOfPersonalAccList.isEmpty() && insuranceNumberOfPersonalAccList.get(0) != null) ? insuranceNumberOfPersonalAccList.get(0).toString() : null;
        String passportId = (passportList != null && !passportList.isEmpty() && passportList.get(0) != null) ? passportList.get(0).toString() : null;
        String taxPayerIdentNumberId = (taxPayerIdentNumberList != null && !taxPayerIdentNumberList.isEmpty() && taxPayerIdentNumberList.get(0) != null) ? taxPayerIdentNumberList.get(0).toString() : null;
        String voluntaryHealthInsuranceId = (voluntaryHealthInsuranceList != null && !voluntaryHealthInsuranceList.isEmpty() && voluntaryHealthInsuranceList.get(0) != null) ? voluntaryHealthInsuranceList.get(0).toString() : null;
                
        String addressId = mainAddressesList.isEmpty() ? null : mainAddressesList.get(0).toString();
        List<String> additionalAddressId = new ArrayList<>();
        for (UUID address : additionalAddressesList) {
            additionalAddressId.add(address.toString());
        }
        String workAddressId = workAddressesList.isEmpty() ? null : workAddressesList.get(0).toString();
        String homeAddressId = homeAddressesList.isEmpty() ? null : homeAddressesList.get(0).toString();

        String personalEmailId = personalEmailList.isEmpty() ? null : personalEmailList.get(0).toString();
        List<String> additionalEmailId = new ArrayList<>();
        for (UUID email : additionalEmailsList) {
            additionalEmailId.add(email.toString());
        }
        List<String> additionalPhoneNumberId = new ArrayList<>();
        for (UUID phoneNumber : additionalPhoneNumbersList) {
            additionalPhoneNumberId.add(phoneNumber.toString());
        }
        String phoneNumberId = phoneNumbersList.isEmpty() ? null : phoneNumbersList.get(0).toString();

        return new IdsDTO(
            personalId, birthCertificateId, compulsoryMedInsuranceId, drivingLicenseId,
            foreignPassportId, insuranceNumberOfPersonalAccId, passportId,
            taxPayerIdentNumberId, voluntaryHealthInsuranceId, addressId,
            additionalAddressId, workAddressId, homeAddressId, personalEmailId,
            additionalEmailId, additionalPhoneNumberId, phoneNumberId
        );
    }


    private boolean isIdLinkedToUsername(Id id, String username) {
        Optional<PersonalData> personalData = entityPersonalDataRepository.findByInvocation(username);

        if (personalData.isEmpty()) {
            logger.warn("No personal data found for user: {}", username);
            return false;
        }

        boolean isLinked = personalData.get().getId().equals(id);
        if (!isLinked) {
            logger.warn("Personal data with ID {} is not linked to user: {}", id, username);
        }

        return isLinked;
    }
}