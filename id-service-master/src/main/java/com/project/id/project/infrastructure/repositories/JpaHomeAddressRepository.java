package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.HomeAddress;
import com.project.id.project.core.repositories.EntityRepositories.EntityHomeAddressRepository;
import com.project.id.project.core.repositories.HomeAddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaHomeAddressRepository implements HomeAddressRepository {
//
//    @Autowired
//    private EntityHomeAddressRepository entityHomeAddressRepository;
//
//    @Override
//    public void addHomeAddress(HomeAddress homeAddress) {
//        entityHomeAddressRepository.save(homeAddress);
//    }
//
//    @Override
//    public HomeAddress getHomeAddress(Id id) {
//        return entityHomeAddressRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Home address not found with ID: " + id));
//    }
//
//    @Override
//    public void updateHomeAddress(HomeAddress homeAddress) {
//        if (!entityHomeAddressRepository.existsById(homeAddress.getId())) {
//            throw new EntityNotFoundException("Home address not found with ID: " + homeAddress.getId());
//        }
//        entityHomeAddressRepository.save(homeAddress);
//    }
//
//    @Override
//    public void deleteHomeAddress(Id id) {
//        if (!entityHomeAddressRepository.existsById(id)) {
//            throw new EntityNotFoundException("Home address not found with ID: " + id);
//        }
//        entityHomeAddressRepository.delete(id);
//    }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaHomeAddressRepository implements HomeAddressRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaHomeAddressRepository.class);

    @Autowired
    private EntityHomeAddressRepository entityHomeAddressRepository;

    @Override
    public void addHomeAddress(HomeAddress homeAddress) {
        logger.info("Adding home address: {}", homeAddress);
        entityHomeAddressRepository.save(homeAddress);
    }

    @Override
    public HomeAddress getHomeAddress(Id id) {
        logger.info("Fetching home address with ID: {}", id);
        return entityHomeAddressRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Home address not found with ID: {}", id);
                    return new EntityNotFoundException("Home address not found with ID: " + id);
                });
    }

    @Override
    public void updateHomeAddress(HomeAddress homeAddress) {
        // if (!entityHomeAddressRepository.existsById(homeAddress.getId())) {
        //     logger.error("Home address not found with ID: {}", homeAddress.getId());
        //     throw new EntityNotFoundException("Home address not found with ID: " + homeAddress.getId());
        // }
        logger.info("Updating home address: {}", homeAddress);
        System.out.println("22222222222222222222222222222");
        entityHomeAddressRepository.save(homeAddress);
    }

    @Override
    public void deleteHomeAddress(Id id) {
        if (!entityHomeAddressRepository.existsById(id)) {
            logger.error("Home address not found with ID: {}", id);
            throw new EntityNotFoundException("Home address not found with ID: " + id);
        }
        logger.info("Deleting home address with ID: {}", id);
        entityHomeAddressRepository.delete(id);
    }
}
