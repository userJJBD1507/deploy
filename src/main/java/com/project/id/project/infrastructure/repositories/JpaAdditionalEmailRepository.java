package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.repositories.AdditionalEmailRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalEmailRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaAdditionalEmailRepository implements AdditionalEmailRepository {
//
//    @Autowired
//    private EntityAdditionalEmailRepository entityAdditionalEmailRepository;
//
//    @Override
//    public void addAdditionalEmail(AdditionalEmail additionalEmail) {
//        entityAdditionalEmailRepository.save(additionalEmail);
//    }
//
//    @Override
//    public AdditionalEmail getAdditionalEmail(Id id) {
//        return entityAdditionalEmailRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Email not found with ID: " + id));
//    }
//
//    @Override
//    public void updateAdditionalEmail(AdditionalEmail additionalEmail) {
//        if (!entityAdditionalEmailRepository.existsById(additionalEmail.getId())) {
//            throw new EntityNotFoundException("Email not found with ID: " + additionalEmail.getId());
//        }
//        entityAdditionalEmailRepository.save(additionalEmail);
//    }
//
//    @Override
//    public void deleteAdditionalEmail(Id id) {
//        if (!entityAdditionalEmailRepository.existsById(id)) {
//            throw new EntityNotFoundException("Email not found with ID: " + id);
//        }
//        entityAdditionalEmailRepository.delete(id);
//    }
//}


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaAdditionalEmailRepository implements AdditionalEmailRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaAdditionalEmailRepository.class);

    @Autowired
    private EntityAdditionalEmailRepository entityAdditionalEmailRepository;

    @Override
    public void addAdditionalEmail(AdditionalEmail additionalEmail) {
        logger.info("Adding additional email: {}", additionalEmail);
        entityAdditionalEmailRepository.save(additionalEmail);
    }

    @Override
    public AdditionalEmail getAdditionalEmail(Id id) {
        logger.info("Fetching additional email with ID: {}", id);
        return entityAdditionalEmailRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Email not found with ID: {}", id);
                    return new EntityNotFoundException("Email not found with ID: " + id);
                });
    }

    @Override
    public void updateAdditionalEmail(AdditionalEmail additionalEmail) {
        // if (!entityAdditionalEmailRepository.existsById(additionalEmail.getId())) {
        //     logger.error("Email not found with ID: {}", additionalEmail.getId());
        //     throw new EntityNotFoundException("Email not found with ID: " + additionalEmail.getId());
        // }
        logger.info("Updating additional email: {}", additionalEmail);
        entityAdditionalEmailRepository.save(additionalEmail);
    }

    @Override
    public void deleteAdditionalEmail(Id id) {
        if (!entityAdditionalEmailRepository.existsById(id)) {
            logger.error("Email not found with ID: {}", id);
            throw new EntityNotFoundException("Email not found with ID: " + id);
        }
        logger.info("Deleting additional email with ID: {}", id);
        entityAdditionalEmailRepository.delete(id);
    }
}
