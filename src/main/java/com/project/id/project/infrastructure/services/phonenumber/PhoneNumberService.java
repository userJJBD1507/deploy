package com.project.id.project.infrastructure.services.phonenumber;

import com.project.id.project.application.DTOs.phonenumbers.PhoneNumberDTO;
import com.project.id.project.application.mappers.phonenumbers.PhoneNumberMapper;
import com.project.id.project.application.services.CrudService;
import com.project.id.project.application.services.Security.NewCrudSer;
import com.project.id.project.application.services.pdf.handlers.CrudUpdated;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.phones.entities.PhoneNumber;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaPhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class PhoneNumberService implements CrudUpdated<PhoneNumberDTO, Id> {
//
//    @Autowired
//    private JpaPhoneNumberRepository phoneNumberRepository;
//
//    @Autowired
//    private EntityPersonalDataRepository entityPersonalDataRepository;
//    @Override
//    public void create(String username, PhoneNumberDTO phoneNumberDTO) {
//        PhoneNumber entity = PhoneNumberMapper.toEntity(phoneNumberDTO);
//
//
//        PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).get();
//        phoneNumberRepository.addPhoneNumber(entity);
//        personalData.setPhoneNumber(entity);
//        entity.setPersonalData(personalData);
//
//        phoneNumberRepository.addPhoneNumber(entity);
//        entityPersonalDataRepository.save(personalData);
//    }
//
//    @Override
//    public Optional<PhoneNumberDTO> read(Id id) {
//        PhoneNumber entity = phoneNumberRepository.getPhoneNumber(id);
//        return Optional.of(PhoneNumberMapper.toDTO(entity));
//    }
//
//
//    @Override
//    public void update(Id id, PhoneNumberDTO phoneNumberDTO, Id userId) {
//        PhoneNumber existingEntity = phoneNumberRepository.getPhoneNumber(id);
//        PhoneNumber updatedEntity = PhoneNumberMapper.toEntity(phoneNumberDTO);
//        updatedEntity.setId(existingEntity.getId());
//        PersonalData user = entityPersonalDataRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        updatedEntity.setPersonalData(user);
//        phoneNumberRepository.updatePhoneNumber(updatedEntity);
//    }
//
//    @Override
//    public void delete(Id id) {
//        phoneNumberRepository.deletePhoneNumber(id);
//    }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberService implements CrudUpdated<PhoneNumberDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(PhoneNumberService.class);

    @Autowired
    private JpaPhoneNumberRepository phoneNumberRepository;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @Override
    public void create(String username, PhoneNumberDTO phoneNumberDTO) {
        logger.info("Starting create method for PhoneNumber with username: {}", username);

        try {
            PhoneNumber entity = PhoneNumberMapper.toEntity(phoneNumberDTO);
            PersonalData personalData = entityPersonalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new IllegalArgumentException("Personal data not found for user: " + username));

            personalData.setPhoneNumber(entity);
            entity.setPersonalData(personalData);

            phoneNumberRepository.addPhoneNumber(entity);
            entityPersonalDataRepository.save(personalData);

            logger.info("Successfully created PhoneNumber for user: {}", username);
        } catch (Exception e) {
            logger.error("Error in create method for PhoneNumber with username: {}", username, e);
        }
    }

    @Override
    public Optional<PhoneNumberDTO> read(Id id) {
        logger.info("Reading PhoneNumber with ID: {}", id);
        PhoneNumber entity = phoneNumberRepository.getPhoneNumber(id);
        logger.info("Successfully read PhoneNumber with ID: {}", id);
        return Optional.of(PhoneNumberMapper.toDTO(entity));
    }

    // @Override
    // public void update(Id id, PhoneNumberDTO phoneNumberDTO, Id userId) {
    //     logger.info("Starting update method for PhoneNumber with ID: {}", id);

    //     try {
    //         PhoneNumber existingEntity = phoneNumberRepository.getPhoneNumber(id);
    //         PhoneNumber updatedEntity = PhoneNumberMapper.toEntity(phoneNumberDTO);
    //         updatedEntity.setId(existingEntity.getId());

    //         PersonalData user = entityPersonalDataRepository.findById(userId)
    //                 .orElseThrow(() -> new IllegalArgumentException("User not found"));

    //         updatedEntity.setPersonalData(user);
    //         phoneNumberRepository.updatePhoneNumber(updatedEntity);

    //         logger.info("Successfully updated PhoneNumber with ID: {}", id);
    //     } catch (Exception e) {
    //         logger.error("Error in update method for PhoneNumber with ID: {}", id, e);
    //     }
    // }


    @Override
    public void update(Id id, PhoneNumberDTO phoneNumberDTO, Id userId) {
        logger.info("Starting update method for PhoneNumber with ID: {}", id);

        try {
            PhoneNumber existingEntity = phoneNumberRepository.getPhoneNumber(id);
            delete(existingEntity.getId());
            PhoneNumber updatedEntity = PhoneNumberMapper.toEntity(phoneNumberDTO);

            PersonalData user = entityPersonalDataRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            updatedEntity.setPersonalData(user);
            phoneNumberRepository.updatePhoneNumber(updatedEntity);

            logger.info("Successfully updated PhoneNumber with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error in update method for PhoneNumber with ID: {}", id, e);
        }
    }
    @Override
    public void delete(Id id) {
        logger.info("Starting delete method for PhoneNumber with ID: {}", id);

        try {
            phoneNumberRepository.deletePhoneNumber(id);
            logger.info("Successfully deleted PhoneNumber with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error in delete method for PhoneNumber with ID: {}", id, e);
        }
    }
}
