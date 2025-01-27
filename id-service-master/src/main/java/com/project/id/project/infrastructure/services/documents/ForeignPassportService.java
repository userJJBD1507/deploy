package com.project.id.project.infrastructure.services.documents;

import com.project.id.project.application.DTOs.documents.ForeignPassportDTO;
import com.project.id.project.application.mappers.documents.ForeignPassportMapper;
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
import com.project.id.project.core.documents.entities.ForeignPassport;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaForeignPassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class ForeignPassportService implements CrudUpdated<ForeignPassportDTO, Id> {
//
//    @Autowired
//    private JpaForeignPassportRepository foreignPassportRepository;
//
//
//    @Autowired
//    private PdfGenerator pdfGenerator;
//    @Autowired
//    private StorageService storageService;
//    @Autowired
//    private PdfRepository pdfRepository;
//
//    @Autowired
//    private EntityPersonalDataRepository entityPersonalDataRepository;
//    @Autowired
//    private DocumentsRepository documentsRepository;
//
//    @Override
//    public void create(String username, ForeignPassportDTO dto) {
//        ForeignPassport entity = ForeignPassportMapper.toEntity(dto);
//        PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).orElseThrow(
//                () -> new IllegalArgumentException("Personal data not found for user: " + username)
//        );
//        Id id = personalData.getDocuments().getId();
//        Documents documents = documentsRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("Documents not found for ID: " + id)
//        );
//        if (documents.getForeignPassport() != null) {
//            throw new IllegalStateException("Foreign passport already exists for these documents.");
//        }
//        foreignPassportRepository.addForeignPassport(entity);
//        documents.setForeignPassport(entity);
//        entity.setDocuments(documents);
//        foreignPassportRepository.addForeignPassport(entity);
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
//
//    @Override
//    public Optional<ForeignPassportDTO> read(Id id) {
//        ForeignPassport entity = foreignPassportRepository.getForeignPassport(id);
//        return Optional.of(ForeignPassportMapper.toDTO(entity));
//    }
//
//    @Override
//    public void update(Id id, ForeignPassportDTO dto, Id documentId) {
//        ForeignPassport existingEntity = foreignPassportRepository.getForeignPassport(id);
//
//        ForeignPassport updatedEntity = ForeignPassportMapper.toEntity(dto);
//        updatedEntity.setId(existingEntity.getId());
//
//        Documents documents = documentsRepository.findById(documentId)
//                .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
//        updatedEntity.setDocuments(documents);
//
//        foreignPassportRepository.updateForeignPassport(updatedEntity);
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
//        Documents byForeignPassport = documentsRepository.getByForeignPassport(id);
//        byForeignPassport.setForeignPassport(null);
//        documentsRepository.save(byForeignPassport);
//        foreignPassportRepository.deleteForeignPassport(id);
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
public class ForeignPassportService implements CrudUpdated<ForeignPassportDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(ForeignPassportService.class);

    @Autowired
    private JpaForeignPassportRepository foreignPassportRepository;

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
    public void create(String username, ForeignPassportDTO dto) {
        logger.info("Starting create method for ForeignPassport with username: {}", username);

        try {
            ForeignPassport entity = ForeignPassportMapper.toEntity(dto);
            PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).orElseThrow(
                    () -> new IllegalArgumentException("Personal data not found for user: " + username)
            );

            Id id = personalData.getDocuments().getId();
            Documents documents = documentsRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("Documents not found for ID: " + id)
            );

            if (documents.getForeignPassport() != null) {
                throw new IllegalStateException("Foreign passport already exists for these documents.");
            }

            foreignPassportRepository.addForeignPassport(entity);
            documents.setForeignPassport(entity);
            entity.setDocuments(documents);
            foreignPassportRepository.addForeignPassport(entity);

            try {
                byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(dto);
                String s3Link = storageService.upload(bytesOfDto);
                Pdf pdf = new Pdf();
                pdf.setResourceId(entity.getId().toString());
                pdf.setS3Link(s3Link);
                pdfRepository.save(pdf);
            } catch (Exception e) {
                logger.error("Error generating PDF for ForeignPassport: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error("Error in create method: {}", e.getMessage(), e);
        }

        logger.info("Finished create method for ForeignPassport with username: {}", username);
    }

    @Override
    public Optional<ForeignPassportDTO> read(Id id) {
        logger.info("Reading ForeignPassport with ID: {}", id);
        ForeignPassport entity = foreignPassportRepository.getForeignPassport(id);
        logger.info("Successfully read ForeignPassport with ID: {}", id);
        return Optional.of(ForeignPassportMapper.toDTO(entity));
    }

    // @Override
    // public void update(Id id, ForeignPassportDTO dto, Id documentId) {
    //     logger.info("Starting update method for ForeignPassport with ID: {}", id);

    //     try {
    //         ForeignPassport existingEntity = foreignPassportRepository.getForeignPassport(id);
    //         ForeignPassport updatedEntity = ForeignPassportMapper.toEntity(dto);
    //         updatedEntity.setId(existingEntity.getId());

    //         Documents documents = documentsRepository.findById(documentId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
    //         updatedEntity.setDocuments(documents);

    //         foreignPassportRepository.updateForeignPassport(updatedEntity);

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

    //     logger.info("Finished update method for ForeignPassport with ID: {}", id);
    // }


    // @Override
    // public void update(Id id, ForeignPassportDTO dto, Id documentId) {
    //     logger.info("Starting update method for ForeignPassport with ID: {}", id);

    //     try {
    //         ForeignPassport existingEntity = foreignPassportRepository.getForeignPassport(id);
    //         delete(existingEntity.getId());
    //         ForeignPassport updatedEntity = ForeignPassportMapper.toEntity(dto);

    //         Documents documents = documentsRepository.findById(documentId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
    //         updatedEntity.setDocuments(documents);

    //         foreignPassportRepository.updateForeignPassport(updatedEntity);

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

    //     logger.info("Finished update method for ForeignPassport with ID: {}", id);
    // }



    @Override
    public void update(Id id, ForeignPassportDTO dto, Id documentId) {
        logger.info("Starting update method for ForeignPassport with ID: {}", id);

        try {
            ForeignPassport existingEntity = foreignPassportRepository.getForeignPassport(id);
            delete(existingEntity.getId());

            ForeignPassport updatedEntity = ForeignPassportMapper.toEntity(dto);
            foreignPassportRepository.addForeignPassport(updatedEntity);

            Documents documents = documentsRepository.findById(documentId)
                    .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
            updatedEntity.setDocuments(documents);
            documents.setForeignPassport(updatedEntity);
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

        logger.info("Finished update method for ForeignPassport with ID: {}", id);
    }

    @Override
    public void delete(Id id) {
        logger.info("Starting delete method for ForeignPassport with ID: {}", id);

        try {
            Documents byForeignPassport = documentsRepository.getByForeignPassport(id);
            byForeignPassport.setForeignPassport(null);
            documentsRepository.save(byForeignPassport);
            foreignPassportRepository.deleteForeignPassport(id);

            Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
            if (pdf.isPresent()) {
                String s3Link = pdf.get().getS3Link();
                storageService.delete(s3Link);
                pdfRepository.deleteById(pdf.get().getId());
            }
        } catch (Exception e) {
            logger.error("Error in delete method: {}", e.getMessage(), e);
        }

        logger.info("Finished delete method for ForeignPassport with ID: {}", id);
    }
}
