package com.project.id.project.infrastructure.services.personal;

import com.project.id.project.application.DTOs.personal.PersonalDataDTO;
import com.project.id.project.application.mappers.personal.PersonalDataMapper;
import com.project.id.project.application.services.OwnDataService;
import com.project.id.project.application.services.linkers.AddressesRepository;
import com.project.id.project.application.services.linkers.DocumentsRepository;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.addresses.entities.HomeAddress;
import com.project.id.project.core.addresses.entities.WorkAddress;
import com.project.id.project.core.documents.entities.BirthCertificate;
import com.project.id.project.core.documents.entities.CompulsoryMedicalInsurance;
import com.project.id.project.core.documents.entities.DrivingLicense;
import com.project.id.project.core.documents.entities.ForeignPassport;
import com.project.id.project.core.documents.entities.InsuranceNumberOfIndividualPersonalAccount;
import com.project.id.project.core.documents.entities.Passport;
import com.project.id.project.core.documents.entities.TaxPayerIdentificationNumber;
import com.project.id.project.core.documents.entities.VoluntaryHealthInsurance;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.emails.entities.PersonalEmail;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.phones.entities.AdditionalPhoneNumber;
import com.project.id.project.core.phones.entities.PhoneNumber;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalPhoneNumberRepository;
import com.project.id.project.infrastructure.repositories.JpaBirthCertificateRepository;
import com.project.id.project.infrastructure.repositories.JpaCompulsoryMedicalInsuranceRepository;
import com.project.id.project.infrastructure.repositories.JpaDrivingLicenseRepository;
import com.project.id.project.infrastructure.repositories.JpaForeignPassportRepository;
import com.project.id.project.infrastructure.repositories.JpaHomeAddressRepository;
import com.project.id.project.infrastructure.repositories.JpaInsuranceNumberOfIndividualPersonalAccountRepository;
import com.project.id.project.infrastructure.repositories.JpaPassportRepository;
import com.project.id.project.infrastructure.repositories.JpaPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaPersonalEmailRepository;
import com.project.id.project.infrastructure.repositories.JpaPhoneNumberRepository;
import com.project.id.project.infrastructure.repositories.JpaTaxPayerIdentificationNumberRepository;
import com.project.id.project.infrastructure.repositories.JpaVoluntaryHealthInsuranceRepository;
import com.project.id.project.infrastructure.repositories.JpaWorkAddressRepository;
import com.project.id.project.infrastructure.services.addresses.AdditionalAddressService;
import com.project.id.project.infrastructure.services.addresses.HomeAddressService;
import com.project.id.project.infrastructure.services.addresses.WorkAddressService;
import com.project.id.project.infrastructure.services.documents.BirthCertificateService;
import com.project.id.project.infrastructure.services.documents.CompulsoryMedicalInsuranceService;
import com.project.id.project.infrastructure.services.documents.DrivingLicenseService;
import com.project.id.project.infrastructure.services.documents.ForeignPassportService;
import com.project.id.project.infrastructure.services.documents.InsuranceNumberOfIndividualPersonalAccountService;
import com.project.id.project.infrastructure.services.documents.PassportService;
import com.project.id.project.infrastructure.services.documents.TaxPayerIdentificationNumberService;
import com.project.id.project.infrastructure.services.documents.VoluntaryHealthInsuranceService;
import com.project.id.project.infrastructure.services.emails.AdditionalEmailService;
import com.project.id.project.infrastructure.services.emails.PersonalEmailService;
import com.project.id.project.infrastructure.services.phonenumber.AdditionalPhoneNumberService;
import com.project.id.project.infrastructure.services.phonenumber.PhoneNumberService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.keycloak.representations.idm.UserRepresentation;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PersonalDataService implements OwnDataService<PersonalDataDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(PersonalDataService.class);

    @Autowired
    private JpaPersonalDataRepository jpaPersonalDataRepository;

    @Autowired
    private AddressesRepository addressesRepository;

    @Autowired
    private DocumentsRepository documentsRepository;

    @Autowired
    private JpaAdditionalEmailRepository jpaAdditionalEmailRepository;
    @Autowired
    private HomeAddressService homeAddressService;
    @Autowired
    private WorkAddressService workAddressService;
    @Autowired
    private AdditionalAddressService additionalAddressService;


    @Autowired
    private BirthCertificateService birthCertificateService;
    @Autowired
    private CompulsoryMedicalInsuranceService compulsoryMedicalInsuranceService;
    @Autowired
    private VoluntaryHealthInsuranceService voluntaryHealthInsuranceService;
    @Autowired
    private ForeignPassportService foreignPassportService;
    @Autowired
    private PassportService passportService;
    @Autowired
    private TaxPayerIdentificationNumberService taxPayerIdentificationNumberService;
    @Autowired
    private DrivingLicenseService drivingLicenseService;
    @Autowired
    private InsuranceNumberOfIndividualPersonalAccountService insuranceNumberOfIndividualPersonalAccountService;

    @Autowired
    private PhoneNumberService phoneNumberService;
    @Autowired
    private PersonalEmailService personalEmailService;
    @Autowired
    private AdditionalPhoneNumberService additionalPhoneNumberService;
    @Autowired
    private AdditionalEmailService additionalEmailService;
    @Autowired
    private JpaHomeAddressRepository jpaHomeAddressRepository;
    @Autowired
    private JpaWorkAddressRepository jpaWorkAddressRepository;
    @Autowired
    private JpaAdditionalAddressRepository jpaAdditionalAddressRepository;
    @Autowired
    private JpaAdditionalPhoneNumberRepository jpaAdditionalPhoneNumberRepository;
    @Autowired
    private JpaPersonalEmailRepository jpaPersonalEmailRepository;
    @Autowired
    private JpaPhoneNumberRepository jpaPhoneNumberRepository;
    @Autowired
    private JpaBirthCertificateRepository jpaBirthCertificateRepository;
    @Autowired
    private JpaCompulsoryMedicalInsuranceRepository jpaCompulsoryMedicalInsuranceRepository;
    @Autowired
    private JpaPassportRepository jpaPassportRepository;
    @Autowired
    private JpaInsuranceNumberOfIndividualPersonalAccountRepository jpaInsuranceNumberOfIndividualPersonalAccountRepository;
    @Autowired
    private JpaDrivingLicenseRepository jpaDrivingLicenseRepository;
    @Autowired
    private JpaTaxPayerIdentificationNumberRepository jpaTaxPayerIdentificationNumberRepository;
    @Autowired
    private JpaVoluntaryHealthInsuranceRepository jpaVoluntaryHealthInsuranceRepository;
    @Autowired
    private JpaForeignPassportRepository jpaForeignPassportRepository;
    @Autowired
    private KeycloakService keycloakService;
    @Override
    public void create(PersonalDataDTO entity) {
        logger.info("Starting create method for PersonalData");

        try { 
            PersonalData personalData = PersonalDataMapper.toEntity(entity);

            Addresses addresses = new Addresses();
            addresses.setPersonalData(personalData);
            personalData.setAddresses(addresses);

            Documents documents = new Documents();
            documents.setPersonalData(personalData);
            personalData.setDocuments(documents);;

            jpaPersonalDataRepository.addPersonalData(personalData);

            addressesRepository.save(addresses);
            documentsRepository.save(documents);

            logger.info("Successfully created PersonalData for entity: {}", personalData);
        } catch (Exception e) {
            logger.error("Error in create method for PersonalData", e);
        }
    }

    @Override
    public Optional<PersonalDataDTO> read(Id id) {
        logger.info("Reading PersonalData with ID: {}", id);
        try {
            PersonalData personalData = jpaPersonalDataRepository.getPersonalData(id);
            logger.info("Successfully read PersonalData with ID: {}", id);
            return Optional.of(PersonalDataMapper.toDTO(personalData));
        } catch (Exception e) {
            logger.error("Error reading PersonalData with ID: {}", id, e);
            return Optional.empty();
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Id id, PersonalDataDTO entity) {
        logger.info("Starting update method for PersonalData with ID: {}", id);
        try {
            PersonalData currentPersonalData = jpaPersonalDataRepository.getPersonalData(id);
            if (currentPersonalData == null) {
                logger.error("PersonalData with ID {} not found in database", id);
                return;
            }
            String currentUsername = currentPersonalData.getInvocation();
            String keycloakUserId = keycloakService.getUserIdByUsername(currentUsername);
            if (keycloakUserId == null) {
                logger.error("User with username '{}' not found in Keycloak", currentUsername);
                return;
            }
            keycloakService.updateKeycloakUser(keycloakUserId, entity);
            PersonalData updatedPersonalData = PersonalDataMapper.toEntity(entity);
            updatedPersonalData.setId(id);
            jpaPersonalDataRepository.addPersonalData(updatedPersonalData);

            logger.info("Successfully updated PersonalData with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error updating PersonalData with ID: {}", id, e);
        }
    }


    @Autowired
    EntityPersonalDataRepository entityPersonalDataRepository;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Id id) {
        logger.info("Starting delete method for PersonalData with ID: {}", id);
        PersonalData personalData = jpaPersonalDataRepository.getPersonalData(id);
        if (personalData == null) {
            logger.error("PersonalData with ID {} not found", id);
            return;
        }


        Documents documents = personalData.getDocuments();
        Addresses addresses = personalData.getAddresses();
        HomeAddress homeAddress = personalData.getAddresses().getHomeAddress();
        WorkAddress workAddress = personalData.getAddresses().getWorkAddress();
        List<AdditionalAddress> additionalAddresses = personalData.getAddresses().getAdditionalAddress();

        List<AdditionalEmail> additionalEmails = personalData.getAdditionalEmailList();
        PersonalEmail personalEmail = personalData.getPersonalEmail();
        PhoneNumber phoneNumber = personalData.getPhoneNumber();
        List<AdditionalPhoneNumber> additionalPhoneNumbers = personalData.getAdditionalPhoneNumberList();


        if (documents != null) {
            clearDocumentForeignKeys(documents);
            deleteRelatedDocuments(documents);
            System.out.println("200 ok");
        }


        if (addresses != null) {
            clearAddressForeignKeys(addresses);
            deleteRelatedAddresses(addresses, homeAddress, workAddress, additionalAddresses);
            System.out.println("200 ok");
        }

        if (additionalEmails != null) {
            for (AdditionalEmail e : additionalEmails) {
                if (e != null) {
                    additionalEmailService.delete(e.getId());
                }
            }
        }
        if (personalEmail != null) {
            personalEmailService.delete(personalEmail.getId());
        }
        if (phoneNumber != null) {
            phoneNumberService.delete(phoneNumber.getId());
        }
        if (additionalPhoneNumbers != null) {
            for (AdditionalPhoneNumber e : additionalPhoneNumbers) {
                if (e != null) {
                    additionalPhoneNumberService.delete(e.getId());
                }
            }
        }
        updateAndDeletePersonalData(personalData.getId());
        System.out.println("gogog");
        String keycloakUsername = personalData.getInvocation();
        String keycloakUserId = keycloakService.getUserIdByUsername(keycloakUsername);
        if (keycloakUserId != null) {
            keycloakService.deleteKeycloakUser(keycloakUserId);
        } else {
            logger.error("User with username {} not found in Keycloak", keycloakUsername);
        }

        logger.info("Successfully deleted PersonalData with ID: {}", id);
    }

    private void clearAddressForeignKeys(Addresses addresses) {
        if (addresses.getHomeAddress() != null) {
            addresses.setHomeAddress(null);
        }
        if (addresses.getWorkAddress() != null) {
            addresses.setWorkAddress(null);
        }
        if (addresses.getAdditionalAddress() != null) {
            addresses.setAdditionalAddress(null);
        }
        addressesRepository.save(addresses);
    }

    private void deleteRelatedAddresses(Addresses addresses, HomeAddress homeAddress, WorkAddress workAddress, List<AdditionalAddress> additionalAddresses) {
        if (homeAddress != null) {
            homeAddressService.delete(homeAddress.getId());
        }
        if (workAddress != null) {
            workAddressService.delete(workAddress.getId());
        }
        for (AdditionalAddress additionalAddress : additionalAddresses) {
            if (additionalAddress != null) {
                additionalAddressService.delete(additionalAddress.getId());
            }
        }
    }

    private void clearDocumentForeignKeys(Documents documents) {
        documents.setBirthCertificate(null);
        documents.setCompulsoryMedicalInsurance(null);
        documents.setPassport(null);
        documents.setInsuranceNumberOfIndividualPersonalAccount(null);
        documents.setDrivingLicense(null);
        documents.setTaxPayerIdentificationNumber(null);
        documents.setVoluntaryHealthInsurance(null);
        documents.setForeignPassport(null);
        documentsRepository.save(documents);
    }

    // private void deleteRelatedDocuments(Documents documents) {
    //     if (documents.getBirthCertificate() != null) {
    //         birthCertificateService.delete(documents.getBirthCertificate().getId());
    //     }
    //     if (documents.getCompulsoryMedicalInsurance() != null) {
    //         compulsoryMedicalInsuranceService.delete(documents.getCompulsoryMedicalInsurance().getId());
    //     }
    //     if (documents.getPassport() != null) {
    //         passportService.delete(documents.getPassport().getId());
    //     }
    //     if (documents.getInsuranceNumberOfIndividualPersonalAccount() != null) {
    //         insuranceNumberOfIndividualPersonalAccountService.delete(documents.getInsuranceNumberOfIndividualPersonalAccount().getId());
    //     }
    //     if (documents.getDrivingLicense() != null) {
    //         jpaDrivingLicenseRepository.deleteDrivingLicense(documents.getDrivingLicense().getId());
    //     }
    //     if (documents.getTaxPayerIdentificationNumber() != null) {
    //         taxPayerIdentificationNumberService.delete(documents.getTaxPayerIdentificationNumber().getId());
    //     }
    //     if (documents.getVoluntaryHealthInsurance() != null) {
    //         voluntaryHealthInsuranceService.delete(documents.getVoluntaryHealthInsurance().getId());
    //     }
    //     if (documents.getForeignPassport() != null) {
    //         foreignPassportService.delete(documents.getForeignPassport().getId());
    //     }
    // }
    @Transactional(rollbackFor = Exception.class)
    private void deleteRelatedDocuments(Documents documents) {
        UUID documentsId = documents.getId().id();
            entityManager.createNativeQuery("DELETE FROM birth_certificate_table WHERE id = (SELECT birth_certificate_id FROM all_documents_table WHERE id = :documentsId)")
                     .setParameter("documentsId", documentsId)
                     .executeUpdate();
    
        entityManager.createNativeQuery("DELETE FROM required_health_insurance_table WHERE id = (SELECT compulsory_med_insurance_id FROM all_documents_table WHERE id = :documentsId)")
                     .setParameter("documentsId", documentsId)
                     .executeUpdate();
    
        entityManager.createNativeQuery("DELETE FROM driving_license_table WHERE id = (SELECT driving_license_id FROM all_documents_table WHERE id = :documentsId)")
                     .setParameter("documentsId", documentsId)
                     .executeUpdate();
    
        entityManager.createNativeQuery("DELETE FROM foreign_passport_table WHERE id = (SELECT foreign_passport_id FROM all_documents_table WHERE id = :documentsId)")
                     .setParameter("documentsId", documentsId)
                     .executeUpdate();
    
        entityManager.createNativeQuery("DELETE FROM insurance_number_of_individual_personal_account WHERE id = (SELECT insurance_number_of_personal_acc_id FROM all_documents_table WHERE id = :documentsId)")
                     .setParameter("documentsId", documentsId)
                     .executeUpdate();
    
        entityManager.createNativeQuery("DELETE FROM passport_table WHERE id = (SELECT passport_id FROM all_documents_table WHERE id = :documentsId)")
                     .setParameter("documentsId", documentsId)
                     .executeUpdate();
    
        entityManager.createNativeQuery("DELETE FROM taxpayer_identification_number_table WHERE id = (SELECT tax_payer_ident_number_id FROM all_documents_table WHERE id = :documentsId)")
                     .setParameter("documentsId", documentsId)
                     .executeUpdate();
    
        entityManager.createNativeQuery("DELETE FROM voluntary_health_insurance_table WHERE id = (SELECT voluntary_health_insurance_id FROM all_documents_table WHERE id = :documentsId)")
                     .setParameter("documentsId", documentsId)
                     .executeUpdate();
    
        // Удаляем саму запись в all_documents_table
        entityManager.createNativeQuery("DELETE FROM all_documents_table WHERE id = :documentsId")
                     .setParameter("documentsId", documentsId)
                     .executeUpdate();




        

        



        entityManager.createNativeQuery("DELETE FROM taxpayer_identification_number_table WHERE id NOT IN (SELECT tax_payer_ident_number_id FROM all_documents_table WHERE tax_payer_ident_number_id IS NOT NULL)")
                     .executeUpdate();
        entityManager.createNativeQuery("DELETE FROM birth_certificate_table WHERE id NOT IN (SELECT birth_certificate_id FROM all_documents_table WHERE birth_certificate_id IS NOT NULL)")
                     .executeUpdate();
        entityManager.createNativeQuery("DELETE FROM required_health_insurance_table WHERE id NOT IN (SELECT compulsory_med_insurance_id FROM all_documents_table WHERE compulsory_med_insurance_id IS NOT NULL)")
                     .executeUpdate();
        entityManager.createNativeQuery("DELETE FROM driving_license_table WHERE id NOT IN (SELECT driving_license_id FROM all_documents_table WHERE driving_license_id IS NOT NULL)")
                     .executeUpdate();
        entityManager.createNativeQuery("DELETE FROM foreign_passport_table WHERE id NOT IN (SELECT foreign_passport_id FROM all_documents_table WHERE foreign_passport_id IS NOT NULL)")
                     .executeUpdate();
        entityManager.createNativeQuery("DELETE FROM insurance_number_of_individual_personal_account WHERE id NOT IN (SELECT insurance_number_of_personal_acc_id FROM all_documents_table WHERE insurance_number_of_personal_acc_id IS NOT NULL)")
                     .executeUpdate();
        entityManager.createNativeQuery("DELETE FROM passport_table WHERE id NOT IN (SELECT passport_id FROM all_documents_table WHERE passport_id IS NOT NULL)")
                     .executeUpdate();
        entityManager.createNativeQuery("DELETE FROM voluntary_health_insurance_table WHERE id NOT IN (SELECT voluntary_health_insurance_id FROM all_documents_table WHERE voluntary_health_insurance_id IS NOT NULL)")
                     .executeUpdate();
    }
    
    



    @PersistenceContext
    private EntityManager entityManager;
    @Transactional(rollbackFor = Exception.class)
    public void updateAndDeletePersonalData(Id personalDataId) {
        UUID uuid = personalDataId.id();
        entityManager.createNativeQuery("UPDATE addresses_table SET personal_data_id = NULL WHERE personal_data_id = :personalDataId")
        .setParameter("personalDataId", uuid)
        .executeUpdate();

    entityManager.createNativeQuery("UPDATE all_documents_table SET personal_data_id = NULL WHERE personal_data_id = :personalDataId")
            .setParameter("personalDataId", uuid)
            .executeUpdate();

    entityManager.createNativeQuery("DELETE FROM addresses_table WHERE personal_data_id = :personalDataId")
            .setParameter("personalDataId", uuid)
            .executeUpdate();

    entityManager.createNativeQuery("DELETE FROM all_documents_table WHERE personal_data_id = :personalDataId")
            .setParameter("personalDataId", uuid)
            .executeUpdate();

    entityManager.createNativeQuery("DELETE FROM personal_data_table WHERE id = :personalDataId")
            .setParameter("personalDataId", uuid)
            .executeUpdate();
    entityManager.createNativeQuery("delete from addresses_table where personal_data_id is NULL")
        .executeUpdate();
    entityManager.createNativeQuery("delete from all_documents_table where personal_data_id is NULL")
        .executeUpdate();
    }
}
