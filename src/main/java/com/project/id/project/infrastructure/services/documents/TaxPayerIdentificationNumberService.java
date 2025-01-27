package com.project.id.project.infrastructure.services.documents;

import com.project.id.project.application.DTOs.documents.TaxPayerIdentificationNumberDTO;
import com.project.id.project.application.mappers.documents.TaxPayerIdentificationNumberMapper;
import com.project.id.project.application.services.CrudService;
import com.project.id.project.application.services.Security.NewCrudSer;
import com.project.id.project.application.services.linkers.DocumentsRepository;
import com.project.id.project.application.services.pdf.PdfGenerator;
import com.project.id.project.application.services.pdf.handlers.CrudUpdated;
import com.project.id.project.application.services.pdf.handlers.Pdf;
import com.project.id.project.application.services.pdf.handlers.PdfRepository;
import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.BirthCertificate;
import com.project.id.project.core.documents.entities.TaxPayerIdentificationNumber;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaTaxPayerIdentificationNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class TaxPayerIdentificationNumberService implements CrudUpdated<TaxPayerIdentificationNumberDTO, Id> {
//
//    @Autowired
//    private JpaTaxPayerIdentificationNumberRepository taxPayerIdentificationNumberRepository;
//
//
//    @Autowired
//    private PdfGenerator pdfGenerator;
//    @Autowired
//    private StorageService storageService;
//
//    @Autowired
//    private PdfRepository pdfRepository;
//    @Autowired
//    private EntityPersonalDataRepository entityPersonalDataRepository;
//    @Autowired
//    private DocumentsRepository documentsRepository;
//
//    @Override
//    public void create(String username, TaxPayerIdentificationNumberDTO taxPayerIdentificationNumberDTO) {
//        TaxPayerIdentificationNumber entity = TaxPayerIdentificationNumberMapper.toEntity(taxPayerIdentificationNumberDTO);
//        PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).orElseThrow(
//                () -> new IllegalArgumentException("Personal data not found for user: " + username)
//        );
//        Id id = personalData.getDocuments().getId();
//        Documents documents = documentsRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("Documents not found for ID: " + id)
//        );
//        if (documents.getTaxPayerIdentificationNumber() != null) {
//            throw new IllegalStateException("Tax payer identification number already exists for these documents.");
//        }
//        taxPayerIdentificationNumberRepository.addTaxPayerIdentificationNumber(entity);
//        documents.setTaxPayerIdentificationNumber(entity);
//        entity.setDocuments(documents);
//        taxPayerIdentificationNumberRepository.addTaxPayerIdentificationNumber(entity);
//
//
//        try {
//            byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(taxPayerIdentificationNumberDTO);
//            String s3Link = storageService.upload(bytesOfDto);
//            Pdf pdf = new Pdf();
//            pdf.setResourceId(entity.getId().toString());
//            pdf.setS3Link(s3Link);
//            pdfRepository.save(pdf);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public Optional<TaxPayerIdentificationNumberDTO> read(Id id) {
//        TaxPayerIdentificationNumber entity = taxPayerIdentificationNumberRepository.getTaxPayerIdentificationNumber(id);
//        return Optional.of(TaxPayerIdentificationNumberMapper.toDTO(entity));
//    }
//
//    @Override
//    public void update(Id id, TaxPayerIdentificationNumberDTO taxPayerIdentificationNumberDTO, Id documentId) {
//        TaxPayerIdentificationNumber existingEntity = taxPayerIdentificationNumberRepository.getTaxPayerIdentificationNumber(id);
//        TaxPayerIdentificationNumber updatedEntity = TaxPayerIdentificationNumberMapper.toEntity(taxPayerIdentificationNumberDTO);
//        updatedEntity.setId(existingEntity.getId());
//
//        Documents documents = documentsRepository.findById(documentId)
//                .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
//
//        updatedEntity.setDocuments(documents);
//
//        taxPayerIdentificationNumberRepository.updateTaxPayerIdentificationNumber(updatedEntity);
//
//        Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
//        if (pdf.isEmpty()) {
//            return;
//        }
//        String s3Link = pdf.get().getS3Link();
//        storageService.delete(s3Link);
//        pdfRepository.deleteById(pdf.get().getId());
//        try {
//            byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(taxPayerIdentificationNumberDTO);
//            String s3l = storageService.upload(bytesOfDto);
//            Pdf updatedPdf = new Pdf();
//            updatedPdf.setResourceId(updatedEntity.getId().toString());
//            updatedPdf.setS3Link(s3l);
//            pdfRepository.save(updatedPdf);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public void delete(Id id) {
//        Documents byTaxPayerIdentificationNumber = documentsRepository.getByTaxPayerIdentificationNumber(id);
//        byTaxPayerIdentificationNumber.setTaxPayerIdentificationNumber(null);
//        documentsRepository.save(byTaxPayerIdentificationNumber);
//        taxPayerIdentificationNumberRepository.deleteTaxPayerIdentificationNumber(id);
//        Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
//        if (pdf.isEmpty()) {
//            return;
//        }
//        String s3Link = pdf.get().getS3Link();
//        storageService.delete(s3Link);
//        pdfRepository.deleteById(pdf.get().getId());
//    }
//}



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaxPayerIdentificationNumberService implements CrudUpdated<TaxPayerIdentificationNumberDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(TaxPayerIdentificationNumberService.class);

    @Autowired
    private JpaTaxPayerIdentificationNumberRepository taxPayerIdentificationNumberRepository;

    @Autowired
    private PdfGenerator pdfGenerator;
    @Autowired
    private StorageService storageService;

    @Autowired
    private PdfRepository pdfRepository;
    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Autowired
    private DocumentsRepository documentsRepository;

    @Override
    public void create(String username, TaxPayerIdentificationNumberDTO taxPayerIdentificationNumberDTO) {
        logger.info("Starting create method for TaxPayerIdentificationNumber with username: {}", username);

        try {
            TaxPayerIdentificationNumber entity = TaxPayerIdentificationNumberMapper.toEntity(taxPayerIdentificationNumberDTO);
            PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).orElseThrow(
                    () -> new IllegalArgumentException("Personal data not found for user: " + username)
            );

            Id id = personalData.getDocuments().getId();
            Documents documents = documentsRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("Documents not found for ID: " + id)
            );

            if (documents.getTaxPayerIdentificationNumber() != null) {
                throw new IllegalStateException("Tax payer identification number already exists for these documents.");
            }

            taxPayerIdentificationNumberRepository.addTaxPayerIdentificationNumber(entity);
            documents.setTaxPayerIdentificationNumber(entity);
            entity.setDocuments(documents);
            taxPayerIdentificationNumberRepository.addTaxPayerIdentificationNumber(entity);

            try {
                byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(taxPayerIdentificationNumberDTO);
                String s3Link = storageService.upload(bytesOfDto);
                Pdf pdf = new Pdf();
                pdf.setResourceId(entity.getId().toString());
                pdf.setS3Link(s3Link);
                pdfRepository.save(pdf);
            } catch (Exception e) {
                logger.error("Error generating PDF for TaxPayerIdentificationNumber: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error("Error in create method: {}", e.getMessage(), e);
        }

        logger.info("Finished create method for TaxPayerIdentificationNumber with username: {}", username);
    }

    @Override
    public Optional<TaxPayerIdentificationNumberDTO> read(Id id) {
        logger.info("Reading TaxPayerIdentificationNumber with ID: {}", id);
        TaxPayerIdentificationNumber entity = taxPayerIdentificationNumberRepository.getTaxPayerIdentificationNumber(id);
        logger.info("Successfully read TaxPayerIdentificationNumber with ID: {}", id);
        return Optional.of(TaxPayerIdentificationNumberMapper.toDTO(entity));
    }

    // @Override
    // public void update(Id id, TaxPayerIdentificationNumberDTO taxPayerIdentificationNumberDTO, Id documentId) {
    //     logger.info("Starting update method for TaxPayerIdentificationNumber with ID: {}", id);

    //     try {
    //         TaxPayerIdentificationNumber existingEntity = taxPayerIdentificationNumberRepository.getTaxPayerIdentificationNumber(id);
    //         TaxPayerIdentificationNumber updatedEntity = TaxPayerIdentificationNumberMapper.toEntity(taxPayerIdentificationNumberDTO);
    //         updatedEntity.setId(existingEntity.getId());

    //         Documents documents = documentsRepository.findById(documentId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
    //         updatedEntity.setDocuments(documents);

    //         taxPayerIdentificationNumberRepository.updateTaxPayerIdentificationNumber(updatedEntity);

    //         Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
    //         if (pdf.isPresent()) {
    //             String s3Link = pdf.get().getS3Link();
    //             storageService.delete(s3Link);
    //             pdfRepository.deleteById(pdf.get().getId());
    //         }

    //         try {
    //             byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(taxPayerIdentificationNumberDTO);
    //             String s3l = storageService.upload(bytesOfDto);
    //             Pdf updatedPdf = new Pdf();
    //             updatedPdf.setResourceId(updatedEntity.getId().toString());
    //             updatedPdf.setS3Link(s3l);
    //             pdfRepository.save(updatedPdf);
    //         } catch (Exception e) {
    //             logger.error("Error generating PDF during update: {}", e.getMessage(), e);
    //         }
    //     } catch (Exception e) {
    //         logger.error("Error in update method: {}", e.getMessage(), e);
    //     }

    //     logger.info("Finished update method for TaxPayerIdentificationNumber with ID: {}", id);
    // }


    // @Override
    // public void update(Id id, TaxPayerIdentificationNumberDTO taxPayerIdentificationNumberDTO, Id documentId) {
    //     logger.info("Starting update method for TaxPayerIdentificationNumber with ID: {}", id);

    //     try {
    //         TaxPayerIdentificationNumber existingEntity = taxPayerIdentificationNumberRepository.getTaxPayerIdentificationNumber(id);
    //         delete(existingEntity.getId());
    //         TaxPayerIdentificationNumber updatedEntity = TaxPayerIdentificationNumberMapper.toEntity(taxPayerIdentificationNumberDTO);

    //         Documents documents = documentsRepository.findById(documentId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
    //         updatedEntity.setDocuments(documents);

    //         taxPayerIdentificationNumberRepository.updateTaxPayerIdentificationNumber(updatedEntity);

    //         Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
    //         if (pdf.isPresent()) {
    //             String s3Link = pdf.get().getS3Link();
    //             storageService.delete(s3Link);
    //             pdfRepository.deleteById(pdf.get().getId());
    //         }

    //         try {
    //             byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(taxPayerIdentificationNumberDTO);
    //             String s3l = storageService.upload(bytesOfDto);
    //             Pdf updatedPdf = new Pdf();
    //             updatedPdf.setResourceId(updatedEntity.getId().toString());
    //             updatedPdf.setS3Link(s3l);
    //             pdfRepository.save(updatedPdf);
    //         } catch (Exception e) {
    //             logger.error("Error generating PDF during update: {}", e.getMessage(), e);
    //         }
    //     } catch (Exception e) {
    //         logger.error("Error in update method: {}", e.getMessage(), e);
    //     }

    //     logger.info("Finished update method for TaxPayerIdentificationNumber with ID: {}", id);
    // }



    @Override
    public void update(Id id, TaxPayerIdentificationNumberDTO taxPayerIdentificationNumberDTO, Id documentId) {
        logger.info("Starting update method for TaxPayerIdentificationNumber with ID: {}", id);

        try {
            TaxPayerIdentificationNumber existingEntity = taxPayerIdentificationNumberRepository.getTaxPayerIdentificationNumber(id);
            delete(existingEntity.getId());

            TaxPayerIdentificationNumber updatedEntity = TaxPayerIdentificationNumberMapper.toEntity(taxPayerIdentificationNumberDTO);
            taxPayerIdentificationNumberRepository.addTaxPayerIdentificationNumber(updatedEntity);

            Documents documents = documentsRepository.findById(documentId)
                    .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
            updatedEntity.setDocuments(documents);
            documents.setTaxPayerIdentificationNumber(updatedEntity);
            documentsRepository.save(documents);

            Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
            if (pdf.isPresent()) {
                String s3Link = pdf.get().getS3Link();
                storageService.delete(s3Link);
                pdfRepository.deleteById(pdf.get().getId());
            }

            try {
                byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(taxPayerIdentificationNumberDTO);
                String s3l = storageService.upload(bytesOfDto);
                Pdf updatedPdf = new Pdf();
                updatedPdf.setResourceId(updatedEntity.getId().toString());
                updatedPdf.setS3Link(s3l);
                pdfRepository.save(updatedPdf);
            } catch (Exception e) {
                logger.error("Error generating PDF during update: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error("Error in update method: {}", e.getMessage(), e);
        }

        logger.info("Finished update method for TaxPayerIdentificationNumber with ID: {}", id);
    }

    @Override
    public void delete(Id id) {
        logger.info("Starting delete method for TaxPayerIdentificationNumber with ID: {}", id);

        try {
            Documents byTaxPayerIdentificationNumber = documentsRepository.getByTaxPayerIdentificationNumber(id);
            byTaxPayerIdentificationNumber.setTaxPayerIdentificationNumber(null);
            documentsRepository.save(byTaxPayerIdentificationNumber);
            taxPayerIdentificationNumberRepository.deleteTaxPayerIdentificationNumber(id);

            Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
            if (pdf.isPresent()) {
                String s3Link = pdf.get().getS3Link();
                storageService.delete(s3Link);
                pdfRepository.deleteById(pdf.get().getId());
            }
        } catch (Exception e) {
            logger.error("Error in delete method: {}", e.getMessage(), e);
        }

        logger.info("Finished delete method for TaxPayerIdentificationNumber with ID: {}", id);
    }
}
