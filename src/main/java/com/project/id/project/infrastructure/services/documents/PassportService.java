package com.project.id.project.infrastructure.services.documents;

import com.project.id.project.application.DTOs.documents.PassportDTO;
import com.project.id.project.application.mappers.documents.PassportMapper;
import com.project.id.project.application.services.CrudService;
import com.project.id.project.application.services.Security.NewCrudSer;
import com.project.id.project.application.services.linkers.DocumentsRepository;
import com.project.id.project.application.services.pdf.PdfGenerator;
import com.project.id.project.application.services.pdf.handlers.CrudUpdated;
import com.project.id.project.application.services.pdf.handlers.Pdf;
import com.project.id.project.application.services.pdf.handlers.PdfRepository;
import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.Passport;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaPassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class PassportService implements CrudUpdated<PassportDTO, Id> {
//
//    @Autowired
//    private JpaPassportRepository passportRepository;
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
//    public void create(String username, PassportDTO passportDTO) {
//        Passport entity = PassportMapper.toEntity(passportDTO);
//        PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).orElseThrow(
//                () -> new IllegalArgumentException("Personal data not found for user: " + username)
//        );
//        Id id = personalData.getDocuments().getId();
//        Documents documents = documentsRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("Documents not found for ID: " + id)
//        );
//        if (documents.getPassport() != null) {
//            throw new IllegalStateException("Passport already exists for these documents.");
//        }
//        passportRepository.addPassport(entity);
//        documents.setPassport(entity);
//        entity.setDocuments(documents);
//        passportRepository.addPassport(entity);
//
//
//
//        try {
//            byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(passportDTO);
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
//    public Optional<PassportDTO> read(Id id) {
//        Passport passport = passportRepository.getPassport(id);
//        return Optional.of(PassportMapper.toDTO(passport));
//    }
//
//    @Override
//    public void update(Id id, PassportDTO passportDTO, Id documentId) {
//        Passport existingEntity = passportRepository.getPassport(id);
//        Passport updatedEntity = PassportMapper.toEntity(passportDTO);
//        updatedEntity.setId(existingEntity.getId());
//
//        Documents documents = documentsRepository.findById(documentId)
//                .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
//
//        updatedEntity.setDocuments(documents);
//
//        passportRepository.updatePassport(updatedEntity);
//
//        Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
//        if (pdf.isEmpty()) {
//            return;
//        }
//        String s3Link = pdf.get().getS3Link();
//        storageService.delete(s3Link);
//        pdfRepository.deleteById(pdf.get().getId());
//        try {
//            byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(passportDTO);
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
//        Documents byPassport = documentsRepository.getByPassport(id);
//        byPassport.setPassport(null);
//        documentsRepository.save(byPassport);
//        passportRepository.deletePassport(id);
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
public class PassportService implements CrudUpdated<PassportDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(PassportService.class);

    @Autowired
    private JpaPassportRepository passportRepository;

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
    public void create(String username, PassportDTO passportDTO) {
        logger.info("Starting create method for Passport with username: {}", username);

        try {
            Passport entity = PassportMapper.toEntity(passportDTO);
            PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).orElseThrow(
                    () -> new IllegalArgumentException("Personal data not found for user: " + username)
            );

            Id id = personalData.getDocuments().getId();
            Documents documents = documentsRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("Documents not found for ID: " + id)
            );

            if (documents.getPassport() != null) {
                throw new IllegalStateException("Passport already exists for these documents.");
            }

            passportRepository.addPassport(entity);
            documents.setPassport(entity);
            entity.setDocuments(documents);
            passportRepository.addPassport(entity);

            try {
                byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(passportDTO);
                String s3Link = storageService.upload(bytesOfDto);
                Pdf pdf = new Pdf();
                pdf.setResourceId(entity.getId().toString());
                pdf.setS3Link(s3Link);
                pdfRepository.save(pdf);
            } catch (Exception e) {
                logger.error("Error generating PDF for Passport: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error("Error in create method: {}", e.getMessage(), e);
        }

        logger.info("Finished create method for Passport with username: {}", username);
    }

    @Override
    public Optional<PassportDTO> read(Id id) {
        logger.info("Reading Passport with ID: {}", id);
        Passport passport = passportRepository.getPassport(id);
        logger.info("Successfully read Passport with ID: {}", id);
        return Optional.of(PassportMapper.toDTO(passport));
    }

    // @Override
    // public void update(Id id, PassportDTO passportDTO, Id documentId) {
    //     logger.info("Starting update method for Passport with ID: {}", id);

    //     try {
    //         Passport existingEntity = passportRepository.getPassport(id);
    //         Passport updatedEntity = PassportMapper.toEntity(passportDTO);
    //         updatedEntity.setId(existingEntity.getId());

    //         Documents documents = documentsRepository.findById(documentId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
    //         updatedEntity.setDocuments(documents);

    //         passportRepository.updatePassport(updatedEntity);

    //         Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
    //         if (pdf.isPresent()) {
    //             String s3Link = pdf.get().getS3Link();
    //             storageService.delete(s3Link);
    //             pdfRepository.deleteById(pdf.get().getId());
    //         }

    //         try {
    //             byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(passportDTO);
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

    //     logger.info("Finished update method for Passport with ID: {}", id);
    // }


    // @Override
    // public void update(Id id, PassportDTO passportDTO, Id documentId) {
    //     logger.info("Starting update method for Passport with ID: {}", id);

    //     try {
    //         Passport existingEntity = passportRepository.getPassport(id);
    //         delete(existingEntity.getId());
    //         Passport updatedEntity = PassportMapper.toEntity(passportDTO);

    //         Documents documents = documentsRepository.findById(documentId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
    //         updatedEntity.setDocuments(documents);

    //         passportRepository.updatePassport(updatedEntity);

    //         Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
    //         if (pdf.isPresent()) {
    //             String s3Link = pdf.get().getS3Link();
    //             storageService.delete(s3Link);
    //             pdfRepository.deleteById(pdf.get().getId());
    //         }

    //         try {
    //             byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(passportDTO);
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

    //     logger.info("Finished update method for Passport with ID: {}", id);
    // }



    @Override
    public void update(Id id, PassportDTO passportDTO, Id documentId) {
        logger.info("Starting update method for Passport with ID: {}", id);

        try {
            Passport existingEntity = passportRepository.getPassport(id);
            delete(existingEntity.getId());

            Passport updatedEntity = PassportMapper.toEntity(passportDTO);
            passportRepository.addPassport(updatedEntity);

            Documents documents = documentsRepository.findById(documentId)
                    .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
            updatedEntity.setDocuments(documents);
            documents.setPassport(updatedEntity);
            documentsRepository.save(documents);

            Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
            if (pdf.isPresent()) {
                String s3Link = pdf.get().getS3Link();
                storageService.delete(s3Link);
                pdfRepository.deleteById(pdf.get().getId());
            }

            try {
                byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(passportDTO);
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

        logger.info("Finished update method for Passport with ID: {}", id);
    }

    @Override
    public void delete(Id id) {
        logger.info("Starting delete method for Passport with ID: {}", id);

        try {
            Documents byPassport = documentsRepository.getByPassport(id);
            byPassport.setPassport(null);
            documentsRepository.save(byPassport);
            passportRepository.deletePassport(id);

            Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
            if (pdf.isPresent()) {
                String s3Link = pdf.get().getS3Link();
                storageService.delete(s3Link);
                pdfRepository.deleteById(pdf.get().getId());
            }
        } catch (Exception e) {
            logger.error("Error in delete method: {}", e.getMessage(), e);
        }

        logger.info("Finished delete method for Passport with ID: {}", id);
    }
}
