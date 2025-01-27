package com.project.id.project.application.mappers.documents;

import com.project.id.project.application.DTOs.documents.DrivingLicenseDTO;
import com.project.id.project.core.documents.entities.DrivingLicense;

// public class DrivingLicenseMapper {

//     public static DrivingLicenseDTO toDTO(DrivingLicense drivingLicense) {
//         DrivingLicenseDTO drivingLicenseDTO = new DrivingLicenseDTO();
//         drivingLicenseDTO.setPhoto(drivingLicense.getPhoto());
//         drivingLicenseDTO.setSurname(drivingLicense.getSurname());
//         drivingLicenseDTO.setName(drivingLicense.getName());
//         drivingLicenseDTO.setPatronymic(drivingLicense.getPatronymic());
//         drivingLicenseDTO.setBirthdate(drivingLicense.getBirthdate());
//         drivingLicenseDTO.setNumber(drivingLicense.getNumber());
//         drivingLicenseDTO.setBirthplace(drivingLicense.getBirthplace());
//         drivingLicenseDTO.setWhoIssued(drivingLicense.getWhoIssued());
//         drivingLicenseDTO.setValidUntil(drivingLicense.getValidUntil());
//         drivingLicenseDTO.setIssueDate(drivingLicense.getIssueDate());
//         drivingLicenseDTO.setPlaceOfIssue(drivingLicense.getPlaceOfIssue());
//         drivingLicenseDTO.setComments(drivingLicense.getComments());
//         drivingLicenseDTO.setCategory(drivingLicense.getCategory());
//         return drivingLicenseDTO;
//     }

//     public static DrivingLicense toEntity(DrivingLicenseDTO drivingLicenseDTO) {
//         DrivingLicense drivingLicense = new DrivingLicense();
//         drivingLicense.setPhoto(drivingLicenseDTO.getPhoto());
//         drivingLicense.setSurname(drivingLicenseDTO.getSurname());
//         drivingLicense.setName(drivingLicenseDTO.getName());
//         drivingLicense.setPatronymic(drivingLicenseDTO.getPatronymic());
//         drivingLicense.setBirthdate(drivingLicenseDTO.getBirthdate());
//         drivingLicense.setNumber(drivingLicenseDTO.getNumber());
//         drivingLicense.setBirthplace(drivingLicenseDTO.getBirthplace());
//         drivingLicense.setWhoIssued(drivingLicenseDTO.getWhoIssued());
//         drivingLicense.setValidUntil(drivingLicenseDTO.getValidUntil());
//         drivingLicense.setIssueDate(drivingLicenseDTO.getIssueDate());
//         drivingLicense.setPlaceOfIssue(drivingLicenseDTO.getPlaceOfIssue());
//         drivingLicense.setComments(drivingLicenseDTO.getComments());
//         drivingLicense.setCategory(drivingLicenseDTO.getCategory());
//         return drivingLicense;
//     }
// }




public class DrivingLicenseMapper {

    public static DrivingLicenseDTO toDTO(DrivingLicense drivingLicense) {
        DrivingLicenseDTO drivingLicenseDTO = new DrivingLicenseDTO();
        drivingLicenseDTO.setPhoto(drivingLicense.getPhoto());
        drivingLicenseDTO.setSurname(drivingLicense.getSurname());
        drivingLicenseDTO.setName(drivingLicense.getName());
        drivingLicenseDTO.setPatronymic(drivingLicense.getPatronymic());
        drivingLicenseDTO.setBirthdate(drivingLicense.getBirthdate());
        drivingLicenseDTO.setNumber(drivingLicense.getNumber());
        drivingLicenseDTO.setBirthplace(drivingLicense.getBirthplace());
        drivingLicenseDTO.setWhoIssued(drivingLicense.getWhoIssued());
        drivingLicenseDTO.setValidUntil(drivingLicense.getValidUntil());
        drivingLicenseDTO.setIssueDate(drivingLicense.getIssueDate());
        drivingLicenseDTO.setPlaceOfIssue(drivingLicense.getPlaceOfIssue());
        drivingLicenseDTO.setComments(drivingLicense.getComments());
        drivingLicenseDTO.setCategory(drivingLicense.getCategory());

        drivingLicenseDTO.setSurnameLatin(drivingLicense.getSurnameLatin());
        drivingLicenseDTO.setNameLatin(drivingLicense.getNameLatin());
        drivingLicenseDTO.setPatronymicLatin(drivingLicense.getPatronymicLatin());
        drivingLicenseDTO.setBirthplaceLatin(drivingLicense.getBirthplaceLatin());
        drivingLicenseDTO.setWhoIssuedLatin(drivingLicense.getWhoIssuedLatin());
        drivingLicenseDTO.setPlaceOfIssueLatin(drivingLicense.getPlaceOfIssueLatin());

        return drivingLicenseDTO;
    }

    public static DrivingLicense toEntity(DrivingLicenseDTO drivingLicenseDTO) {
        DrivingLicense drivingLicense = new DrivingLicense();
        drivingLicense.setPhoto(drivingLicenseDTO.getPhoto());
        drivingLicense.setSurname(drivingLicenseDTO.getSurname());
        drivingLicense.setName(drivingLicenseDTO.getName());
        drivingLicense.setPatronymic(drivingLicenseDTO.getPatronymic());
        drivingLicense.setBirthdate(drivingLicenseDTO.getBirthdate());
        drivingLicense.setNumber(drivingLicenseDTO.getNumber());
        drivingLicense.setBirthplace(drivingLicenseDTO.getBirthplace());
        drivingLicense.setWhoIssued(drivingLicenseDTO.getWhoIssued());
        drivingLicense.setValidUntil(drivingLicenseDTO.getValidUntil());
        drivingLicense.setIssueDate(drivingLicenseDTO.getIssueDate());
        drivingLicense.setPlaceOfIssue(drivingLicenseDTO.getPlaceOfIssue());
        drivingLicense.setComments(drivingLicenseDTO.getComments());
        drivingLicense.setCategory(drivingLicenseDTO.getCategory());

        drivingLicense.setSurnameLatin(drivingLicenseDTO.getSurnameLatin());
        drivingLicense.setNameLatin(drivingLicenseDTO.getNameLatin());
        drivingLicense.setPatronymicLatin(drivingLicenseDTO.getPatronymicLatin());
        drivingLicense.setBirthplaceLatin(drivingLicenseDTO.getBirthplaceLatin());
        drivingLicense.setWhoIssuedLatin(drivingLicenseDTO.getWhoIssuedLatin());
        drivingLicense.setPlaceOfIssueLatin(drivingLicenseDTO.getPlaceOfIssueLatin());

        return drivingLicense;
    }
}
