package com.project.id.project.application.services.upload;

import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.core.documents.entities.*;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageUploaderController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private EntityPersonalDataRepository personalDataRepository;
    @Operation(
	summary = "Добавление фото Свидетельства о рождении",
	description = "Позволяет добавть фото Свидетельства о рождении"
    )
    @PostMapping("/birthCertificate/upload")
    public ResponseEntity<String> birthCertificateUploadImage(
            @RequestParam("file") MultipartFile file) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        try {
            String filename = storageService.upload(file);
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            BirthCertificate birthCertificate = documents.getBirthCertificate();
            if (birthCertificate == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have a birth certificate document");
            }
            birthCertificate.setPhoto(filename);
            personalDataRepository.save(personalData);
            return ResponseEntity.ok("Photo uploaded and assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Обновленеие фото Свидетельства о рождении",
        description = "Позволяет обновить фото Свидетельства о рождении"
        )
    @PutMapping("/birthCertificate/update")
    public ResponseEntity<String> birthCertificateUpdateImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            BirthCertificate birthCertificate = documents.getBirthCertificate();
            if (birthCertificate == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have a birth certificate document");
            }
            String oldPhoto = birthCertificate.getPhoto();
            if (oldPhoto != null) {
                storageService.delete(oldPhoto);
            }
            String newFilename = storageService.upload(file);
            birthCertificate.setPhoto(newFilename);
            personalDataRepository.save(personalData);

            return ResponseEntity.ok("Photo updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update photo: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Удаление фото Свидетельства о рождении",
        description = "Позволяет удалить фото Свидетельства о рождении"
        )
    @DeleteMapping("/birthCertificate/delete")
    public ResponseEntity<Void> delete() {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            BirthCertificate birthCertificate = documents.getBirthCertificate();
            if (birthCertificate == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            String photo = birthCertificate.getPhoto();
            if (photo != null) {
                storageService.delete(photo);
                birthCertificate.setPhoto(null);
                personalDataRepository.save(personalData);
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }







    @Operation(
        summary = "Добавление фото ОМС",
        description = "Позволяет добавть фото ОМС"
        )
    @PostMapping("/compulsoryMedicalInsurance/upload")
    public ResponseEntity<String> compulsoryMedicalInsuranceUploadImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            String filename = storageService.upload(file);
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            CompulsoryMedicalInsurance compulsoryMedicalInsurance = documents.getCompulsoryMedicalInsurance();
            if (compulsoryMedicalInsurance == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have compulsory medical insurance document");
            }
            compulsoryMedicalInsurance.setPhoto(filename);
            personalDataRepository.save(personalData);
            return ResponseEntity.ok("Photo uploaded and assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Обновление фото ОМС",
        description = "Позволяет обновить фото ОМС"
        )
    @PutMapping("/compulsoryMedicalInsurance/update")
    public ResponseEntity<String> compulsoryMedicalInsuranceUpdateImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            CompulsoryMedicalInsurance compulsoryMedicalInsurance = documents.getCompulsoryMedicalInsurance();
            if (compulsoryMedicalInsurance == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have compulsory medical insurance document");
            }
            String oldPhoto = compulsoryMedicalInsurance.getPhoto();
            if (oldPhoto != null) {
                storageService.delete(oldPhoto);
            }
            String newFilename = storageService.upload(file);
            compulsoryMedicalInsurance.setPhoto(newFilename);
            personalDataRepository.save(personalData);

            return ResponseEntity.ok("Photo updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update photo: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Удаление фото ОМС",
        description = "Позволяет удалить фото ОМС"
        )
    @DeleteMapping("/compulsoryMedicalInsurance/delete")
    public ResponseEntity<Void> deleteCompulsoryMedicalInsurance() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            CompulsoryMedicalInsurance compulsoryMedicalInsurance = documents.getCompulsoryMedicalInsurance();
            if (compulsoryMedicalInsurance == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            String photo = compulsoryMedicalInsurance.getPhoto();
            if (photo != null) {
                storageService.delete(photo);
                compulsoryMedicalInsurance.setPhoto(null);
                personalDataRepository.save(personalData);
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    @Operation(
        summary = "Добавление фото ВУ",
        description = "Позволяет добавть фото ВУ"
        )
    @PostMapping("/drivingLicense/upload")
    public ResponseEntity<String> drivingLicenseUploadImage(
            @RequestParam("file") MultipartFile file) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");


        try {
            String filename = storageService.upload(file);
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            DrivingLicense drivingLicense = documents.getDrivingLicense();
            if (drivingLicense == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have a driving license document");
            }
            drivingLicense.setPhoto(filename);
            personalDataRepository.save(personalData);
            return ResponseEntity.ok("Photo uploaded and assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Обновление фото ВУ",
        description = "Позволяет обновить фото ВУ"
        )
    @PutMapping("/drivingLicense/update")
    public ResponseEntity<String> drivingLicenseUpdateImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            DrivingLicense drivingLicense = documents.getDrivingLicense();
            if (drivingLicense == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have a driving license document");
            }
            String oldPhoto = drivingLicense.getPhoto();
            if (oldPhoto != null) {
                storageService.delete(oldPhoto);
            }
            String newFilename = storageService.upload(file);
            drivingLicense.setPhoto(newFilename);
            personalDataRepository.save(personalData);

            return ResponseEntity.ok("Photo updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update photo: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Удаление фото ВУ",
        description = "Позволяет удалить фото ВУ"
        )
    @DeleteMapping("/drivingLicense/delete")
    public ResponseEntity<Void> deleteDrivingLicense() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            DrivingLicense drivingLicense = documents.getDrivingLicense();
            if (drivingLicense == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            String photo = drivingLicense.getPhoto();
            if (photo != null) {
                storageService.delete(photo);
                drivingLicense.setPhoto(null);
                personalDataRepository.save(personalData);
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    @Operation(
        summary = "Добавление фото загран паспорта",
        description = "Позволяет добавть фото загран паспорта"
        )
    @PostMapping("/foreignPassport/upload")
    public ResponseEntity<String> foreignPassportUploadImage(
            @RequestParam("file") MultipartFile file) {


        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        try {
            String filename = storageService.upload(file);
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            ForeignPassport foreignPassport = documents.getForeignPassport();
            if (foreignPassport == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have a foreign passport document");
            }
            foreignPassport.setPhoto(filename);
            personalDataRepository.save(personalData);
            return ResponseEntity.ok("Photo uploaded and assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Обновление фото загран паспорта",
        description = "Позволяет обновить фото загран паспорта"
        )
    @PutMapping("/foreignPassport/update")
    public ResponseEntity<String> foreignPassportUpdateImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            ForeignPassport foreignPassport = documents.getForeignPassport();
            if (foreignPassport == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have a foreign passport document");
            }
            String oldPhoto = foreignPassport.getPhoto();
            if (oldPhoto != null) {
                storageService.delete(oldPhoto);
            }
            String newFilename = storageService.upload(file);
            foreignPassport.setPhoto(newFilename);
            personalDataRepository.save(personalData);

            return ResponseEntity.ok("Photo updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update photo: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Удаление фото загран паспорта",
        description = "Позволяет удалить фото загран паспорта"
        )
    @DeleteMapping("/foreignPassport/delete")
    public ResponseEntity<Void> foreignPassportDeleteImage() {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            ForeignPassport foreignPassport = documents.getForeignPassport();
            if (foreignPassport == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            String photo = foreignPassport.getPhoto();
            if (photo != null) {
                storageService.delete(photo);
                foreignPassport.setPhoto(null);
                personalDataRepository.save(personalData);
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }







    @Operation(
        summary = "Добавление фото СНИЛС",
        description = "Позволяет добавть фото СНИЛС"
        )
    @PostMapping("/insuranceNumber/upload")
    public ResponseEntity<String> insuranceNumberUploadImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            String filename = storageService.upload(file);
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            InsuranceNumberOfIndividualPersonalAccount insuranceNumber = documents.getInsuranceNumberOfIndividualPersonalAccount();
            if (insuranceNumber == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have an insurance number document");
            }
            insuranceNumber.setPhoto(filename);
            personalDataRepository.save(personalData);
            return ResponseEntity.ok("Photo uploaded and assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Обновление фото СНИЛС",
        description = "Позволяет обновить фото СНИЛС"
        )
    @PutMapping("/insuranceNumber/update")
    public ResponseEntity<String> insuranceNumberUpdateImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            InsuranceNumberOfIndividualPersonalAccount insuranceNumber = documents.getInsuranceNumberOfIndividualPersonalAccount();
            if (insuranceNumber == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have an insurance number document");
            }
            String oldPhoto = insuranceNumber.getPhoto();
            if (oldPhoto != null) {
                storageService.delete(oldPhoto);
            }
            String newFilename = storageService.upload(file);
            insuranceNumber.setPhoto(newFilename);
            personalDataRepository.save(personalData);

            return ResponseEntity.ok("Photo updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update photo: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Удаление фото СНИЛС",
        description = "Позволяет удалить фото СНИЛС"
        )
    @DeleteMapping("/insuranceNumber/delete")
    public ResponseEntity<Void> insuranceNumberDeleteImage() {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            InsuranceNumberOfIndividualPersonalAccount insuranceNumber = documents.getInsuranceNumberOfIndividualPersonalAccount();
            if (insuranceNumber == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            String photo = insuranceNumber.getPhoto();
            if (photo != null) {
                storageService.delete(photo);
                insuranceNumber.setPhoto(null);
                personalDataRepository.save(personalData);
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }











    @Operation(
        summary = "Добавление фото паспорта",
        description = "Позволяет добавть фото паспорта"
        )
    @PostMapping("/passport/upload")
    public ResponseEntity<String> passportUploadImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            String filename = storageService.upload(file);
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            Passport passport = documents.getPassport();
            if (passport == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have a passport document");
            }
            passport.setPhoto(filename);
            personalDataRepository.save(personalData);
            return ResponseEntity.ok("Photo uploaded and assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Обновление фото паспорта",
        description = "Позволяет обновить фото паспорта"
        )
    @PutMapping("/passport/update")
    public ResponseEntity<String> passportUpdateImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            Passport passport = documents.getPassport();
            if (passport == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have a passport document");
            }
            String oldPhoto = passport.getPhoto();
            if (oldPhoto != null) {
                storageService.delete(oldPhoto);
            }
            String newFilename = storageService.upload(file);
            passport.setPhoto(newFilename);
            personalDataRepository.save(personalData);

            return ResponseEntity.ok("Photo updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update photo: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Удаление фото паспорта",
        description = "Позволяет удалить фото паспорта"
        )
    @DeleteMapping("/passport/delete")
    public ResponseEntity<Void> passportDeleteImage() {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Passport passport = documents.getPassport();
            if (passport == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            String photo = passport.getPhoto();
            if (photo != null) {
                storageService.delete(photo);
                passport.setPhoto(null);
                personalDataRepository.save(personalData);
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }








    @Operation(
        summary = "Добавление фото ИНН",
        description = "Позволяет добавть фото ИНН"
        )
    @PostMapping("/taxPayerIdentificationNumber/upload")
    public ResponseEntity<String> taxPayerIdentificationNumberUploadImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            String filename = storageService.upload(file);
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            TaxPayerIdentificationNumber tin = documents.getTaxPayerIdentificationNumber();
            if (tin == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have a tax payer identification number document");
            }
            tin.setPhoto(filename);
            personalDataRepository.save(personalData);
            return ResponseEntity.ok("Photo uploaded and assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Обновление фото ИНН",
        description = "Позволяет обновить фото ИНН"
        )
    @PutMapping("/taxPayerIdentificationNumber/update")
    public ResponseEntity<String> taxPayerIdentificationNumberUpdateImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            TaxPayerIdentificationNumber tin = documents.getTaxPayerIdentificationNumber();
            if (tin == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have a tax payer identification number document");
            }
            String oldPhoto = tin.getPhoto();
            if (oldPhoto != null) {
                storageService.delete(oldPhoto);
            }
            String newFilename = storageService.upload(file);
            tin.setPhoto(newFilename);
            personalDataRepository.save(personalData);

            return ResponseEntity.ok("Photo updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update photo: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Удаление фото ИНН",
        description = "Позволяет удалить фото ИНН"
        )
    @DeleteMapping("/taxPayerIdentificationNumber/delete")
    public ResponseEntity<Void> taxPayerIdentificationNumberDeleteImage() {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            TaxPayerIdentificationNumber tin = documents.getTaxPayerIdentificationNumber();
            if (tin == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            String photo = tin.getPhoto();
            if (photo != null) {
                storageService.delete(photo);
                tin.setPhoto(null);
                personalDataRepository.save(personalData);
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }









    @Operation(
        summary = "Добавление фото ДМС",
        description = "Позволяет добавть фото ДМС"
        )
    @PostMapping("/voluntaryHealthInsurance/upload")
    public ResponseEntity<String> voluntaryHealthInsuranceUploadImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            String filename = storageService.upload(file);
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            VoluntaryHealthInsurance voluntaryHealthInsurance = documents.getVoluntaryHealthInsurance();
            if (voluntaryHealthInsurance == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have a voluntary health insurance document");
            }
            voluntaryHealthInsurance.setPhoto(filename);
            personalDataRepository.save(personalData);
            return ResponseEntity.ok("Photo uploaded and assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Обновление фото ДМС",
        description = "Позволяет обновить фото ДМС"
        )
    @PutMapping("/voluntaryHealthInsurance/update")
    public ResponseEntity<String> voluntaryHealthInsuranceUpdateImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No documents associated with the user");
            }
            VoluntaryHealthInsurance voluntaryHealthInsurance = documents.getVoluntaryHealthInsurance();
            if (voluntaryHealthInsurance == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User does not have a voluntary health insurance document");
            }
            String oldPhoto = voluntaryHealthInsurance.getPhoto();
            if (oldPhoto != null) {
                storageService.delete(oldPhoto);
            }
            String newFilename = storageService.upload(file);
            voluntaryHealthInsurance.setPhoto(newFilename);
            personalDataRepository.save(personalData);

            return ResponseEntity.ok("Photo updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update photo: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Удаление фото ДМС",
        description = "Позволяет удалить фото ДМС"
        )
    @DeleteMapping("/voluntaryHealthInsurance/delete")
    public ResponseEntity<Void> voluntaryHealthInsuranceDeleteImage() {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Documents documents = personalData.getDocuments();
            if (documents == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            VoluntaryHealthInsurance voluntaryHealthInsurance = documents.getVoluntaryHealthInsurance();
            if (voluntaryHealthInsurance == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            String photo = voluntaryHealthInsurance.getPhoto();
            if (photo != null) {
                storageService.delete(photo);
                voluntaryHealthInsurance.setPhoto(null);
                personalDataRepository.save(personalData);
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    @Operation(
        summary = "Добавление аватарки",
        description = "Позволяет добавть аватарку"
        )
    @PostMapping("/personalData/upload")
    public ResponseEntity<String> personalDataUploadImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            String filename = storageService.upload(file);
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            personalData.setAvatar(filename);
            personalDataRepository.save(personalData);
            return ResponseEntity.ok("Photo uploaded and assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Обновление аватарки",
        description = "Позволяет обновить аватарку"
        )
    @PutMapping("/personalData/update")
    public ResponseEntity<String> personalDataUpdateImage(
            @RequestParam("file") MultipartFile file) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");

        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            String oldPhoto = personalData.getAvatar();
            if (oldPhoto != null) {
                storageService.delete(oldPhoto);
            }
            String newFilename = storageService.upload(file);
            personalData.setAvatar(newFilename);
            personalDataRepository.save(personalData);

            return ResponseEntity.ok("Photo updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update photo: " + e.getMessage());
        }
    }
    @Operation(
        summary = "Удаление аватарки",
        description = "Позволяет удалить аватарку"
        )
    @DeleteMapping("/personalData/delete")
    public ResponseEntity<Void> personalDataDeleteImage() {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getClaimAsString("preferred_username");
        try {
            PersonalData personalData = personalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            String photo = personalData.getAvatar();
            if (photo != null) {
                storageService.delete(photo);
                personalData.setAvatar(null);
                personalDataRepository.save(personalData);
            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

