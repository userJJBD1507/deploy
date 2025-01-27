package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.Passport;
import com.project.id.project.core.repositories.EntityRepositories.EntityPassportRepository;
import com.project.id.project.core.repositories.PassportRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaPassportRepository implements PassportRepository {
//
//    @Autowired
//    private EntityPassportRepository entityPassportRepository;
//
//    @Override
//    public void addPassport(Passport passport) {
//        entityPassportRepository.save(passport);
//    }
//
//    @Override
//    public Passport getPassport(Id id) {
//        return entityPassportRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Passport not found with ID: " + id));
//    }
//
//    @Override
//    public void updatePassport(Passport passport) {
//        if (!entityPassportRepository.existsById(passport.getId())) {
//            throw new EntityNotFoundException("Passport not found with ID: " + passport.getId());
//        }
//        entityPassportRepository.save(passport);
//    }
//
//    @Override
//    public void deletePassport(Id id) {
//        if (!entityPassportRepository.existsById(id)) {
//            throw new EntityNotFoundException("Passport not found with ID: " + id);
//        }
//        entityPassportRepository.delete(id);
//    }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaPassportRepository implements PassportRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaPassportRepository.class);

    @Autowired
    private EntityPassportRepository entityPassportRepository;

    @Override
    public void addPassport(Passport passport) {
        logger.info("Adding passport: {}", passport);
        entityPassportRepository.save(passport);
    }

    @Override
    public Passport getPassport(Id id) {
        logger.info("Fetching passport with ID: {}", id);
        return entityPassportRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Passport not found with ID: {}", id);
                    return new EntityNotFoundException("Passport not found with ID: " + id);
                });
    }

    @Override
    public void updatePassport(Passport passport) {
        // if (!entityPassportRepository.existsById(passport.getId())) {
        //     logger.error("Passport not found with ID: {}", passport.getId());
        //     throw new EntityNotFoundException("Passport not found with ID: " + passport.getId());
        // }
        logger.info("Updating passport: {}", passport);
        entityPassportRepository.save(passport);
    }

    @Override
    public void deletePassport(Id id) {
        if (!entityPassportRepository.existsById(id)) {
            logger.error("Passport not found with ID: {}", id);
            throw new EntityNotFoundException("Passport not found with ID: " + id);
        }
        logger.info("Deleting passport with ID: {}", id);
        entityPassportRepository.delete(id);
    }
}
