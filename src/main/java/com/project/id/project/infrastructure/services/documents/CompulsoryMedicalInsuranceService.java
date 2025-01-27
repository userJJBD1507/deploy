package com.project.id.project.infrastructure.services.documents;

import com.project.id.project.application.DTOs.documents.CompulsoryMedicalInsuranceDTO;
import com.project.id.project.application.mappers.documents.CompulsoryMedicalInsuranceMapper;
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
import com.project.id.project.core.documents.entities.CompulsoryMedicalInsurance;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaCompulsoryMedicalInsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class CompulsoryMedicalInsuranceService implements CrudUpdated<CompulsoryMedicalInsuranceDTO, Id> {
//
//    @Autowired
//    private JpaCompulsoryMedicalInsuranceRepository compulsoryMedicalInsuranceRepository;
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
//    public void create(String username, CompulsoryMedicalInsuranceDTO dto) {
//        CompulsoryMedicalInsurance entity = CompulsoryMedicalInsuranceMapper.toEntity(dto);
//        PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).orElseThrow(
//                () -> new IllegalArgumentException("Personal data not found for user: " + username)
//        );
//        Id id = personalData.getDocuments().getId();
//        Documents documents = documentsRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("Documents not found for ID: " + id)
//        );
//        if (documents.getCompulsoryMedicalInsurance() != null) {
//            throw new IllegalStateException("Compulsory medical insurance already exists for these documents.");
//        }
//        compulsoryMedicalInsuranceRepository.addCompulsoryMedicalInsurance(entity);
//        documents.setCompulsoryMedicalInsurance(entity);
//        entity.setDocuments(documents);
//        compulsoryMedicalInsuranceRepository.addCompulsoryMedicalInsurance(entity);
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
//    public Optional<CompulsoryMedicalInsuranceDTO> read(Id id) {
//        CompulsoryMedicalInsurance entity = compulsoryMedicalInsuranceRepository.getCompulsoryMedicalInsurance(id);
//        return Optional.of(CompulsoryMedicalInsuranceMapper.toDTO(entity));
//    }
//
//    @Override
//    public void update(Id id, CompulsoryMedicalInsuranceDTO dto, Id documentId) {
//        CompulsoryMedicalInsurance existingEntity = compulsoryMedicalInsuranceRepository.getCompulsoryMedicalInsurance(id);
//
//        CompulsoryMedicalInsurance updatedEntity = CompulsoryMedicalInsuranceMapper.toEntity(dto);
//        updatedEntity.setId(existingEntity.getId());
//
//        Documents documents = documentsRepository.findById(documentId)
//                .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
//        updatedEntity.setDocuments(documents);
//
//        compulsoryMedicalInsuranceRepository.updateCompulsoryMedicalInsurance(updatedEntity);
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
//        Documents byCompulsoryMedicalInsurance = documentsRepository.getByCompulsoryMedicalInsurance(id);
//        byCompulsoryMedicalInsurance.setCompulsoryMedicalInsurance(null);
//        documentsRepository.save(byCompulsoryMedicalInsurance);
//        compulsoryMedicalInsuranceRepository.deleteCompulsoryMedicalInsurance(id);
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
public class CompulsoryMedicalInsuranceService implements CrudUpdated<CompulsoryMedicalInsuranceDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(CompulsoryMedicalInsuranceService.class);

    @Autowired
    private JpaCompulsoryMedicalInsuranceRepository compulsoryMedicalInsuranceRepository;

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
    public void create(String username, CompulsoryMedicalInsuranceDTO dto) {
        logger.info("Starting create method for CompulsoryMedicalInsurance with username: {}", username);

        try {
            CompulsoryMedicalInsurance entity = CompulsoryMedicalInsuranceMapper.toEntity(dto);
            PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).orElseThrow(
                    () -> new IllegalArgumentException("Personal data not found for user: " + username)
            );

            Id id = personalData.getDocuments().getId();
            Documents documents = documentsRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("Documents not found for ID: " + id)
            );

            if (documents.getCompulsoryMedicalInsurance() != null) {
                throw new IllegalStateException("Compulsory medical insurance already exists for these documents.");
            }

            compulsoryMedicalInsuranceRepository.addCompulsoryMedicalInsurance(entity);
            documents.setCompulsoryMedicalInsurance(entity);
            entity.setDocuments(documents);
            compulsoryMedicalInsuranceRepository.addCompulsoryMedicalInsurance(entity);

            try {
                byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(dto);
                String s3Link = storageService.upload(bytesOfDto);
                Pdf pdf = new Pdf();
                pdf.setResourceId(entity.getId().toString());
                pdf.setS3Link(s3Link);
                pdfRepository.save(pdf);
            } catch (Exception e) {
                logger.error("Error generating PDF for CompulsoryMedicalInsurance: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error("Error in create method: {}", e.getMessage(), e);
        }

        logger.info("Finished create method for CompulsoryMedicalInsurance with username: {}", username);
    }

    @Override
    public Optional<CompulsoryMedicalInsuranceDTO> read(Id id) {
        logger.info("Reading CompulsoryMedicalInsurance with ID: {}", id);
        CompulsoryMedicalInsurance entity = compulsoryMedicalInsuranceRepository.getCompulsoryMedicalInsurance(id);
        logger.info("Successfully read CompulsoryMedicalInsurance with ID: {}", id);
        return Optional.of(CompulsoryMedicalInsuranceMapper.toDTO(entity));
    }

    // @Override
    // public void update(Id id, CompulsoryMedicalInsuranceDTO dto, Id documentId) {
    //     logger.info("Starting update method for CompulsoryMedicalInsurance with ID: {}", id);

    //     try {
    //         CompulsoryMedicalInsurance existingEntity = compulsoryMedicalInsuranceRepository.getCompulsoryMedicalInsurance(id);
    //         CompulsoryMedicalInsurance updatedEntity = CompulsoryMedicalInsuranceMapper.toEntity(dto);
    //         updatedEntity.setId(existingEntity.getId());

    //         Documents documents = documentsRepository.findById(documentId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
    //         updatedEntity.setDocuments(documents);

    //         compulsoryMedicalInsuranceRepository.updateCompulsoryMedicalInsurance(updatedEntity);

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

    //     logger.info("Finished update method for CompulsoryMedicalInsurance with ID: {}", id);
    // }


    // @Override
    // public void update(Id id, CompulsoryMedicalInsuranceDTO dto, Id documentId) {
    //     logger.info("Starting update method for CompulsoryMedicalInsurance with ID: {}", id);

    //     try {
    //         CompulsoryMedicalInsurance existingEntity = compulsoryMedicalInsuranceRepository.getCompulsoryMedicalInsurance(id);
    //         delete(existingEntity.getId());
    //         CompulsoryMedicalInsurance updatedEntity = CompulsoryMedicalInsuranceMapper.toEntity(dto);

    //         Documents documents = documentsRepository.findById(documentId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
    //         updatedEntity.setDocuments(documents);

    //         compulsoryMedicalInsuranceRepository.updateCompulsoryMedicalInsurance(updatedEntity);

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

    //     logger.info("Finished update method for CompulsoryMedicalInsurance with ID: {}", id);
    // }

    @Override
    public void update(Id id, CompulsoryMedicalInsuranceDTO dto, Id documentId) {
    logger.info("Starting update method for CompulsoryMedicalInsurance with ID: {}", id);

    try {
        CompulsoryMedicalInsurance existingEntity = compulsoryMedicalInsuranceRepository.getCompulsoryMedicalInsurance(id);
        delete(existingEntity.getId());

        CompulsoryMedicalInsurance updatedEntity = CompulsoryMedicalInsuranceMapper.toEntity(dto);
        compulsoryMedicalInsuranceRepository.addCompulsoryMedicalInsurance(updatedEntity);

        Documents documents = documentsRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
        updatedEntity.setDocuments(documents);
        documents.setCompulsoryMedicalInsurance(updatedEntity);
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

    logger.info("Finished update method for CompulsoryMedicalInsurance with ID: {}", id);
}

    @Override
    public void delete(Id id) {
        logger.info("Starting delete method for CompulsoryMedicalInsurance with ID: {}", id);

        try {
            Documents byCompulsoryMedicalInsurance = documentsRepository.getByCompulsoryMedicalInsurance(id);
            byCompulsoryMedicalInsurance.setCompulsoryMedicalInsurance(null);
            documentsRepository.save(byCompulsoryMedicalInsurance);
            compulsoryMedicalInsuranceRepository.deleteCompulsoryMedicalInsurance(id);

            Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
            if (pdf.isPresent()) {
                String s3Link = pdf.get().getS3Link();
                storageService.delete(s3Link);
                pdfRepository.deleteById(pdf.get().getId());
            }
        } catch (Exception e) {
            logger.error("Error in delete method: {}", e.getMessage(), e);
        }

        logger.info("Finished delete method for CompulsoryMedicalInsurance with ID: {}", id);
    }
}
