package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.phones.entities.AdditionalPhoneNumber;
import com.project.id.project.core.repositories.AdditionalPhoneNumberRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalPhoneNumberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaAdditionalPhoneNumberRepository implements AdditionalPhoneNumberRepository {
//
//    @Autowired
//    private EntityAdditionalPhoneNumberRepository entityAdditionalPhoneNumberRepository;
//
//    @Override
//    public void addAdditionalPhoneNumber(AdditionalPhoneNumber additionalPhoneNumber) {
//        entityAdditionalPhoneNumberRepository.save(additionalPhoneNumber);
//    }
//
//    @Override
//    public AdditionalPhoneNumber getAdditionalPhoneNumber(Id id) {
//        return entityAdditionalPhoneNumberRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Phone number not found with ID: " + id));
//    }
//
//    @Override
//    public void updateAdditionalPhoneNumber(AdditionalPhoneNumber additionalPhoneNumber) {
//        if (!entityAdditionalPhoneNumberRepository.existsById(additionalPhoneNumber.getId())) {
//            throw new EntityNotFoundException("Phone number not found with ID: " + additionalPhoneNumber.getId());
//        }
//        entityAdditionalPhoneNumberRepository.save(additionalPhoneNumber);
//    }
//
//    @Override
//    public void deleteAdditionalPhoneNumber(Id id) {
//        if (!entityAdditionalPhoneNumberRepository.existsById(id)) {
//            throw new EntityNotFoundException("Phone number not found with ID: " + id);
//        }
//        entityAdditionalPhoneNumberRepository.delete(id);
//    }
//}


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaAdditionalPhoneNumberRepository implements AdditionalPhoneNumberRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaAdditionalPhoneNumberRepository.class);

    @Autowired
    private EntityAdditionalPhoneNumberRepository entityAdditionalPhoneNumberRepository;

    @Override
    public void addAdditionalPhoneNumber(AdditionalPhoneNumber additionalPhoneNumber) {
        logger.info("Adding additional phone number: {}", additionalPhoneNumber);
        entityAdditionalPhoneNumberRepository.save(additionalPhoneNumber);
    }

    @Override
    public AdditionalPhoneNumber getAdditionalPhoneNumber(Id id) {
        logger.info("Fetching additional phone number with ID: {}", id);
        return entityAdditionalPhoneNumberRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Phone number not found with ID: {}", id);
                    return new EntityNotFoundException("Phone number not found with ID: " + id);
                });
    }

    @Override
    public void updateAdditionalPhoneNumber(AdditionalPhoneNumber additionalPhoneNumber) {
        // if (!entityAdditionalPhoneNumberRepository.existsById(additionalPhoneNumber.getId())) {
        //     logger.error("Phone number not found with ID: {}", additionalPhoneNumber.getId());
        //     throw new EntityNotFoundException("Phone number not found with ID: " + additionalPhoneNumber.getId());
        // }
        logger.info("Updating additional phone number: {}", additionalPhoneNumber);
        entityAdditionalPhoneNumberRepository.save(additionalPhoneNumber);
    }

    @Override
    public void deleteAdditionalPhoneNumber(Id id) {
        if (!entityAdditionalPhoneNumberRepository.existsById(id)) {
            logger.error("Phone number not found with ID: {}", id);
            throw new EntityNotFoundException("Phone number not found with ID: " + id);
        }
        logger.info("Deleting additional phone number with ID: {}", id);
        entityAdditionalPhoneNumberRepository.delete(id);
    }
}
