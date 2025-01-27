package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.DrivingLicense;
import com.project.id.project.core.repositories.DrivingLicenseRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityDrivingLicenseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaDrivingLicenseRepository implements DrivingLicenseRepository {
//
//    @Autowired
//    private EntityDrivingLicenseRepository entityDrivingLicenseRepository;
//
//    @Override
//    public void addDrivingLicense(DrivingLicense drivingLicense) {
//        entityDrivingLicenseRepository.save(drivingLicense);
//    }
//
//    @Override
//    public DrivingLicense getDrivingLicense(Id id) {
//        return entityDrivingLicenseRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Driving license not found with ID: " + id));
//    }
//
//    @Override
//    public void updateDrivingLicense(DrivingLicense drivingLicense) {
//        if (!entityDrivingLicenseRepository.existsById(drivingLicense.getId())) {
//            throw new EntityNotFoundException("Driving license not found with ID: " + drivingLicense.getId());
//        }
//        entityDrivingLicenseRepository.save(drivingLicense);
//    }
//
//    @Override
//    public void deleteDrivingLicense(Id id) {
//        if (!entityDrivingLicenseRepository.existsById(id)) {
//            throw new EntityNotFoundException("Driving license not found with ID: " + id);
//        }
//        entityDrivingLicenseRepository.delete(id);
//    }
//}


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaDrivingLicenseRepository implements DrivingLicenseRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaDrivingLicenseRepository.class);

    @Autowired
    private EntityDrivingLicenseRepository entityDrivingLicenseRepository;

    @Override
    public void addDrivingLicense(DrivingLicense drivingLicense) {
        logger.info("Adding driving license: {}", drivingLicense);
        entityDrivingLicenseRepository.save(drivingLicense);
    }

    @Override
    public DrivingLicense getDrivingLicense(Id id) {
        logger.info("Fetching driving license with ID: {}", id);
        return entityDrivingLicenseRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Driving license not found with ID: {}", id);
                    return new EntityNotFoundException("Driving license not found with ID: " + id);
                });
    }

    @Override
    public void updateDrivingLicense(DrivingLicense drivingLicense) {
        // if (!entityDrivingLicenseRepository.existsById(drivingLicense.getId())) {
        //     logger.error("Driving license not found with ID: {}", drivingLicense.getId());
        //     throw new EntityNotFoundException("Driving license not found with ID: " + drivingLicense.getId());
        // }
        logger.info("Updating driving license: {}", drivingLicense);
        entityDrivingLicenseRepository.save(drivingLicense);
    }

    @Override
    public void deleteDrivingLicense(Id id) {
        if (!entityDrivingLicenseRepository.existsById(id)) {
            logger.error("Driving license not found with ID: {}", id);
            throw new EntityNotFoundException("Driving license not found with ID: " + id);
        }
        logger.info("Deleting driving license with ID: {}", id);
        entityDrivingLicenseRepository.delete(id);
    }
}
