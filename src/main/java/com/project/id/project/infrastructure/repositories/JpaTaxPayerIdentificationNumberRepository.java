package com.project.id.project.infrastructure.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.TaxPayerIdentificationNumber;
import com.project.id.project.core.repositories.EntityRepositories.EntityTaxPayerIdentificationNumberRepository;
import com.project.id.project.core.repositories.TaxPayerIdentificationNumberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
//public class JpaTaxPayerIdentificationNumberRepository implements TaxPayerIdentificationNumberRepository {
//
//    @Autowired
//    private EntityTaxPayerIdentificationNumberRepository entityTaxPayerIdentificationNumberRepository;
//
//    @Override
//    public void addTaxPayerIdentificationNumber(TaxPayerIdentificationNumber taxPayerIdentificationNumber) {
//        entityTaxPayerIdentificationNumberRepository.save(taxPayerIdentificationNumber);
//    }
//
//    @Override
//    public TaxPayerIdentificationNumber getTaxPayerIdentificationNumber(Id id) {
//        return entityTaxPayerIdentificationNumberRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("TaxPayerIdentificationNumber not found with ID: " + id));
//    }
//
//    @Override
//    public void updateTaxPayerIdentificationNumber(TaxPayerIdentificationNumber taxPayerIdentificationNumber) {
//        if (!entityTaxPayerIdentificationNumberRepository.existsById(taxPayerIdentificationNumber.getId())) {
//            throw new EntityNotFoundException("TaxPayerIdentificationNumber not found with ID: " + taxPayerIdentificationNumber.getId());
//        }
//        entityTaxPayerIdentificationNumberRepository.save(taxPayerIdentificationNumber);
//    }
//
//    @Override
//    public void deleteTaxPayerIdentificationNumber(Id id) {
//        if (!entityTaxPayerIdentificationNumberRepository.existsById(id)) {
//            throw new EntityNotFoundException("TaxPayerIdentificationNumber not found with ID: " + id);
//        }
//        entityTaxPayerIdentificationNumberRepository.delete(id);
//    }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JpaTaxPayerIdentificationNumberRepository implements TaxPayerIdentificationNumberRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaTaxPayerIdentificationNumberRepository.class);

    @Autowired
    private EntityTaxPayerIdentificationNumberRepository entityTaxPayerIdentificationNumberRepository;

    @Override
    public void addTaxPayerIdentificationNumber(TaxPayerIdentificationNumber taxPayerIdentificationNumber) {
        logger.info("Adding TaxPayerIdentificationNumber: {}", taxPayerIdentificationNumber);
        entityTaxPayerIdentificationNumberRepository.save(taxPayerIdentificationNumber);
    }

    @Override
    public TaxPayerIdentificationNumber getTaxPayerIdentificationNumber(Id id) {
        logger.info("Fetching TaxPayerIdentificationNumber with ID: {}", id);
        return entityTaxPayerIdentificationNumberRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("TaxPayerIdentificationNumber not found with ID: {}", id);
                    return new EntityNotFoundException("TaxPayerIdentificationNumber not found with ID: " + id);
                });
    }

    @Override
    public void updateTaxPayerIdentificationNumber(TaxPayerIdentificationNumber taxPayerIdentificationNumber) {
        // if (!entityTaxPayerIdentificationNumberRepository.existsById(taxPayerIdentificationNumber.getId())) {
        //     logger.error("TaxPayerIdentificationNumber not found with ID: {}", taxPayerIdentificationNumber.getId());
        //     throw new EntityNotFoundException("TaxPayerIdentificationNumber not found with ID: " + taxPayerIdentificationNumber.getId());
        // }
        logger.info("Updating TaxPayerIdentificationNumber: {}", taxPayerIdentificationNumber);
        entityTaxPayerIdentificationNumberRepository.save(taxPayerIdentificationNumber);
    }

    @Override
    public void deleteTaxPayerIdentificationNumber(Id id) {
        if (!entityTaxPayerIdentificationNumberRepository.existsById(id)) {
            logger.error("TaxPayerIdentificationNumber not found with ID: {}", id);
            throw new EntityNotFoundException("TaxPayerIdentificationNumber not found with ID: " + id);
        }
        logger.info("Deleting TaxPayerIdentificationNumber with ID: {}", id);
        entityTaxPayerIdentificationNumberRepository.delete(id);
    }
}
