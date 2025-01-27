package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.core.repositories.PersonalDataRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaPersonalDataRepository implements PersonalDataRepository {
//
//    @Autowired
//    private EntityPersonalDataRepository entityPersonalDataRepository;
//
//    @Override
//    public void addPersonalData(PersonalData personalData) {
//        entityPersonalDataRepository.save(personalData);
//    }
//
//    @Override
//    public PersonalData getPersonalData(Id id) {
//        return entityPersonalDataRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Personal data not found with ID: " + id));
//    }
//
//    @Override
//    public void deletePersonalData(Id id) {
//        if (!entityPersonalDataRepository.existsById(id)) {
//            throw new EntityNotFoundException("Personal data not found with ID: " + id);
//        }
//        entityPersonalDataRepository.delete(id);
//    }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaPersonalDataRepository implements PersonalDataRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaPersonalDataRepository.class);

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @Override
    public void addPersonalData(PersonalData personalData) {
        logger.info("Adding personal data: {}", personalData);
        entityPersonalDataRepository.save(personalData);
    }

    @Override
    public PersonalData getPersonalData(Id id) {
        logger.info("Fetching personal data with ID: {}", id);
        return entityPersonalDataRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Personal data not found with ID: {}", id);
                    return new EntityNotFoundException("Personal data not found with ID: " + id);
                });
    }

    @Override
    public void deletePersonalData(Id id) {
        if (!entityPersonalDataRepository.existsById(id)) {
            logger.error("Personal data not found with ID: {}", id);
            throw new EntityNotFoundException("Personal data not found with ID: " + id);
        }
        logger.info("Deleting personal data with ID: {}", id);
        entityPersonalDataRepository.delete(id);
    }
}
