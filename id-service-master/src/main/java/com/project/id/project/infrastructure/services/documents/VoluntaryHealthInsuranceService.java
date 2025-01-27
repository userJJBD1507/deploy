package com.project.id.project.infrastructure.services.documents;

import com.project.id.project.application.DTOs.documents.VoluntaryHealthInsuranceDTO;
import com.project.id.project.application.mappers.documents.VoluntaryHealthInsuranceMapper;
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
import com.project.id.project.core.documents.entities.VoluntaryHealthInsurance;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaVoluntaryHealthInsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class VoluntaryHealthInsuranceService implements CrudUpdated<VoluntaryHealthInsuranceDTO, Id> {
//
//    @Autowired
//    private JpaVoluntaryHealthInsuranceRepository voluntaryHealthInsuranceRepository;
//
//    @Autowired
//    private PdfRepository pdfRepository;
//    @Autowired
//    private PdfGenerator pdfGenerator;
//    @Autowired
//    private StorageService storageService;
//
//    @Autowired
//    private EntityPersonalDataRepository entityPersonalDataRepository;
//    @Autowired
//    private DocumentsRepository documentsRepository;
//
//@Override
//public void create(String username, VoluntaryHealthInsuranceDTO voluntaryHealthInsuranceDTO) {
//    VoluntaryHealthInsurance entity = VoluntaryHealthInsuranceMapper.toEntity(voluntaryHealthInsuranceDTO);
//    PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).orElseThrow(
//            () -> new IllegalArgumentException("Personal data not found for user: " + username)
//    );
//    Id id = personalData.getDocuments().getId();
//    Documents documents = documentsRepository.findById(id).orElseThrow(
//            () -> new IllegalArgumentException("Documents not found for ID: " + id)
//    );
//    if (documents.getVoluntaryHealthInsurance() != null) {
//        throw new IllegalStateException("Voluntary health insurance already exists for these documents.");
//    }
//    voluntaryHealthInsuranceRepository.addVoluntaryHealthInsurance(entity);
//    documents.setVoluntaryHealthInsurance(entity);
//    entity.setDocuments(documents);
//    voluntaryHealthInsuranceRepository.addVoluntaryHealthInsurance(entity);
//
//
//    try {
//        byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(voluntaryHealthInsuranceDTO);
//        String s3Link = storageService.upload(bytesOfDto);
//        Pdf pdf = new Pdf();
//        pdf.setResourceId(entity.getId().toString());
//        pdf.setS3Link(s3Link);
//        pdfRepository.save(pdf);
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}
//
//    @Override
//    public Optional<VoluntaryHealthInsuranceDTO> read(Id id) {
//        VoluntaryHealthInsurance entity = voluntaryHealthInsuranceRepository.getVoluntaryHealthInsurance(id);
//        return Optional.of(VoluntaryHealthInsuranceMapper.toDTO(entity));
//    }
//
//    @Override
//    public void update(Id id, VoluntaryHealthInsuranceDTO voluntaryHealthInsuranceDTO, Id documentId) {
//        VoluntaryHealthInsurance existingEntity = voluntaryHealthInsuranceRepository.getVoluntaryHealthInsurance(id);
//        VoluntaryHealthInsurance updatedEntity = VoluntaryHealthInsuranceMapper.toEntity(voluntaryHealthInsuranceDTO);
//        updatedEntity.setId(existingEntity.getId());
//
//        Documents documents = documentsRepository.findById(documentId)
//                .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
//
//        updatedEntity.setDocuments(documents);
//
//        voluntaryHealthInsuranceRepository.updateVoluntaryHealthInsurance(updatedEntity);
//
//        Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
//        if (pdf.isEmpty()) {
//            return;
//        }
//        String s3Link = pdf.get().getS3Link();
//        storageService.delete(s3Link);
//        pdfRepository.deleteById(pdf.get().getId());
//        try {
//            byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(voluntaryHealthInsuranceDTO);
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
//        Documents byVoluntaryHealthInsurance = documentsRepository.getByVoluntaryHealthInsurance(id);
//        byVoluntaryHealthInsurance.setVoluntaryHealthInsurance(null);
//        documentsRepository.save(byVoluntaryHealthInsurance);
//        voluntaryHealthInsuranceRepository.deleteVoluntaryHealthInsurance(id);
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
public class VoluntaryHealthInsuranceService implements CrudUpdated<VoluntaryHealthInsuranceDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(VoluntaryHealthInsuranceService.class);

    @Autowired
    private JpaVoluntaryHealthInsuranceRepository voluntaryHealthInsuranceRepository;

    @Autowired
    private PdfRepository pdfRepository;
    @Autowired
    private PdfGenerator pdfGenerator;
    @Autowired
    private StorageService storageService;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Autowired
    private DocumentsRepository documentsRepository;

    @Override
    public void create(String username, VoluntaryHealthInsuranceDTO voluntaryHealthInsuranceDTO) {
        logger.info("Starting create method for VoluntaryHealthInsurance with username: {}", username);

        try {
            VoluntaryHealthInsurance entity = VoluntaryHealthInsuranceMapper.toEntity(voluntaryHealthInsuranceDTO);
            PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).orElseThrow(
                    () -> new IllegalArgumentException("Personal data not found for user: " + username)
            );
            Id id = personalData.getDocuments().getId();
            Documents documents = documentsRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("Documents not found for ID: " + id)
            );
            if (documents.getVoluntaryHealthInsurance() != null) {
                throw new IllegalStateException("Voluntary health insurance already exists for these documents.");
            }

            voluntaryHealthInsuranceRepository.addVoluntaryHealthInsurance(entity);
            documents.setVoluntaryHealthInsurance(entity);
            entity.setDocuments(documents);
            voluntaryHealthInsuranceRepository.addVoluntaryHealthInsurance(entity);

            try {
                byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(voluntaryHealthInsuranceDTO);
                String s3Link = storageService.upload(bytesOfDto);
                Pdf pdf = new Pdf();
                pdf.setResourceId(entity.getId().toString());
                pdf.setS3Link(s3Link);
                pdfRepository.save(pdf);
            } catch (Exception e) {
                logger.error("Error generating PDF for VoluntaryHealthInsurance: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error("Error in create method: {}", e.getMessage(), e);
        }

        logger.info("Finished create method for VoluntaryHealthInsurance with username: {}", username);
    }

    @Override
    public Optional<VoluntaryHealthInsuranceDTO> read(Id id) {
        logger.info("Reading VoluntaryHealthInsurance with ID: {}", id);
        VoluntaryHealthInsurance entity = voluntaryHealthInsuranceRepository.getVoluntaryHealthInsurance(id);
        logger.info("Successfully read VoluntaryHealthInsurance with ID: {}", id);
        return Optional.of(VoluntaryHealthInsuranceMapper.toDTO(entity));
    }

    // @Override
    // public void update(Id id, VoluntaryHealthInsuranceDTO voluntaryHealthInsuranceDTO, Id documentId) {
    //     logger.info("Starting update method for VoluntaryHealthInsurance with ID: {}", id);

    //     try {
    //         VoluntaryHealthInsurance existingEntity = voluntaryHealthInsuranceRepository.getVoluntaryHealthInsurance(id);
    //         VoluntaryHealthInsurance updatedEntity = VoluntaryHealthInsuranceMapper.toEntity(voluntaryHealthInsuranceDTO);
    //         updatedEntity.setId(existingEntity.getId());

    //         Documents documents = documentsRepository.findById(documentId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
    //         updatedEntity.setDocuments(documents);

    //         voluntaryHealthInsuranceRepository.updateVoluntaryHealthInsurance(updatedEntity);

    //         Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
    //         if (pdf.isPresent()) {
    //             String s3Link = pdf.get().getS3Link();
    //             storageService.delete(s3Link);
    //             pdfRepository.deleteById(pdf.get().getId());
    //         }

    //         try {
    //             byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(voluntaryHealthInsuranceDTO);
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

    //     logger.info("Finished update method for VoluntaryHealthInsurance with ID: {}", id);
    // }


    // @Override
    // public void update(Id id, VoluntaryHealthInsuranceDTO voluntaryHealthInsuranceDTO, Id documentId) {
    //     logger.info("Starting update method for VoluntaryHealthInsurance with ID: {}", id);

    //     try {
    //         VoluntaryHealthInsurance existingEntity = voluntaryHealthInsuranceRepository.getVoluntaryHealthInsurance(id);
    //         delete(existingEntity.getId());
    //         VoluntaryHealthInsurance updatedEntity = VoluntaryHealthInsuranceMapper.toEntity(voluntaryHealthInsuranceDTO);

    //         Documents documents = documentsRepository.findById(documentId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
    //         updatedEntity.setDocuments(documents);

    //         voluntaryHealthInsuranceRepository.updateVoluntaryHealthInsurance(updatedEntity);

    //         Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
    //         if (pdf.isPresent()) {
    //             String s3Link = pdf.get().getS3Link();
    //             storageService.delete(s3Link);
    //             pdfRepository.deleteById(pdf.get().getId());
    //         }

    //         try {
    //             byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(voluntaryHealthInsuranceDTO);
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

    //     logger.info("Finished update method for VoluntaryHealthInsurance with ID: {}", id);
    // }



    @Override
    public void update(Id id, VoluntaryHealthInsuranceDTO voluntaryHealthInsuranceDTO, Id documentId) {
        logger.info("Starting update method for VoluntaryHealthInsurance with ID: {}", id);

        try {
            VoluntaryHealthInsurance existingEntity = voluntaryHealthInsuranceRepository.getVoluntaryHealthInsurance(id);
            delete(existingEntity.getId());

            VoluntaryHealthInsurance updatedEntity = VoluntaryHealthInsuranceMapper.toEntity(voluntaryHealthInsuranceDTO);
            voluntaryHealthInsuranceRepository.addVoluntaryHealthInsurance(updatedEntity);

            Documents documents = documentsRepository.findById(documentId)
                    .orElseThrow(() -> new IllegalArgumentException("Documents not found"));
            updatedEntity.setDocuments(documents);
            documents.setVoluntaryHealthInsurance(updatedEntity);
            documentsRepository.save(documents);

            Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
            if (pdf.isPresent()) {
                String s3Link = pdf.get().getS3Link();
                storageService.delete(s3Link);
                pdfRepository.deleteById(pdf.get().getId());
            }

            try {
                byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(voluntaryHealthInsuranceDTO);
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

        logger.info("Finished update method for VoluntaryHealthInsurance with ID: {}", id);
    }

    @Override
    public void delete(Id id) {
        logger.info("Starting delete method for VoluntaryHealthInsurance with ID: {}", id);

        try {
            Documents byVoluntaryHealthInsurance = documentsRepository.getByVoluntaryHealthInsurance(id);
            byVoluntaryHealthInsurance.setVoluntaryHealthInsurance(null);
            documentsRepository.save(byVoluntaryHealthInsurance);
            voluntaryHealthInsuranceRepository.deleteVoluntaryHealthInsurance(id);

            Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
            if (pdf.isPresent()) {
                String s3Link = pdf.get().getS3Link();
                storageService.delete(s3Link);
                pdfRepository.deleteById(pdf.get().getId());
            }
        } catch (Exception e) {
            logger.error("Error in delete method: {}", e.getMessage(), e);
        }

        logger.info("Finished delete method for VoluntaryHealthInsurance with ID: {}", id);
    }
}
