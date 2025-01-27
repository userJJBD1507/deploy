package com.project.id.project.infrastructure.services.addresses;

import com.project.id.project.application.DTOs.address.HomeAddressDTO;
import com.project.id.project.application.mappers.addresses.HomeAddressMapper;
import com.project.id.project.application.services.CrudService;
import com.project.id.project.application.services.Security.NewCrudSer;
import com.project.id.project.application.services.linkers.AddressesRepository;
import com.project.id.project.application.services.pdf.PdfGenerator;
import com.project.id.project.application.services.pdf.handlers.CrudUpdated;
import com.project.id.project.application.services.pdf.handlers.Pdf;
import com.project.id.project.application.services.pdf.handlers.PdfRepository;
import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.HomeAddress;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaHomeAddressRepository;
import com.project.id.project.infrastructure.services.personal.PersonalDataService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//@Service
//public class HomeAddressService implements CrudUpdated<HomeAddressDTO, Id> {
//
//    @Autowired
//    private JpaHomeAddressRepository homeAddressRepository;
//
//    @Autowired
//    private PdfGenerator pdfGenerator;
//    @Autowired
//    private StorageService storageService;
//    @Autowired
//    private PdfRepository pdfRepository;
//
//
//    @Autowired
//    private EntityPersonalDataRepository entityPersonalDataRepository;
//    @Autowired
//    private AddressesRepository addressesRepository;
//    @Override
//    public void create(String username, HomeAddressDTO dto) {
//        HomeAddress entity = HomeAddressMapper.toEntity(dto);
//
//        PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).get();
//        Id id = personalData.getAddresses().getId();
//        Addresses addresses = addressesRepository.findById(id).get();
//        homeAddressRepository.addHomeAddress(entity);
//        addresses.setHomeAddress(entity);
//        entity.setAddresses(addresses);
//
//        homeAddressRepository.addHomeAddress(entity);
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
//    public Optional<HomeAddressDTO> read(Id id) {
//        HomeAddress entity = homeAddressRepository.getHomeAddress(id);
//        return Optional.of(HomeAddressMapper.toDTO(entity));
//    }
//
//    @Override
//    public void update(Id id, HomeAddressDTO dto, Id addressesId) {
//        HomeAddress existingEntity = homeAddressRepository.getHomeAddress(id);
//
//        HomeAddress updatedEntity = HomeAddressMapper.toEntity(dto);
//        updatedEntity.setId(existingEntity.getId());
//
//        Addresses addresses = addressesRepository.findById(addressesId)
//                .orElseThrow(() -> new IllegalArgumentException("Addresses not found"));
//        updatedEntity.setAddresses(addresses);
//
//        homeAddressRepository.updateHomeAddress(updatedEntity);
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
//        homeAddressRepository.deleteHomeAddress(id);
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
public class HomeAddressService implements CrudUpdated<HomeAddressDTO, Id> {

    private static final Logger logger = LoggerFactory.getLogger(HomeAddressService.class);

    @Autowired
    private JpaHomeAddressRepository homeAddressRepository;

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

    @Override
    public void create(String username, HomeAddressDTO dto) {
        logger.info("Starting create method for username: {}", username);

        try {
            HomeAddress entity = HomeAddressMapper.toEntity(dto);

            PersonalData personalData = entityPersonalDataRepository.findByInvocation(username).get();
            Id id = personalData.getAddresses().getId();
            Addresses addresses = addressesRepository.findById(id).get();
            entity.setAddresses(addresses);
            homeAddressRepository.addHomeAddress(entity);
            addresses.setHomeAddress(entity);
            entity.setAddresses(addresses);

            homeAddressRepository.addHomeAddress(entity);

            try {
                byte[] bytesOfDto = pdfGenerator.generatePdfFromDto(dto);
                String s3Link = storageService.upload(bytesOfDto);
                Pdf pdf = new Pdf();
                pdf.setResourceId(entity.getId().toString());
                pdf.setS3Link(s3Link);
                pdfRepository.save(pdf);
            } catch (Exception e) {
                logger.error("Error generating PDF for home address: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error("Error in create method: {}", e.getMessage(), e);
        }

        logger.info("Finished create method for username: {}", username);
    }

    @Override
    public Optional<HomeAddressDTO> read(Id id) {
        logger.info("Reading HomeAddress with ID: {}", id);
        HomeAddress entity = homeAddressRepository.getHomeAddress(id);
        logger.info("Successfully read HomeAddress with ID: {}", id);
        return Optional.of(HomeAddressMapper.toDTO(entity));
    }
    
    // @Override
    // public void update(Id id, HomeAddressDTO dto, Id addressesId) {
    //     logger.info("Starting update method for HomeAddress with ID: {}", id);

    //     try {
    //         HomeAddress existingEntity = homeAddressRepository.getHomeAddress(id);
    //         System.out.println("ENTITY " + existingEntity);
    //         HomeAddress updatedEntity = HomeAddressMapper.toEntity(dto);
    //         updatedEntity.setId(existingEntity.getId());

    //         Addresses addresses = addressesRepository.findById(addressesId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Addresses not found"));
    //         updatedEntity.setAddresses(addresses);
    //         homeAddressRepository.updateHomeAddress(updatedEntity);

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

    //     logger.info("Finished update method for HomeAddress with ID: {}", id);
    // }
    

    @Override
    public void update(Id id, HomeAddressDTO dto, Id addressesId) {
        logger.info("Starting update method for HomeAddress with ID: {}", id);

        try {
            HomeAddress existingEntity = homeAddressRepository.getHomeAddress(id);
            System.out.println("ENTITY " + existingEntity);
            delete(existingEntity.getId());
            HomeAddress updatedEntity = HomeAddressMapper.toEntity(dto);

            Addresses addresses = addressesRepository.findById(addressesId)
                    .orElseThrow(() -> new IllegalArgumentException("Addresses not found"));
            updatedEntity.setAddresses(addresses);
            homeAddressRepository.updateHomeAddress(updatedEntity);

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

        logger.info("Finished update method for HomeAddress with ID: {}", id);
    }
    @Override
    public void delete(Id id) {
        logger.info("Starting delete method for HomeAddress with ID: {}", id);

        try {
            homeAddressRepository.deleteHomeAddress(id);
            Optional<Pdf> pdf = pdfRepository.findByResourceId(id.toString());
            if (pdf.isPresent()) {
                String s3Link = pdf.get().getS3Link();
                storageService.delete(s3Link);
                pdfRepository.deleteById(pdf.get().getId());
            }
        } catch (Exception e) {
            logger.error("Error in delete method: {}", e.getMessage(), e);
        }

        logger.info("Finished delete method for HomeAddress with ID: {}", id);
    }
}
