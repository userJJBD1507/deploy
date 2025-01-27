package com.project.id.project.infrastructure.services.emails;

import com.project.id.project.application.DTOs.emails.AdditionalEmailDTO;
import com.project.id.project.application.mappers.emails.AdditionalEmailMapper;
import com.project.id.project.application.services.CrudService;
import com.project.id.project.application.services.Security.NewCrudSer;
import com.project.id.project.application.services.linkers.DocumentsRepository;
import com.project.id.project.application.services.pdf.handlers.CrudUpdated;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class AdditionalEmailService implements CrudUpdated<AdditionalEmailDTO, Id> {
//
//    @Autowired
//    private JpaAdditionalEmailRepository additionalEmailRepository;
//
//    @Autowired
//    private EntityPersonalDataRepository entityPersonalDataRepository;
//    @Override
//    public void create(String username, AdditionalEmailDTO additionalEmailDTO) {
//        AdditionalEmail entity = AdditionalEmailMapper.toEntity(additionalEmailDTO);
//
//
//        PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).get();
//        personalData.getAdditionalEmailList().add(entity);
//        entity.setPersonalData(personalData);
//
//
//
//        additionalEmailRepository.addAdditionalEmail(entity);
//    }
//
//    @Override
//    public Optional<AdditionalEmailDTO> read(Id id) {
//        AdditionalEmail entity = additionalEmailRepository.getAdditionalEmail(id);
//        return Optional.of(AdditionalEmailMapper.toDTO(entity));
//    }
//
//
//    @Override
//    public void update(Id id, AdditionalEmailDTO additionalEmailDTO, Id userId) {
//        AdditionalEmail existingEntity = additionalEmailRepository.getAdditionalEmail(id);
//        AdditionalEmail updatedEntity = AdditionalEmailMapper.toEntity(additionalEmailDTO);
//        updatedEntity.setId(existingEntity.getId());
//        PersonalData user = entityPersonalDataRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        updatedEntity.setPersonalData(user);
//        additionalEmailRepository.updateAdditionalEmail(updatedEntity);
//    }
//
//    @Override
//    public void delete(Id id) {
//        additionalEmailRepository.deleteAdditionalEmail(id);
//    }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AdditionalEmailService implements CrudUpdated<AdditionalEmailDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(AdditionalEmailService.class);

    @Autowired
    private JpaAdditionalEmailRepository additionalEmailRepository;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @Override
    public void create(String username, AdditionalEmailDTO additionalEmailDTO) {
        logger.info("Starting create method for AdditionalEmail with username: {}", username);

        try {
            AdditionalEmail entity = AdditionalEmailMapper.toEntity(additionalEmailDTO);

            PersonalData personalData = entityPersonalDataRepository.findByInvocation(username)
                    .orElseThrow(() -> new IllegalArgumentException("Personal data not found for username: " + username));

            personalData.getAdditionalEmailList().add(entity);
            entity.setPersonalData(personalData);

            additionalEmailRepository.addAdditionalEmail(entity);

            logger.info("Successfully created AdditionalEmail for user: {}", username);
        } catch (Exception e) {
            logger.error("Error in create method for AdditionalEmail with username: {}", username, e);
        }
    }

    @Override
    public Optional<AdditionalEmailDTO> read(Id id) {
        logger.info("Reading AdditionalEmail with ID: {}", id);
        try {
            AdditionalEmail entity = additionalEmailRepository.getAdditionalEmail(id);
            logger.info("Successfully read AdditionalEmail with ID: {}", id);
            return Optional.of(AdditionalEmailMapper.toDTO(entity));
        } catch (Exception e) {
            logger.error("Error reading AdditionalEmail with ID: {}", id, e);
            return Optional.empty();
        }
    }

    // @Override
    // public void update(Id id, AdditionalEmailDTO additionalEmailDTO, Id userId) {
    //     logger.info("Starting update method for AdditionalEmail with ID: {}", id);

    //     try {
    //         AdditionalEmail existingEntity = additionalEmailRepository.getAdditionalEmail(id);
    //         AdditionalEmail updatedEntity = AdditionalEmailMapper.toEntity(additionalEmailDTO);
    //         updatedEntity.setId(existingEntity.getId());

    //         PersonalData user = entityPersonalDataRepository.findById(userId)
    //                 .orElseThrow(() -> new IllegalArgumentException("User not found"));

    //         updatedEntity.setPersonalData(user);
    //         additionalEmailRepository.updateAdditionalEmail(updatedEntity);

    //         logger.info("Successfully updated AdditionalEmail with ID: {}", id);
    //     } catch (Exception e) {
    //         logger.error("Error updating AdditionalEmail with ID: {}", id, e);
    //     }
    // }


    @Override
    public void update(Id id, AdditionalEmailDTO additionalEmailDTO, Id userId) {
        logger.info("Starting update method for AdditionalEmail with ID: {}", id);

        try {
            AdditionalEmail existingEntity = additionalEmailRepository.getAdditionalEmail(id);
            delete(existingEntity.getId());
            AdditionalEmail updatedEntity = AdditionalEmailMapper.toEntity(additionalEmailDTO);

            PersonalData user = entityPersonalDataRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            updatedEntity.setPersonalData(user);
            additionalEmailRepository.updateAdditionalEmail(updatedEntity);

            logger.info("Successfully updated AdditionalEmail with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error updating AdditionalEmail with ID: {}", id, e);
        }
    }
    @Override
    public void delete(Id id) {
        logger.info("Starting delete method for AdditionalEmail with ID: {}", id);

        try {
            additionalEmailRepository.deleteAdditionalEmail(id);
            logger.info("Successfully deleted AdditionalEmail with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting AdditionalEmail with ID: {}", id, e);
        }
    }
}
