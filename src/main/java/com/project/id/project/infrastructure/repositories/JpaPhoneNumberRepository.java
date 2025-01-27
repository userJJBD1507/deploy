package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.phones.entities.PhoneNumber;
import com.project.id.project.core.repositories.EntityRepositories.EntityPhoneNumberRepository;
import com.project.id.project.core.repositories.PhoneNumberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaPhoneNumberRepository implements PhoneNumberRepository {
//
//    @Autowired
//    private EntityPhoneNumberRepository entityPhoneNumberRepository;
//
//    @Override
//    public void addPhoneNumber(PhoneNumber phoneNumber) {
//        entityPhoneNumberRepository.save(phoneNumber);
//    }
//
//    @Override
//    public PhoneNumber getPhoneNumber(Id id) {
//        return entityPhoneNumberRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Phone number not found with ID: " + id));
//    }
//
//    @Override
//    public void updatePhoneNumber(PhoneNumber phoneNumber) {
//        if (!entityPhoneNumberRepository.existsById(phoneNumber.getId())) {
//            throw new EntityNotFoundException("Phone number not found with ID: " + phoneNumber.getId());
//        }
//        entityPhoneNumberRepository.save(phoneNumber);
//    }
//
//    @Override
//    public void deletePhoneNumber(Id id) {
//        if (!entityPhoneNumberRepository.existsById(id)) {
//            throw new EntityNotFoundException("Phone number not found with ID: " + id);
//        }
//        entityPhoneNumberRepository.delete(id);
//    }
//}
//



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaPhoneNumberRepository implements PhoneNumberRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaPhoneNumberRepository.class);

    @Autowired
    private EntityPhoneNumberRepository entityPhoneNumberRepository;

    @Override
    public void addPhoneNumber(PhoneNumber phoneNumber) {
        logger.info("Adding phone number: {}", phoneNumber);
        entityPhoneNumberRepository.save(phoneNumber);
    }

    @Override
    public PhoneNumber getPhoneNumber(Id id) {
        logger.info("Fetching phone number with ID: {}", id);
        return entityPhoneNumberRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Phone number not found with ID: {}", id);
                    return new EntityNotFoundException("Phone number not found with ID: " + id);
                });
    }

    @Override
    public void updatePhoneNumber(PhoneNumber phoneNumber) {
        // if (!entityPhoneNumberRepository.existsById(phoneNumber.getId())) {
        //     logger.error("Phone number not found with ID: {}", phoneNumber.getId());
        //     throw new EntityNotFoundException("Phone number not found with ID: " + phoneNumber.getId());
        // }
        logger.info("Updating phone number: {}", phoneNumber);
        entityPhoneNumberRepository.save(phoneNumber);
    }

    @Override
    public void deletePhoneNumber(Id id) {
        if (!entityPhoneNumberRepository.existsById(id)) {
            logger.error("Phone number not found with ID: {}", id);
            throw new EntityNotFoundException("Phone number not found with ID: " + id);
        }
        logger.info("Deleting phone number with ID: {}", id);
        entityPhoneNumberRepository.delete(id);
    }
}
