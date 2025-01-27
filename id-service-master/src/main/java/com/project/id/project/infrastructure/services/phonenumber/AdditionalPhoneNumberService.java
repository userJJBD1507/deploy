package com.project.id.project.infrastructure.services.phonenumber;

import com.project.id.project.application.DTOs.phonenumbers.AdditionalPhoneNumberDTO;
import com.project.id.project.application.mappers.phonenumbers.AdditionalPhoneNumberMapper;
import com.project.id.project.application.services.CrudService;
import com.project.id.project.application.services.Security.NewCrudSer;
import com.project.id.project.application.services.pdf.handlers.CrudUpdated;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.phones.entities.AdditionalPhoneNumber;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalPhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class AdditionalPhoneNumberService implements CrudUpdated<AdditionalPhoneNumberDTO, Id> {
//
//    @Autowired
//    private JpaAdditionalPhoneNumberRepository additionalPhoneNumberRepository;
//
//
//    @Autowired
//    private EntityPersonalDataRepository entityPersonalDataRepository;
//    @Override
//    public void create(String username, AdditionalPhoneNumberDTO additionalPhoneNumberDTO) {
//        AdditionalPhoneNumber entity = AdditionalPhoneNumberMapper.toEntity(additionalPhoneNumberDTO);
//
//
//        PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).get();
//        personalData.getAdditionalPhoneNumberList().add(entity);
//        entity.setPersonalData(personalData);
//
//
//        additionalPhoneNumberRepository.addAdditionalPhoneNumber(entity);
//    }
//
//    @Override
//    public Optional<AdditionalPhoneNumberDTO> read(Id id) {
//        AdditionalPhoneNumber entity = additionalPhoneNumberRepository.getAdditionalPhoneNumber(id);
//        return Optional.of(AdditionalPhoneNumberMapper.toDTO(entity));
//    }
//
//    @Override
//    public void update(Id id, AdditionalPhoneNumberDTO additionalPhoneNumberDTO, Id userId) {
//        AdditionalPhoneNumber existingEntity = additionalPhoneNumberRepository.getAdditionalPhoneNumber(id);
//        AdditionalPhoneNumber updatedEntity = AdditionalPhoneNumberMapper.toEntity(additionalPhoneNumberDTO);
//        updatedEntity.setId(existingEntity.getId());
//        PersonalData user = entityPersonalDataRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        updatedEntity.setPersonalData(user);
//        additionalPhoneNumberRepository.updateAdditionalPhoneNumber(updatedEntity);
//    }
//
//    @Override
//    public void delete(Id id) {
//        additionalPhoneNumberRepository.deleteAdditionalPhoneNumber(id);
//    }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AdditionalPhoneNumberService implements CrudUpdated<AdditionalPhoneNumberDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(AdditionalPhoneNumberService.class);

    @Autowired
    private JpaAdditionalPhoneNumberRepository additionalPhoneNumberRepository;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @Override
    public void create(String username, AdditionalPhoneNumberDTO additionalPhoneNumberDTO) {
        logger.info("Starting create method for AdditionalPhoneNumber with username: {}", username);

        try {
            AdditionalPhoneNumber entity = AdditionalPhoneNumberMapper.toEntity(additionalPhoneNumberDTO);
            PersonalData personalData = entityPersonalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new IllegalArgumentException("Personal data not found for user: " + username));

            personalData.getAdditionalPhoneNumberList().add(entity);
            entity.setPersonalData(personalData);

            additionalPhoneNumberRepository.addAdditionalPhoneNumber(entity);

            logger.info("Successfully created AdditionalPhoneNumber for user: {}", username);
        } catch (Exception e) {
            logger.error("Error in create method for AdditionalPhoneNumber with username: {}", username, e);
        }
    }

    @Override
    public Optional<AdditionalPhoneNumberDTO> read(Id id) {
        logger.info("Reading AdditionalPhoneNumber with ID: {}", id);
        AdditionalPhoneNumber entity = additionalPhoneNumberRepository.getAdditionalPhoneNumber(id);
        logger.info("Successfully read AdditionalPhoneNumber with ID: {}", id);
        return Optional.of(AdditionalPhoneNumberMapper.toDTO(entity));
    }

    // @Override
    // public void update(Id id, AdditionalPhoneNumberDTO additionalPhoneNumberDTO, Id userId) {
    //     logger.info("Starting update method for AdditionalPhoneNumber with ID: {}", id);

    //     try {
    //         AdditionalPhoneNumber existingEntity = additionalPhoneNumberRepository.getAdditionalPhoneNumber(id);
    //         AdditionalPhoneNumber updatedEntity = AdditionalPhoneNumberMapper.toEntity(additionalPhoneNumberDTO);
    //         updatedEntity.setId(existingEntity.getId());

    //         PersonalData user = entityPersonalDataRepository.findById(userId)
    //                 .orElseThrow(() -> new IllegalArgumentException("User not found"));

    //         updatedEntity.setPersonalData(user);
    //         additionalPhoneNumberRepository.updateAdditionalPhoneNumber(updatedEntity);

    //         logger.info("Successfully updated AdditionalPhoneNumber with ID: {}", id);
    //     } catch (Exception e) {
    //         logger.error("Error in update method for AdditionalPhoneNumber with ID: {}", id, e);
    //     }
    // }


    @Override
    public void update(Id id, AdditionalPhoneNumberDTO additionalPhoneNumberDTO, Id userId) {
        logger.info("Starting update method for AdditionalPhoneNumber with ID: {}", id);

        try {
            AdditionalPhoneNumber existingEntity = additionalPhoneNumberRepository.getAdditionalPhoneNumber(id);
            delete(existingEntity.getId());
            AdditionalPhoneNumber updatedEntity = AdditionalPhoneNumberMapper.toEntity(additionalPhoneNumberDTO);

            PersonalData user = entityPersonalDataRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            updatedEntity.setPersonalData(user);
            additionalPhoneNumberRepository.updateAdditionalPhoneNumber(updatedEntity);

            logger.info("Successfully updated AdditionalPhoneNumber with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error in update method for AdditionalPhoneNumber with ID: {}", id, e);
        }
    }

    @Override
    public void delete(Id id) {
        logger.info("Starting delete method for AdditionalPhoneNumber with ID: {}", id);

        try {
            additionalPhoneNumberRepository.deleteAdditionalPhoneNumber(id);
            logger.info("Successfully deleted AdditionalPhoneNumber with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error in delete method for AdditionalPhoneNumber with ID: {}", id, e);
        }
    }
}
