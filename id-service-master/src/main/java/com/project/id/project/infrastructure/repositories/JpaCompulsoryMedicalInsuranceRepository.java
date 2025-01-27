package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.CompulsoryMedicalInsurance;
import com.project.id.project.core.repositories.CompulsoryMedicalInsuranceRepository;
import com.project.id.project.core.repositories.EntityRepositories.EntityCompulsoryMedicalInsuranceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaCompulsoryMedicalInsuranceRepository implements CompulsoryMedicalInsuranceRepository {
//
//    @Autowired
//    private EntityCompulsoryMedicalInsuranceRepository entityCompulsoryMedicalInsuranceRepository;
//
//    @Override
//    public void addCompulsoryMedicalInsurance(CompulsoryMedicalInsurance compulsoryMedicalInsurance) {
//        entityCompulsoryMedicalInsuranceRepository.save(compulsoryMedicalInsurance);
//    }
//
//    @Override
//    public CompulsoryMedicalInsurance getCompulsoryMedicalInsurance(Id id) {
//        return entityCompulsoryMedicalInsuranceRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Compulsory medical insurance not found with ID: " + id));
//    }
//
//    @Override
//    public void updateCompulsoryMedicalInsurance(CompulsoryMedicalInsurance compulsoryMedicalInsurance) {
//        if (!entityCompulsoryMedicalInsuranceRepository.existsById(compulsoryMedicalInsurance.getId())) {
//            throw new EntityNotFoundException("Compulsory medical insurance not found with ID: " + compulsoryMedicalInsurance.getId());
//        }
//        entityCompulsoryMedicalInsuranceRepository.save(compulsoryMedicalInsurance);
//    }
//
//    @Override
//    public void deleteCompulsoryMedicalInsurance(Id id) {
//        if (!entityCompulsoryMedicalInsuranceRepository.existsById(id)) {
//            throw new EntityNotFoundException("Compulsory medical insurance not found with ID: " + id);
//        }
//        entityCompulsoryMedicalInsuranceRepository.delete(id);
//    }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaCompulsoryMedicalInsuranceRepository implements CompulsoryMedicalInsuranceRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaCompulsoryMedicalInsuranceRepository.class);

    @Autowired
    private EntityCompulsoryMedicalInsuranceRepository entityCompulsoryMedicalInsuranceRepository;

    @Override
    public void addCompulsoryMedicalInsurance(CompulsoryMedicalInsurance compulsoryMedicalInsurance) {
        logger.info("Adding compulsory medical insurance: {}", compulsoryMedicalInsurance);
        entityCompulsoryMedicalInsuranceRepository.save(compulsoryMedicalInsurance);
    }

    @Override
    public CompulsoryMedicalInsurance getCompulsoryMedicalInsurance(Id id) {
        logger.info("Fetching compulsory medical insurance with ID: {}", id);
        return entityCompulsoryMedicalInsuranceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Compulsory medical insurance not found with ID: {}", id);
                    return new EntityNotFoundException("Compulsory medical insurance not found with ID: " + id);
                });
    }

    @Override
    public void updateCompulsoryMedicalInsurance(CompulsoryMedicalInsurance compulsoryMedicalInsurance) {
        // if (!entityCompulsoryMedicalInsuranceRepository.existsById(compulsoryMedicalInsurance.getId())) {
        //     logger.error("Compulsory medical insurance not found with ID: {}", compulsoryMedicalInsurance.getId());
        //     throw new EntityNotFoundException("Compulsory medical insurance not found with ID: " + compulsoryMedicalInsurance.getId());
        // }
        logger.info("Updating compulsory medical insurance: {}", compulsoryMedicalInsurance);
        entityCompulsoryMedicalInsuranceRepository.save(compulsoryMedicalInsurance);
    }

    @Override
    public void deleteCompulsoryMedicalInsurance(Id id) {
        if (!entityCompulsoryMedicalInsuranceRepository.existsById(id)) {
            logger.error("Compulsory medical insurance not found with ID: {}", id);
            throw new EntityNotFoundException("Compulsory medical insurance not found with ID: " + id);
        }
        logger.info("Deleting compulsory medical insurance with ID: {}", id);
        entityCompulsoryMedicalInsuranceRepository.delete(id);
    }
}
