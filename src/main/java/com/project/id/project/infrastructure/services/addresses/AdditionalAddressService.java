package com.project.id.project.infrastructure.services.addresses;

import com.project.id.project.application.DTOs.address.AdditionalAddressDTO;
import com.project.id.project.application.mappers.addresses.AdditionalAddressMapper;
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
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaAdditionalAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//public class AdditionalAddressService implements CrudUpdated<AdditionalAddressDTO, Id> {
//
//    @Autowired
//    private JpaAdditionalAddressRepository additionalAddressRepository;
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
//    public void create(String username, AdditionalAddressDTO dto) {
//        AdditionalAddress entity = AdditionalAddressMapper.toEntity(dto);
//
//
//        PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).get();
//        Id id = personalData.getAddresses().getId();
//        Addresses addresses = addressesRepository.findById(id).get();
//        additionalAddressRepository.addAdditionalAddress(entity);
//        addresses.getAdditionalAddress().add(entity);
//        entity.setAddresses(addresses);
//
//
//        additionalAddressRepository.addAdditionalAddress(entity);
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
//    public Optional<AdditionalAddressDTO> read(Id id) {
//        AdditionalAddress entity = additionalAddressRepository.getAdditionalAddress(id);
//        return Optional.of(AdditionalAddressMapper.toDTO(entity));
//    }
//
//    @Override
//    public void update(Id id, AdditionalAddressDTO dto, Id addressesId) {
//        AdditionalAddress existingEntity = additionalAddressRepository.getAdditionalAddress(id);
//        AdditionalAddress updatedEntity = AdditionalAddressMapper.toEntity(dto);
//        updatedEntity.setId(existingEntity.getId());
//
//        Addresses addresses = addressesRepository.findById(addressesId)
//                .orElseThrow(() -> new IllegalArgumentException("Addresses not found"));
//        updatedEntity.setAddresses(addresses);
//
//        additionalAddressRepository.updateAdditionalAddress(updatedEntity);
//
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
//        additionalAddressRepository.deleteAdditionalAddress(id);
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

@Service
public class AdditionalAddressService implements CrudUpdated<AdditionalAddressDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(AdditionalAddressService.class);

    @Autowired
    private JpaAdditionalAddressRepository additionalAddressRepository;

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
    // public void create(String username, AdditionalAddressDTO dto) {
    //     logger.info("Starting create method for username: {}", username);

    //     try {
    //         AdditionalAddress entity = AdditionalAddressMapper.toEntity(dto);

    //         PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).get();
    //         Id id = personalData.getAddresses().getId();
    //         Addresses addresses = addressesRepository.findById(id).get();
    //         additionalAddressRepository.addAdditionalAddress(entity);
    //         addresses.getAdditionalAddress().add(entity);
    //         entity.setAddresses(addresses);

    //         additionalAddressRepository.addAdditionalAddress(entity);

    //         try {
    //             byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(dto);
    //             String s3Link = storageService.upload(bytesOfDto);
    //             Pdf pdf = new Pdf();
    //             pdf.setResourceId(entity.getId().toString());
    //             pdf.setS3Link(s3Link);
    //             pdfRepository.save(pdf);
    //         } catch (Exception e) {
    //             logger.error("Error generating PDF for additional address: {}", e.getMessage(), e);
    //         }
    //     } catch (Exception e) {
    //         logger.error("Error in create method: {}", e.getMessage(), e);
    //     }

    //     logger.info("Finished create method for username: {}", username);
    // }
    @Override
    public void create(String username, AdditionalAddressDTO dto) {
        logger.info("Starting create method for username: {}", username);

        try {
            AdditionalAddress entity = AdditionalAddressMapper.toEntity(dto);
            PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).get();
            Id id = personalData.getAddresses().getId();
            Addresses addresses = addressesRepository.findById(id).get();
            addresses.getAdditionalAddress().add(entity);
            entity.setAddresses(addresses);
            additionalAddressRepository.addAdditionalAddress(entity);

            try {
                byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(dto);
                String s3Link = storageService.upload(bytesOfDto);
                Pdf pdf = new Pdf();
                pdf.setResourceId(entity.getId().toString());
                pdf.setS3Link(s3Link);
                pdfRepository.save(pdf);
            } catch (Exception e) {
                logger.error("Error generating PDF for additional address: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error("Error in create method: {}", e.getMessage(), e);
        }

        logger.info("Finished create method for username: {}", username);
    }
    @Override
    public Optional<AdditionalAddressDTO> read(Id id) {
        logger.info("Reading AdditionalAddress with ID: {}", id);
        AdditionalAddress entity = additionalAddressRepository.getAdditionalAddress(id);
        logger.info("Successfully read AdditionalAddress with ID: {}", id);
        return Optional.of(AdditionalAddressMapper.toDTO(entity));
    }

    // @Override
    // public void update(Id id, AdditionalAddressDTO dto, Id addressesId) {
    //     logger.info("Starting update method for AdditionalAddress with ID: {}", id);

    //     try {
    //         AdditionalAddress existingEntity = additionalAddressRepository.getAdditionalAddress(id);
    //         AdditionalAddress updatedEntity = AdditionalAddressMapper.toEntity(dto);
    //         updatedEntity.setId(existingEntity.getId());

    //         Addresses addresses = addressesRepository.findById(addressesId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Addresses not found"));
    //         updatedEntity.setAddresses(addresses);

    //         additionalAddressRepository.updateAdditionalAddress(updatedEntity);

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

    //     logger.info("Finished update method for AdditionalAddress with ID: {}", id);
    // }


    @Override
    public void update(Id id, AdditionalAddressDTO dto, Id addressesId) {
        logger.info("Starting update method for AdditionalAddress with ID: {}", id);

        try {
            AdditionalAddress existingEntity = additionalAddressRepository.getAdditionalAddress(id);
            delete(existingEntity.getId());
            AdditionalAddress updatedEntity = AdditionalAddressMapper.toEntity(dto);

            Addresses addresses = addressesRepository.findById(addressesId)
                    .orElseThrow(() -> new IllegalArgumentException("Addresses not found"));
            updatedEntity.setAddresses(addresses);

            additionalAddressRepository.updateAdditionalAddress(updatedEntity);

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

        logger.info("Finished update method for AdditionalAddress with ID: {}", id);
    }
    @Override
    public void delete(Id id) {
        logger.info("Starting delete method for AdditionalAddress with ID: {}", id);

        try {
            additionalAddressRepository.deleteAdditionalAddress(id);
            Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
            if (pdf.isPresent()) {
                String s3Link = pdf.get().getS3Link();
                storageService.delete(s3Link);
                pdfRepository.deleteById(pdf.get().getId());
            }
        } catch (Exception e) {
            logger.error("Error in delete method: {}", e.getMessage(), e);
        }

        logger.info("Finished delete method for AdditionalAddress with ID: {}", id);
    }
}
