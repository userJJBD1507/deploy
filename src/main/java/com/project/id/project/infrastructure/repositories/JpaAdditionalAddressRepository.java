package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.repositories.AdditionalAddressRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityAdditionalAddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaAdditionalAddressRepository implements AdditionalAddressRepository {
//    @Autowired
//    private EntityAdditionalAddressRepository entityAdditionalAddressRepository;
//    @Override
//    public void addAdditionalAddress(AdditionalAddress additionalAddress) {
//        entityAdditionalAddressRepository.save(additionalAddress);
//    }
//
//    @Override
//    public AdditionalAddress getAdditionalAddress(Id id) {
//        return entityAdditionalAddressRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
//    }
//
//    @Override
//    public void updateAdditionalAddress(AdditionalAddress additionalAddress) {
//        if (!entityAdditionalAddressRepository.existsById(additionalAddress.getId())) {
//            throw new EntityNotFoundException("Address not found");
//        }
//        entityAdditionalAddressRepository.save(additionalAddress);
//    }
//
//    @Override
//    public void deleteAdditionalAddress(Id id) {
//        if (!entityAdditionalAddressRepository.existsById(id)) {
//            throw new EntityNotFoundException("Address not found");
//        }
//        entityAdditionalAddressRepository.delete(id);
//    }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaAdditionalAddressRepository implements AdditionalAddressRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaAdditionalAddressRepository.class);

    @Autowired
    private EntityAdditionalAddressRepository entityAdditionalAddressRepository;

    @Override
    public void addAdditionalAddress(AdditionalAddress additionalAddress) {
        logger.info("Adding additional address: {}", additionalAddress);
        entityAdditionalAddressRepository.save(additionalAddress);
    }

    @Override
    public AdditionalAddress getAdditionalAddress(Id id) {
        logger.info("Fetching additional address with ID: {}", id);
        return entityAdditionalAddressRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Address not found with ID: {}", id);
                    return new EntityNotFoundException("Address not found");
                });
    }

    @Override
    public void updateAdditionalAddress(AdditionalAddress additionalAddress) {
        // if (!entityAdditionalAddressRepository.existsById(additionalAddress.getId())) {
        //     logger.error("Address not found with ID: {}", additionalAddress.getId());
        //     throw new EntityNotFoundException("Address not found");
        // }
        logger.info("Updating additional address: {}", additionalAddress);
        entityAdditionalAddressRepository.save(additionalAddress);
    }

    @Override
    public void deleteAdditionalAddress(Id id) {
        if (!entityAdditionalAddressRepository.existsById(id)) {
            logger.error("Address not found with ID: {}", id);
            throw new EntityNotFoundException("Address not found");
        }
        logger.info("Deleting additional address with ID: {}", id);
        entityAdditionalAddressRepository.delete(id);
    }
}
