package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.BirthCertificate;
import com.project.id.project.core.repositories.BirthCertificateRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityBirthCertificateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaBirthCertificateRepository implements BirthCertificateRepository {
//
//    @Autowired
//    private EntityBirthCertificateRepository entityBirthCertificateRepository;
//
//    @Override
//    public void addBirthCertificate(BirthCertificate birthCertificate) {
//        entityBirthCertificateRepository.save(birthCertificate);
//    }
//
//    @Override
//    public BirthCertificate getBirthCertificate(Id id) {
//        return entityBirthCertificateRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Birth certificate not found with ID: " + id));
//    }
//
//    @Override
//    public void updateBirthCertificate(BirthCertificate birthCertificate) {
//        if (!entityBirthCertificateRepository.existsById(birthCertificate.getId())) {
//            throw new EntityNotFoundException("Birth certificate not found with ID: " + birthCertificate.getId());
//        }
//        entityBirthCertificateRepository.save(birthCertificate);
//    }
//
//    @Override
//    public void deleteBirthCertificate(Id id) {
//        if (!entityBirthCertificateRepository.existsById(id)) {
//            throw new EntityNotFoundException("Birth certificate not found with ID: " + id);
//        }
//        entityBirthCertificateRepository.delete(id);
//    }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaBirthCertificateRepository implements BirthCertificateRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaBirthCertificateRepository.class);

    @Autowired
    private EntityBirthCertificateRepository entityBirthCertificateRepository;

    @Override
    public void addBirthCertificate(BirthCertificate birthCertificate) {
        logger.info("Adding birth certificate: {}", birthCertificate);
        entityBirthCertificateRepository.save(birthCertificate);
    }

    @Override
    public BirthCertificate getBirthCertificate(Id id) {
        logger.info("Fetching birth certificate with ID: {}", id);
        return entityBirthCertificateRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Birth certificate not found with ID: {}", id);
                    return new EntityNotFoundException("Birth certificate not found with ID: " + id);
                });
    }

    @Override
    public void updateBirthCertificate(BirthCertificate birthCertificate) {
        // if (!entityBirthCertificateRepository.existsById(birthCertificate.getId())) {
        //     logger.error("Birth certificate not found with ID: {}", birthCertificate.getId());
        //     throw new EntityNotFoundException("Birth certificate not found with ID: " + birthCertificate.getId());
        // }
        logger.info("Updating birth certificate: {}", birthCertificate);
        entityBirthCertificateRepository.save(birthCertificate);
    }

    @Override
    public void deleteBirthCertificate(Id id) {
        if (!entityBirthCertificateRepository.existsById(id)) {
            logger.error("Birth certificate not found with ID: {}", id);
            throw new EntityNotFoundException("Birth certificate not found with ID: " + id);
        }
        logger.info("Deleting birth certificate with ID: {}", id);
        entityBirthCertificateRepository.delete(id);
    }
}
