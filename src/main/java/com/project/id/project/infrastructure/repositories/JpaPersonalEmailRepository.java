package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.emails.entities.PersonalEmail;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalEmailRepository;
import com.project.id.project.core.repositories.PersonalEmailRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaPersonalEmailRepository implements PersonalEmailRepository {
//
//    @Autowired
//    private EntityPersonalEmailRepository entityPersonalEmailRepository;
//
//    @Override
//    public void addPersonalEmail(PersonalEmail personalEmail) {
//        entityPersonalEmailRepository.save(personalEmail);
//    }
//
//    @Override
//    public PersonalEmail getPersonalEmail(Id id) {
//        return entityPersonalEmailRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Personal email not found with ID: " + id));
//    }
//
//    @Override
//    public void updatePersonalEmail(PersonalEmail personalEmail) {
//        if (!entityPersonalEmailRepository.existsById(personalEmail.getId())) {
//            throw new EntityNotFoundException("Personal email not found with ID: " + personalEmail.getId());
//        }
//        entityPersonalEmailRepository.save(personalEmail);
//    }
//
//    @Override
//    public void deletePersonalEmail(Id id) {
//        if (!entityPersonalEmailRepository.existsById(id)) {
//            throw new EntityNotFoundException("Personal email not found with ID: " + id);
//        }
//        entityPersonalEmailRepository.delete(id);
//    }
//}
//



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaPersonalEmailRepository implements PersonalEmailRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaPersonalEmailRepository.class);

    @Autowired
    private EntityPersonalEmailRepository entityPersonalEmailRepository;

    @Override
    public void addPersonalEmail(PersonalEmail personalEmail) {
        logger.info("Adding personal email: {}", personalEmail);
        entityPersonalEmailRepository.save(personalEmail);
    }

    @Override
    public PersonalEmail getPersonalEmail(Id id) {
        logger.info("Fetching personal email with ID: {}", id);
        return entityPersonalEmailRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Personal email not found with ID: {}", id);
                    return new EntityNotFoundException("Personal email not found with ID: " + id);
                });
    }

    @Override
    public void updatePersonalEmail(PersonalEmail personalEmail) {
        // if (!entityPersonalEmailRepository.existsById(personalEmail.getId())) {
        //     logger.error("Personal email not found with ID: {}", personalEmail.getId());
        //     throw new EntityNotFoundException("Personal email not found with ID: " + personalEmail.getId());
        // }
        logger.info("Updating personal email: {}", personalEmail);
        entityPersonalEmailRepository.save(personalEmail);
    }

    @Override
    public void deletePersonalEmail(Id id) {
        if (!entityPersonalEmailRepository.existsById(id)) {
            logger.error("Personal email not found with ID: {}", id);
            throw new EntityNotFoundException("Personal email not found with ID: " + id);
        }
        logger.info("Deleting personal email with ID: {}", id);
        entityPersonalEmailRepository.delete(id);
    }
}
