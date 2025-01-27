package com.project.id.project.infrastructure.services.addresses;

import com.project.id.project.application.DTOs.address.WorkAddressDTO;
import com.project.id.project.application.mappers.addresses.WorkAddressMapper;
import com.project.id.project.application.services.CrudService;
import com.project.id.project.application.services.Security.NewCrudSer;
import com.project.id.project.application.services.linkers.AddressesRepository;
import com.project.id.project.application.services.pdf.PdfGenerator;
import com.project.id.project.application.services.pdf.handlers.CrudUpdated;
import com.project.id.project.application.services.pdf.handlers.Pdf;
import com.project.id.project.application.services.pdf.handlers.PdfRepository;
import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.addresses.entities.WorkAddress;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaWorkAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//@Service
//public class WorkAddressService implements CrudUpdated<WorkAddressDTO, Id> {
//
//    @Autowired
//    private JpaWorkAddressRepository workAddressRepository;
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
//    private AddressesRepository addressesRepository;
//    @Override
//    public void create(String username, WorkAddressDTO dto) {
//        WorkAddress entity = WorkAddressMapper.toEntity(dto);
//
//
//        PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).get();
//        Id id = personalData.getAddresses().getId();
//        Addresses addresses = addressesRepository.findById(id).get();
//        workAddressRepository.addWorkAddress(entity);
//        addresses.setWorkAddress(entity);
//        entity.setAddresses(addresses);
//
//
//        workAddressRepository.addWorkAddress(entity);
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
//    public Optional<WorkAddressDTO> read(Id id) {
//        WorkAddress entity = workAddressRepository.getWorkAddress(id);
//        return Optional.of(WorkAddressMapper.toDTO(entity));
//    }
//
//    @Override
//    public void update(Id id, WorkAddressDTO dto, Id addressesId) {
//        WorkAddress existingEntity = workAddressRepository.getWorkAddress(id);
//
//        WorkAddress updatedEntity = WorkAddressMapper.toEntity(dto);
//        updatedEntity.setId(existingEntity.getId());
//
//        Addresses addresses = addressesRepository.findById(addressesId)
//                .orElseThrow(() -> new IllegalArgumentException("Addresses not found"));
//        updatedEntity.setAddresses(addresses);
//
//        workAddressRepository.updateWorkAddress(updatedEntity);
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
//    @Transactional
//    public void delete(Id id) {
//        workAddressRepository.deleteWorkAddress(id);
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkAddressService implements CrudUpdated<WorkAddressDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(WorkAddressService.class);

    @Autowired
    private JpaWorkAddressRepository workAddressRepository;

    @Autowired
    private PdfGenerator pdfGenerator;
    @Autowired
    private StorageService storageService;
    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Autowired
    private AddressesRepository addressesRepository;

    // @Override
    // public void create(String username, WorkAddressDTO dto) {
    //     logger.info("Starting create method for WorkAddress with username: {}", username);

    //     try {
    //         WorkAddress entity = WorkAddressMapper.toEntity(dto);

    //         PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).get();
    //         Id id = personalData.getAddresses().getId();
    //         Addresses addresses = addressesRepository.findById(id).get();
    //         workAddressRepository.addWorkAddress(entity);
    //         addresses.setWorkAddress(entity);
    //         entity.setAddresses(addresses);

    //         workAddressRepository.addWorkAddress(entity);

    //         try {
    //             byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(dto);
    //             String s3Link = storageService.upload(bytesOfDto);
    //             Pdf pdf = new Pdf();
    //             pdf.setResourceId(entity.getId().toString());
    //             pdf.setS3Link(s3Link);
    //             pdfRepository.save(pdf);
    //         } catch (Exception e) {
    //             logger.error("Error generating PDF for WorkAddress: {}", e.getMessage(), e);
    //         }
    //     } catch (Exception e) {
    //         logger.error("Error in create method: {}", e.getMessage(), e);
    //     }

    //     logger.info("Finished create method for WorkAddress with username: {}", username);
    // }
    @Override
    public void create(String username, WorkAddressDTO dto) {
        logger.info("Starting create method for WorkAddress with username: {}", username);

        try {
            WorkAddress entity = WorkAddressMapper.toEntity(dto);

            PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).get();
            Id id = personalData.getAddresses().getId();
            Addresses addresses = addressesRepository.findById(id).get();
            entity.setAddresses(addresses);
            workAddressRepository.addWorkAddress(entity);
            addresses.setWorkAddress(entity);
            entity.setAddresses(addresses);

            workAddressRepository.addWorkAddress(entity);

            try {
                byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(dto);
                String s3Link = storageService.upload(bytesOfDto);
                Pdf pdf = new Pdf();
                pdf.setResourceId(entity.getId().toString());
                pdf.setS3Link(s3Link);
                pdfRepository.save(pdf);
            } catch (Exception e) {
                logger.error("Error generating PDF for WorkAddress: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error("Error in create method: {}", e.getMessage(), e);
        }

        logger.info("Finished create method for WorkAddress with username: {}", username);
    }
    @Override
    public Optional<WorkAddressDTO> read(Id id) {
        logger.info("Reading WorkAddress with ID: {}", id);
        WorkAddress entity = workAddressRepository.getWorkAddress(id);
        logger.info("Successfully read WorkAddress with ID: {}", id);
        return Optional.of(WorkAddressMapper.toDTO(entity));
    }

    // @Override
    // public void update(Id id, WorkAddressDTO dto, Id addressesId) {
    //     logger.info("Starting update method for WorkAddress with ID: {}", id);

    //     try {
    //         WorkAddress existingEntity = workAddressRepository.getWorkAddress(id);
    //         WorkAddress updatedEntity = WorkAddressMapper.toEntity(dto);
    //         updatedEntity.setId(existingEntity.getId());

    //         Addresses addresses = addressesRepository.findById(addressesId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Addresses not found"));
    //         updatedEntity.setAddresses(addresses);

    //         workAddressRepository.updateWorkAddress(updatedEntity);

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

    //     logger.info("Finished update method for WorkAddress with ID: {}", id);
    // }


    @Override
    public void update(Id id, WorkAddressDTO dto, Id addressesId) {
        logger.info("Starting update method for WorkAddress with ID: {}", id);

        try {
            WorkAddress existingEntity = workAddressRepository.getWorkAddress(id);
            delete(existingEntity.getId());
            WorkAddress updatedEntity = WorkAddressMapper.toEntity(dto);

            Addresses addresses = addressesRepository.findById(addressesId)
                    .orElseThrow(() -> new IllegalArgumentException("Addresses not found"));
            updatedEntity.setAddresses(addresses);

            workAddressRepository.updateWorkAddress(updatedEntity);

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

        logger.info("Finished update method for WorkAddress with ID: {}", id);
    }
    @Override
    @Transactional
    public void delete(Id id) {
        logger.info("Starting delete method for WorkAddress with ID: {}", id);

        try {
            workAddressRepository.deleteWorkAddress(id);
            Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
            if (pdf.isPresent()) {
                String s3Link = pdf.get().getS3Link();
                storageService.delete(s3Link);
                pdfRepository.deleteById(pdf.get().getId());
            }
        } catch (Exception e) {
            logger.error("Error in delete method: {}", e.getMessage(), e);
        }

        logger.info("Finished delete method for WorkAddress with ID: {}", id);
    }
}
