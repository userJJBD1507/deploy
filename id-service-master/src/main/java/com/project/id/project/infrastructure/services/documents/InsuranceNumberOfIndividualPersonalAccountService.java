package com.project.id.project.infrastructure.services.documents;

import com.project.id.project.application.DTOs.documents.InsuranceNumberOfIndividualPersonalAccountDTO;
import com.project.id.project.application.mappers.documents.InsuranceNumberOfIndividualPersonalAccountMapper;
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
import com.project.id.project.core.documents.entities.InsuranceNumberOfIndividualPersonalAccount;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaInsuranceNumberOfIndividualPersonalAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class InsuranceNumberOfIndividualPersonalAccountService
//        implements CrudUpdated<InsuranceNumberOfIndividualPersonalAccountDTO, Id> {
//
//    @Autowired
//    private JpaInsuranceNumberOfIndividualPersonalAccountRepository insuranceRepository;
//
//
//    @Autowired
//    private PdfGenerator pdfGenerator;
//    @Autowired
//    private StorageService storageService;
//    @Autowired
//    private PdfRepository pdfRepository;
//    @Autowired
//    private EntityPersonalDataRepository entityPersonalDataRepository;
//    @Autowired
//    private DocumentsRepository documentsRepository;
//
//    @Override
//    public void create(String username, InsuranceNumberOfIndividualPersonalAccountDTO dto) {
//        InsuranceNumberOfIndividualPersonalAccount entity = InsuranceNumberOfIndividualPersonalAccountMapper.toEntity(dto);
//        PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).orElseThrow(
//                () -> new IllegalArgumentException("Personal data not found for user: " + username)
//        );
//        Id id = personalData.getDocuments().getId();
//        Documents documents = documentsRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("Documents not found for ID: " + id)
//        );
//        if (documents.getInsuranceNumberOfIndividualPersonalAccount() != null) {
//            throw new IllegalStateException("Insurance number of individual personal account already exists for these documents.");
//        }
//        insuranceRepository.addInsuranceNumberOfIndividualPersonalAccount(entity);
//        documents.setInsuranceNumberOfIndividualPersonalAccount(entity);
//        entity.setDocuments(documents);
//        insuranceRepository.addInsuranceNumberOfIndividualPersonalAccount(entity);
//
//
//
//        try {
//            byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(dto);
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
//    public Optional<InsuranceNumberOfIndividualPersonalAccountDTO> read(Id id) {
//        InsuranceNumberOfIndividualPersonalAccount entity = insuranceRepository.getInsuranceNumberOfIndividualPersonalAccount(id);
//        return Optional.of(InsuranceNumberOfIndividualPersonalAccountMapper.toDTO(entity));
//    }
//
//    @Override
//    public void update(Id id, InsuranceNumberOfIndividualPersonalAccountDTO dto, Id documentId) {
//        InsuranceNumberOfIndividualPersonalAccount existingEntity = insuranceRepository.getInsuranceNumberOfIndividualPersonalAccount(id);
//
//        InsuranceNumberOfIndividualPersonalAccount updatedEntity = InsuranceNumberOfIndividualPersonalAccountMapper.toEntity(dto);
//        updatedEntity.setId(existingEntity.getId());
//
//        Documents documents = documentsRepository.findById(documentId)
//                .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
//        updatedEntity.setDocuments(documents);
//
//        insuranceRepository.updateInsuranceNumberOfIndividualPersonalAccount(updatedEntity);
//
//        Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
//        if (pdf.isEmpty()) {
//            return;
//        }
//        String s3Link = pdf.get().getS3Link();
//        storageService.delete(s3Link);
//        pdfRepository.deleteById(pdf.get().getId());
//        try {
//            byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(dto);
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
//        Documents byInsuranceNumberOfIndividualPersonalAccount = documentsRepository.getByInsuranceNumberOfIndividualPersonalAccount(id);
//        byInsuranceNumberOfIndividualPersonalAccount.setInsuranceNumberOfIndividualPersonalAccount(null);
//        documentsRepository.save(byInsuranceNumberOfIndividualPersonalAccount);
//        insuranceRepository.deleteInsuranceNumberOfIndividualPersonalAccount(id);
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
public class InsuranceNumberOfIndividualPersonalAccountService
        implements CrudUpdated<InsuranceNumberOfIndividualPersonalAccountDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(InsuranceNumberOfIndividualPersonalAccountService.class);

    @Autowired
    private JpaInsuranceNumberOfIndividualPersonalAccountRepository insuranceRepository;

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
    public void create(String username, InsuranceNumberOfIndividualPersonalAccountDTO dto) {
        logger.info("Starting create method for InsuranceNumberOfIndividualPersonalAccount with username: {}", username);

        try {
            InsuranceNumberOfIndividualPersonalAccount entity = InsuranceNumberOfIndividualPersonalAccountMapper.toEntity(dto);
            PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).orElseThrow(
                    () -> new IllegalArgumentException("Personal data not found for user: " + username)
            );

            Id id = personalData.getDocuments().getId();
            Documents documents = documentsRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("Documents not found for ID: " + id)
            );

            if (documents.getInsuranceNumberOfIndividualPersonalAccount() != null) {
                throw new IllegalStateException("Insurance number of individual personal account already exists for these documents.");
            }

            insuranceRepository.addInsuranceNumberOfIndividualPersonalAccount(entity);
            documents.setInsuranceNumberOfIndividualPersonalAccount(entity);
            entity.setDocuments(documents);
            insuranceRepository.addInsuranceNumberOfIndividualPersonalAccount(entity);

            try {
                byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(dto);
                String s3Link = storageService.upload(bytesOfDto);
                Pdf pdf = new Pdf();
                pdf.setResourceId(entity.getId().toString());
                pdf.setS3Link(s3Link);
                pdfRepository.save(pdf);
            } catch (Exception e) {
                logger.error("Error generating PDF for InsuranceNumberOfIndividualPersonalAccount: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error("Error in create method: {}", e.getMessage(), e);
        }

        logger.info("Finished create method for InsuranceNumberOfIndividualPersonalAccount with username: {}", username);
    }

    @Override
    public Optional<InsuranceNumberOfIndividualPersonalAccountDTO> read(Id id) {
        logger.info("Reading InsuranceNumberOfIndividualPersonalAccount with ID: {}", id);
        InsuranceNumberOfIndividualPersonalAccount entity = insuranceRepository.getInsuranceNumberOfIndividualPersonalAccount(id);
        logger.info("Successfully read InsuranceNumberOfIndividualPersonalAccount with ID: {}", id);
        return Optional.of(InsuranceNumberOfIndividualPersonalAccountMapper.toDTO(entity));
    }

    // @Override
    // public void update(Id id, InsuranceNumberOfIndividualPersonalAccountDTO dto, Id documentId) {
    //     logger.info("Starting update method for InsuranceNumberOfIndividualPersonalAccount with ID: {}", id);

    //     try {
    //         InsuranceNumberOfIndividualPersonalAccount existingEntity = insuranceRepository.getInsuranceNumberOfIndividualPersonalAccount(id);
    //         InsuranceNumberOfIndividualPersonalAccount updatedEntity = InsuranceNumberOfIndividualPersonalAccountMapper.toEntity(dto);
    //         updatedEntity.setId(existingEntity.getId());

    //         Documents documents = documentsRepository.findById(documentId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
    //         updatedEntity.setDocuments(documents);

    //         insuranceRepository.updateInsuranceNumberOfIndividualPersonalAccount(updatedEntity);

    //         Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
    //         if (pdf.isPresent()) {
    //             String s3Link = pdf.get().getS3Link();
    //             storageService.delete(s3Link);
    //             pdfRepository.deleteById(pdf.get().getId());
    //         }

    //         try {
    //             byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(dto);
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

    //     logger.info("Finished update method for InsuranceNumberOfIndividualPersonalAccount with ID: {}", id);
    // }



    // @Override
    // public void update(Id id, InsuranceNumberOfIndividualPersonalAccountDTO dto, Id documentId) {
    //     logger.info("Starting update method for InsuranceNumberOfIndividualPersonalAccount with ID: {}", id);

    //     try {
    //         InsuranceNumberOfIndividualPersonalAccount existingEntity = insuranceRepository.getInsuranceNumberOfIndividualPersonalAccount(id);
    //         delete(existingEntity.getId());
    //         InsuranceNumberOfIndividualPersonalAccount updatedEntity = InsuranceNumberOfIndividualPersonalAccountMapper.toEntity(dto);
            

    //         Documents documents = documentsRepository.findById(documentId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
    //         updatedEntity.setDocuments(documents);

    //         insuranceRepository.updateInsuranceNumberOfIndividualPersonalAccount(updatedEntity);

    //         Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
    //         if (pdf.isPresent()) {
    //             String s3Link = pdf.get().getS3Link();
    //             storageService.delete(s3Link);
    //             pdfRepository.deleteById(pdf.get().getId());
    //         }

    //         try {
    //             byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(dto);
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

    //     logger.info("Finished update method for InsuranceNumberOfIndividualPersonalAccount with ID: {}", id);
    // }



    @Override
    public void update(Id id, InsuranceNumberOfIndividualPersonalAccountDTO dto, Id documentId) {
        logger.info("Starting update method for InsuranceNumberOfIndividualPersonalAccount with ID: {}", id);

        try {
            InsuranceNumberOfIndividualPersonalAccount existingEntity = insuranceRepository.getInsuranceNumberOfIndividualPersonalAccount(id);
            delete(existingEntity.getId());

            InsuranceNumberOfIndividualPersonalAccount updatedEntity = InsuranceNumberOfIndividualPersonalAccountMapper.toEntity(dto);
            insuranceRepository.addInsuranceNumberOfIndividualPersonalAccount(updatedEntity);

            Documents documents = documentsRepository.findById(documentId)
                    .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
            updatedEntity.setDocuments(documents);
            documents.setInsuranceNumberOfIndividualPersonalAccount(updatedEntity);
            documentsRepository.save(documents);

            Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
            if (pdf.isPresent()) {
                String s3Link = pdf.get().getS3Link();
                storageService.delete(s3Link);
                pdfRepository.deleteById(pdf.get().getId());
            }

            try {
                byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(dto);
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

        logger.info("Finished update method for InsuranceNumberOfIndividualPersonalAccount with ID: {}", id);
    }

    @Override
    public void delete(Id id) {
        logger.info("Starting delete method for InsuranceNumberOfIndividualPersonalAccount with ID: {}", id);

        try {
            Documents byInsuranceNumberOfIndividualPersonalAccount = documentsRepository.getByInsuranceNumberOfIndividualPersonalAccount(id);
            byInsuranceNumberOfIndividualPersonalAccount.setInsuranceNumberOfIndividualPersonalAccount(null);
            documentsRepository.save(byInsuranceNumberOfIndividualPersonalAccount);
            insuranceRepository.deleteInsuranceNumberOfIndividualPersonalAccount(id);

            Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
            if (pdf.isPresent()) {
                String s3Link = pdf.get().getS3Link();
                storageService.delete(s3Link);
                pdfRepository.deleteById(pdf.get().getId());
            }
        } catch (Exception e) {
            logger.error("Error in delete method: {}", e.getMessage(), e);
        }

        logger.info("Finished delete method for InsuranceNumberOfIndividualPersonalAccount with ID: {}", id);
    }
}
