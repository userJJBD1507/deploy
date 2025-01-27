package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.InsuranceNumberOfIndividualPersonalAccount;
import com.project.id.project.core.repositories.EntityRepositories.EntityInsuranceNumberOfIndividualPersonalAccountRepository;
import com.project.id.project.core.repositories.InsuranceNumberOfIndividualPersonalAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaInsuranceNumberOfIndividualPersonalAccountRepository implements
//        InsuranceNumberOfIndividualPersonalAccountRepository {
//
//    @Autowired
//    private EntityInsuranceNumberOfIndividualPersonalAccountRepository entityInsuranceNumberOfIndividualPersonalAccountRepository;
//
//    @Override
//    public void addInsuranceNumberOfIndividualPersonalAccount(InsuranceNumberOfIndividualPersonalAccount insuranceNumber) {
//        entityInsuranceNumberOfIndividualPersonalAccountRepository.save(insuranceNumber);
//    }
//
//    @Override
//    public InsuranceNumberOfIndividualPersonalAccount getInsuranceNumberOfIndividualPersonalAccount(Id id) {
//        return entityInsuranceNumberOfIndividualPersonalAccountRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Insurance number not found with ID: " + id));
//    }
//
//    @Override
//    public void updateInsuranceNumberOfIndividualPersonalAccount(InsuranceNumberOfIndividualPersonalAccount insuranceNumber) {
//        if (!entityInsuranceNumberOfIndividualPersonalAccountRepository.existsById(insuranceNumber.getId())) {
//            throw new EntityNotFoundException("Insurance number not found with ID: " + insuranceNumber.getId());
//        }
//        entityInsuranceNumberOfIndividualPersonalAccountRepository.save(insuranceNumber);
//    }
//
//    @Override
//    public void deleteInsuranceNumberOfIndividualPersonalAccount(Id id) {
//        if (!entityInsuranceNumberOfIndividualPersonalAccountRepository.existsById(id)) {
//            throw new EntityNotFoundException("Insurance number not found with ID: " + id);
//        }
//        entityInsuranceNumberOfIndividualPersonalAccountRepository.delete(id);
//    }
//}


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaInsuranceNumberOfIndividualPersonalAccountRepository implements
        InsuranceNumberOfIndividualPersonalAccountRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaInsuranceNumberOfIndividualPersonalAccountRepository.class);

    @Autowired
    private EntityInsuranceNumberOfIndividualPersonalAccountRepository entityInsuranceNumberOfIndividualPersonalAccountRepository;

    @Override
    public void addInsuranceNumberOfIndividualPersonalAccount(InsuranceNumberOfIndividualPersonalAccount insuranceNumber) {
        logger.info("Adding insurance number: {}", insuranceNumber);
        entityInsuranceNumberOfIndividualPersonalAccountRepository.save(insuranceNumber);
    }

    @Override
    public InsuranceNumberOfIndividualPersonalAccount getInsuranceNumberOfIndividualPersonalAccount(Id id) {
        logger.info("Fetching insurance number with ID: {}", id);
        return entityInsuranceNumberOfIndividualPersonalAccountRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Insurance number not found with ID: {}", id);
                    return new EntityNotFoundException("Insurance number not found with ID: " + id);
                });
    }

    @Override
    public void updateInsuranceNumberOfIndividualPersonalAccount(InsuranceNumberOfIndividualPersonalAccount insuranceNumber) {
        // if (!entityInsuranceNumberOfIndividualPersonalAccountRepository.existsById(insuranceNumber.getId())) {
        //     logger.error("Insurance number not found with ID: {}", insuranceNumber.getId());
        //     throw new EntityNotFoundException("Insurance number not found with ID: " + insuranceNumber.getId());
        // }
        logger.info("Updating insurance number: {}", insuranceNumber);
        entityInsuranceNumberOfIndividualPersonalAccountRepository.save(insuranceNumber);
    }

    @Override
    public void deleteInsuranceNumberOfIndividualPersonalAccount(Id id) {
        if (!entityInsuranceNumberOfIndividualPersonalAccountRepository.existsById(id)) {
            logger.error("Insurance number not found with ID: {}", id);
            throw new EntityNotFoundException("Insurance number not found with ID: " + id);
        }
        logger.info("Deleting insurance number with ID: {}", id);
        entityInsuranceNumberOfIndividualPersonalAccountRepository.delete(id);
    }
}
