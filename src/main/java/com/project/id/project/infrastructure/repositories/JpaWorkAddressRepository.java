package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.WorkAddress;
import com.project.id.project.core.repositories.EntityRepositories.EntityWorkAddressRepository;
import com.project.id.project.core.repositories.WorkAddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaWorkAddressRepository implements WorkAddressRepository {
//
//    @Autowired
//    private EntityWorkAddressRepository entityWorkAddressRepository;
//
//    @Override
//    public void addWorkAddress(WorkAddress workAddress) {
//        entityWorkAddressRepository.save(workAddress);
//    }
//
//    @Override
//    public WorkAddress getWorkAddress(Id id) {
//        return entityWorkAddressRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("WorkAddress not found with ID: " + id));
//    }
//
//    @Override
//    public void updateWorkAddress(WorkAddress workAddress) {
//        if (!entityWorkAddressRepository.existsById(workAddress.getId())) {
//            throw new EntityNotFoundException("WorkAddress not found with ID: " + workAddress.getId());
//        }
//        entityWorkAddressRepository.save(workAddress);
//    }
//
//    @Override
//    public void deleteWorkAddress(Id id) {
//        if (!entityWorkAddressRepository.existsById(id)) {
//            throw new EntityNotFoundException("WorkAddress not found with ID: " + id);
//        }
//        entityWorkAddressRepository.delete(id);
//    }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaWorkAddressRepository implements WorkAddressRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaWorkAddressRepository.class);

    @Autowired
    private EntityWorkAddressRepository entityWorkAddressRepository;

    @Override
    public void addWorkAddress(WorkAddress workAddress) {
        logger.info("Adding WorkAddress: {}", workAddress);
        entityWorkAddressRepository.save(workAddress);
    }

    @Override
    public WorkAddress getWorkAddress(Id id) {
        logger.info("Fetching WorkAddress with ID: {}", id);
        return entityWorkAddressRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("WorkAddress not found with ID: {}", id);
                    return new EntityNotFoundException("WorkAddress not found with ID: " + id);
                });
    }

    @Override
    public void updateWorkAddress(WorkAddress workAddress) {
        // if (!entityWorkAddressRepository.existsById(workAddress.getId())) {
        //     logger.error("WorkAddress not found with ID: {}", workAddress.getId());
        //     throw new EntityNotFoundException("WorkAddress not found with ID: " + workAddress.getId());
        // }
        logger.info("Updating WorkAddress: {}", workAddress);
        entityWorkAddressRepository.save(workAddress);
    }

    @Override
    public void deleteWorkAddress(Id id) {
        if (!entityWorkAddressRepository.existsById(id)) {
            logger.error("WorkAddress not found with ID: {}", id);
            throw new EntityNotFoundException("WorkAddress not found with ID: " + id);
        }
        logger.info("Deleting WorkAddress with ID: {}", id);
        entityWorkAddressRepository.delete(id);
    }
}
