package com.project.id.project.infrastructure.services.emails;

import com.project.id.project.application.DTOs.emails.PersonalEmailDTO;
import com.project.id.project.application.mappers.emails.PersonalEmailMapper;
import com.project.id.project.application.services.CrudService;
import com.project.id.project.application.services.Security.NewCrudSer;
import com.project.id.project.application.services.pdf.handlers.CrudUpdated;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.emails.entities.PersonalEmail;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaPersonalEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class PersonalEmailService implements CrudUpdated<PersonalEmailDTO, Id> {
//
//    @Autowired
//    private JpaPersonalEmailRepository personalEmailRepository;
//
//    @Autowired
//    private EntityPersonalDataRepository entityPersonalDataRepository;
//    @Override
//    public void create(String username, PersonalEmailDTO personalEmailDTO) {
//        PersonalEmail entity = PersonalEmailMapper.toEntity(personalEmailDTO);
//
//
//        PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).get();
//        personalEmailRepository.addPersonalEmail(entity);
//        personalData.setPersonalEmail(entity);
//        entity.setPersonalData(personalData);
//
//
//        personalEmailRepository.addPersonalEmail(entity);
//        entityPersonalDataRepository.save(personalData);
//    }
//
//    @Override
//    public Optional<PersonalEmailDTO> read(Id id) {
//        PersonalEmail entity = personalEmailRepository.getPersonalEmail(id);
//        return Optional.of(PersonalEmailMapper.toDTO(entity));
//    }
//
//
//    @Override
//    public void update(Id id, PersonalEmailDTO personalEmailDTO, Id userId) {
//        PersonalEmail existingEntity = personalEmailRepository.getPersonalEmail(id);
//        PersonalEmail updatedEntity = PersonalEmailMapper.toEntity(personalEmailDTO);
//        updatedEntity.setId(existingEntity.getId());
//        PersonalData user = entityPersonalDataRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        updatedEntity.setPersonalData(user);
//        personalEmailRepository.updatePersonalEmail(updatedEntity);
//    }
//
//    @Override
//    public void delete(Id id) {
//        personalEmailRepository.deletePersonalEmail(id);
//    }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PersonalEmailService implements CrudUpdated<PersonalEmailDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(PersonalEmailService.class);

    @Autowired
    private JpaPersonalEmailRepository personalEmailRepository;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @Override
    public void create(String username, PersonalEmailDTO personalEmailDTO) {
        logger.info("Starting create method for PersonalEmail with username: {}", username);

        try {
            PersonalEmail entity = PersonalEmailMapper.toEntity(personalEmailDTO);

            PersonalData personalData = entityPersonalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new IllegalArgumentException("Personal data not found for username: " + username));

            // personalEmailRepository.addPersonalEmail(entity);
            personalData.setPersonalEmail(entity);
            entity.setPersonalData(personalData);

            personalEmailRepository.addPersonalEmail(entity);
            entityPersonalDataRepository.save(personalData);

            logger.info("Successfully created PersonalEmail for user: {}", username);
        } catch (Exception e) {
            logger.error("Error in create method for PersonalEmail with username: {}", username, e);
        }
    }

    @Override
    public Optional<PersonalEmailDTO> read(Id id) {
        logger.info("Reading PersonalEmail with ID: {}", id);
        try {
            PersonalEmail entity = personalEmailRepository.getPersonalEmail(id);
            logger.info("Successfully read PersonalEmail with ID: {}", id);
            return Optional.of(PersonalEmailMapper.toDTO(entity));
        } catch (Exception e) {
            logger.error("Error reading PersonalEmail with ID: {}", id, e);
            return Optional.empty();
        }
    }

    // @Override
    // public void update(Id id, PersonalEmailDTO personalEmailDTO, Id userId) {
    //     logger.info("Starting update method for PersonalEmail with ID: {}", id);

    //     try {
    //         PersonalEmail existingEntity = personalEmailRepository.getPersonalEmail(id);
    //         PersonalEmail updatedEntity = PersonalEmailMapper.toEntity(personalEmailDTO);
    //         updatedEntity.setId(existingEntity.getId());

    //         PersonalData user = entityPersonalDataRepository.findById(userId)
    //                 .orElseThrow(() -> new IllegalArgumentException("User not found"));

    //         updatedEntity.setPersonalData(user);
    //         personalEmailRepository.updatePersonalEmail(updatedEntity);

    //         logger.info("Successfully updated PersonalEmail with ID: {}", id);
    //     } catch (Exception e) {
    //         logger.error("Error updating PersonalEmail with ID: {}", id, e);
    //     }
    // }


    @Override
    public void update(Id id, PersonalEmailDTO personalEmailDTO, Id userId) {
        logger.info("Starting update method for PersonalEmail with ID: {}", id);

        try {
            PersonalEmail existingEntity = personalEmailRepository.getPersonalEmail(id);
            delete(existingEntity.getId());
            PersonalEmail updatedEntity = PersonalEmailMapper.toEntity(personalEmailDTO);

            PersonalData user = entityPersonalDataRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            updatedEntity.setPersonalData(user);
            personalEmailRepository.updatePersonalEmail(updatedEntity);

            logger.info("Successfully updated PersonalEmail with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error updating PersonalEmail with ID: {}", id, e);
        }
    }
    @Override
    public void delete(Id id) {
        logger.info("Starting delete method for PersonalEmail with ID: {}", id);

        try {
            personalEmailRepository.deletePersonalEmail(id);
            logger.info("Successfully deleted PersonalEmail with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting PersonalEmail with ID: {}", id, e);
        }
    }
}
