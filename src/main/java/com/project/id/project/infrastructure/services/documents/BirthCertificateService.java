package com.project.id.project.infrastructure.services.documents;

import com.project.id.project.application.DTOs.documents.BirthCertificateDTO;
import com.project.id.project.application.mappers.documents.BirthCertificateMapper;
import com.project.id.project.application.services.CrudService;
import com.project.id.project.application.services.Security.NewCrudSer;
import com.project.id.project.application.services.linkers.AddressesRepository;
import com.project.id.project.application.services.linkers.DocumentsRepository;
import com.project.id.project.application.services.pdf.PdfGenerator;
import com.project.id.project.application.services.pdf.handlers.CrudUpdated;
import com.project.id.project.application.services.pdf.handlers.Pdf;
import com.project.id.project.application.services.pdf.handlers.PdfRepository;
import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.BirthCertificate;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaBirthCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class BirthCertificateService implements CrudUpdated<BirthCertificateDTO, Id> {
//
//    @Autowired
//    private JpaBirthCertificateRepository birthCertificateRepository;
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
//    public void create(String username, BirthCertificateDTO dto) {
//        BirthCertificate entity = BirthCertificateMapper.toEntity(dto);
//        PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).orElseThrow(
//                () -> new IllegalArgumentException("Personal data not found for user: " + username)
//        );
//
//        Id id = personalData.getDocuments().getId();
//        Documents documents = documentsRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("Documents not found for ID: " + id)
//        );
//        if (documents.getBirthCertificate() != null) {
//            throw new IllegalStateException("Birth certificate already exists for these documents.");
//        }
//        birthCertificateRepository.addBirthCertificate(entity);
//        documents.setBirthCertificate(entity);
//        entity.setDocuments(documents);
//        birthCertificateRepository.addBirthCertificate(entity);
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
//    public Optional<BirthCertificateDTO> read(Id id) {
//        BirthCertificate entity = birthCertificateRepository.getBirthCertificate(id);
//        return Optional.of(BirthCertificateMapper.toDTO(entity));
//    }
//
//    @Override
//    public void update(Id id, BirthCertificateDTO dto, Id documentId) {
//        BirthCertificate existingEntity = birthCertificateRepository.getBirthCertificate(id);
//
//        BirthCertificate updatedEntity = BirthCertificateMapper.toEntity(dto);
//        updatedEntity.setId(existingEntity.getId());
//
//        Documents documents = documentsRepository.findById(documentId)
//                .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
//        updatedEntity.setDocuments(documents);
//
//        birthCertificateRepository.updateBirthCertificate(updatedEntity);
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
//    @Override
//    public void delete(Id id) {
//        Documents byBirthCertificate = documentsRepository.getByBirthCertificate(id);
//        byBirthCertificate.setBirthCertificate(null);
//        documentsRepository.save(byBirthCertificate);
//        birthCertificateRepository.deleteBirthCertificate(id);
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
public class BirthCertificateService implements CrudUpdated<BirthCertificateDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(BirthCertificateService.class);

    @Autowired
    private JpaBirthCertificateRepository birthCertificateRepository;

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
    public void create(String username, BirthCertificateDTO dto) {
        logger.info("Starting create method for BirthCertificate with username: {}", username);

        try {
            BirthCertificate entity = BirthCertificateMapper.toEntity(dto);
            PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).orElseThrow(
                    () -> new IllegalArgumentException("Personal data not found for user: " + username)
            );

            Id id = personalData.getDocuments().getId();
            Documents documents = documentsRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("Documents not found for ID: " + id)
            );

            if (documents.getBirthCertificate() != null) {
                throw new IllegalStateException("Birth certificate already exists for these documents.");
            }

            birthCertificateRepository.addBirthCertificate(entity);
            documents.setBirthCertificate(entity);
            entity.setDocuments(documents);
            birthCertificateRepository.addBirthCertificate(entity);

            try {
                byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(dto);
                String s3Link = storageService.upload(bytesOfDto);
                Pdf pdf = new Pdf();
                pdf.setResourceId(entity.getId().toString());
                pdf.setS3Link(s3Link);
                pdfRepository.save(pdf);
            } catch (Exception e) {
                logger.error("Error generating PDF for BirthCertificate: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error("Error in create method: {}", e.getMessage(), e);
        }

        logger.info("Finished create method for BirthCertificate with username: {}", username);
    }

    @Override
    public Optional<BirthCertificateDTO> read(Id id) {
        logger.info("Reading BirthCertificate with ID: {}", id);
        BirthCertificate entity = birthCertificateRepository.getBirthCertificate(id);
        logger.info("Successfully read BirthCertificate with ID: {}", id);
        return Optional.of(BirthCertificateMapper.toDTO(entity));
    }

    // @Override
    // public void update(Id id, BirthCertificateDTO dto, Id documentId) {
    //     logger.info("Starting update method for BirthCertificate with ID: {}", id);

    //     try {
    //         BirthCertificate existingEntity = birthCertificateRepository.getBirthCertificate(id);
    //         BirthCertificate updatedEntity = BirthCertificateMapper.toEntity(dto);
    //         updatedEntity.setId(existingEntity.getId());

    //         Documents documents = documentsRepository.findById(documentId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
    //         updatedEntity.setDocuments(documents);

    //         birthCertificateRepository.updateBirthCertificate(updatedEntity);

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

    //     logger.info("Finished update method for BirthCertificate with ID: {}", id);
    // }


    @Override
    public void update(Id id, BirthCertificateDTO dto, Id documentId) {
        logger.info("Starting update method for BirthCertificate with ID: {}", id);

        try {
            BirthCertificate existingEntity = birthCertificateRepository.getBirthCertificate(id);
            delete(existingEntity.getId());
        
            BirthCertificate updatedEntity = BirthCertificateMapper.toEntity(dto);
            birthCertificateRepository.addBirthCertificate(updatedEntity);
        
            Documents documents = documentsRepository.findById(documentId)
                    .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
            updatedEntity.setDocuments(documents);
            documents.setBirthCertificate(updatedEntity);
        
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
        

        logger.info("Finished update method for BirthCertificate with ID: {}", id);
    }
    @Override
    public void delete(Id id) {
        logger.info("Starting delete method for BirthCertificate with ID: {}", id);

        try {
            Documents byBirthCertificate = documentsRepository.getByBirthCertificate(id);
            byBirthCertificate.setBirthCertificate(null);
            documentsRepository.save(byBirthCertificate);
            birthCertificateRepository.deleteBirthCertificate(id);

            Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
            if (pdf.isPresent()) {
                String s3Link = pdf.get().getS3Link();
                storageService.delete(s3Link);
                pdfRepository.deleteById(pdf.get().getId());
            }
        } catch (Exception e) {
            logger.error("Error in delete method: {}", e.getMessage(), e);
        }

        logger.info("Finished delete method for BirthCertificate with ID: {}", id);
    }
}
