package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.VoluntaryHealthInsurance;
import com.project.id.project.core.repositories.EntityRepositories.EntityVoluntaryHealthInsuranceRepository;
import com.project.id.project.core.repositories.VoluntaryHealthInsuranceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaVoluntaryHealthInsuranceRepository implements VoluntaryHealthInsuranceRepository {
//
//    @Autowired
//    private EntityVoluntaryHealthInsuranceRepository entityVoluntaryHealthInsuranceRepository;
//
//    @Override
//    public void addVoluntaryHealthInsurance(VoluntaryHealthInsurance voluntaryHealthInsurance) {
//        entityVoluntaryHealthInsuranceRepository.save(voluntaryHealthInsurance);
//    }
//
//    @Override
//    public VoluntaryHealthInsurance getVoluntaryHealthInsurance(Id id) {
//        return entityVoluntaryHealthInsuranceRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("VoluntaryHealthInsurance not found with ID: " + id));
//    }
//
//    @Override
//    public void updateVoluntaryHealthInsurance(VoluntaryHealthInsurance voluntaryHealthInsurance) {
//        if (!entityVoluntaryHealthInsuranceRepository.existsById(voluntaryHealthInsurance.getId())) {
//            throw new EntityNotFoundException("VoluntaryHealthInsurance not found with ID: " + voluntaryHealthInsurance.getId());
//        }
//        entityVoluntaryHealthInsuranceRepository.save(voluntaryHealthInsurance);
//    }
//
//    @Override
//    public void deleteVoluntaryHealthInsurance(Id id) {
//        if (!entityVoluntaryHealthInsuranceRepository.existsById(id)) {
//            throw new EntityNotFoundException("VoluntaryHealthInsurance not found with ID: " + id);
//        }
//        entityVoluntaryHealthInsuranceRepository.delete(id);
//    }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaVoluntaryHealthInsuranceRepository implements VoluntaryHealthInsuranceRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaVoluntaryHealthInsuranceRepository.class);

    @Autowired
    private EntityVoluntaryHealthInsuranceRepository entityVoluntaryHealthInsuranceRepository;

    @Override
    public void addVoluntaryHealthInsurance(VoluntaryHealthInsurance voluntaryHealthInsurance) {
        logger.info("Adding VoluntaryHealthInsurance: {}", voluntaryHealthInsurance);
        entityVoluntaryHealthInsuranceRepository.save(voluntaryHealthInsurance);
    }

    @Override
    public VoluntaryHealthInsurance getVoluntaryHealthInsurance(Id id) {
        logger.info("Fetching VoluntaryHealthInsurance with ID: {}", id);
        return entityVoluntaryHealthInsuranceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("VoluntaryHealthInsurance not found with ID: {}", id);
                    return new EntityNotFoundException("VoluntaryHealthInsurance not found with ID: " + id);
                });
    }

    @Override
    public void updateVoluntaryHealthInsurance(VoluntaryHealthInsurance voluntaryHealthInsurance) {
        // if (!entityVoluntaryHealthInsuranceRepository.existsById(voluntaryHealthInsurance.getId())) {
        //     logger.error("VoluntaryHealthInsurance not found with ID: {}", voluntaryHealthInsurance.getId());
        //     throw new EntityNotFoundException("VoluntaryHealthInsurance not found with ID: " + voluntaryHealthInsurance.getId());
        // }
        logger.info("Updating VoluntaryHealthInsurance: {}", voluntaryHealthInsurance);
        entityVoluntaryHealthInsuranceRepository.save(voluntaryHealthInsurance);
    }

    @Override
    public void deleteVoluntaryHealthInsurance(Id id) {
        if (!entityVoluntaryHealthInsuranceRepository.existsById(id)) {
            logger.error("VoluntaryHealthInsurance not found with ID: {}", id);
            throw new EntityNotFoundException("VoluntaryHealthInsurance not found with ID: " + id);
        }
        logger.info("Deleting VoluntaryHealthInsurance with ID: {}", id);
        entityVoluntaryHealthInsuranceRepository.delete(id);
    }
}
