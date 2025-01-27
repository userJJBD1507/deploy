package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.ForeignPassport;
import com.project.id.project.core.repositories.EntityRepositories.EntityForeignPassportRepository;
import com.project.id.project.core.repositories.ForeignPassportRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaForeignPassportRepository implements ForeignPassportRepository {
//
//    @Autowired
//    private EntityForeignPassportRepository entityForeignPassportRepository;
//
//    @Override
//    public void addForeignPassport(ForeignPassport foreignPassport) {
//        entityForeignPassportRepository.save(foreignPassport);
//    }
//
//    @Override
//    public ForeignPassport getForeignPassport(Id id) {
//        return entityForeignPassportRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Foreign passport not found with ID: " + id));
//    }
//
//    @Override
//    public void updateForeignPassport(ForeignPassport foreignPassport) {
//        if (!entityForeignPassportRepository.existsById(foreignPassport.getId())) {
//            throw new EntityNotFoundException("Foreign passport not found with ID: " + foreignPassport.getId());
//        }
//        entityForeignPassportRepository.save(foreignPassport);
//    }
//
//    @Override
//    public void deleteForeignPassport(Id id) {
//        if (!entityForeignPassportRepository.existsById(id)) {
//            throw new EntityNotFoundException("Foreign passport not found with ID: " + id);
//        }
//        entityForeignPassportRepository.delete(id);
//    }
//}


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaForeignPassportRepository implements ForeignPassportRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaForeignPassportRepository.class);

    @Autowired
    private EntityForeignPassportRepository entityForeignPassportRepository;

    @Override
    public void addForeignPassport(ForeignPassport foreignPassport) {
        logger.info("Adding foreign passport: {}", foreignPassport);
        entityForeignPassportRepository.save(foreignPassport);
    }

    @Override
    public ForeignPassport getForeignPassport(Id id) {
        logger.info("Fetching foreign passport with ID: {}", id);
        return entityForeignPassportRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Foreign passport not found with ID: {}", id);
                    return new EntityNotFoundException("Foreign passport not found with ID: " + id);
                });
    }

    @Override
    public void updateForeignPassport(ForeignPassport foreignPassport) {
        // if (!entityForeignPassportRepository.existsById(foreignPassport.getId())) {
        //     logger.error("Foreign passport not found with ID: {}", foreignPassport.getId());
        //     throw new EntityNotFoundException("Foreign passport not found with ID: " + foreignPassport.getId());
        // }
        logger.info("Updating foreign passport: {}", foreignPassport);
        entityForeignPassportRepository.save(foreignPassport);
    }

    @Override
    public void deleteForeignPassport(Id id) {
        if (!entityForeignPassportRepository.existsById(id)) {
            logger.error("Foreign passport not found with ID: {}", id);
            throw new EntityNotFoundException("Foreign passport not found with ID: " + id);
        }
        logger.info("Deleting foreign passport with ID: {}", id);
        entityForeignPassportRepository.delete(id);
    }
}
