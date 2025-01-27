package com.project.id.project.application.mappers.documents;

import com.project.id.project.application.DTOs.documents.ForeignPassportDTO;
import com.project.id.project.core.documents.entities.ForeignPassport;

// public class ForeignPassportMapper {

//     public static ForeignPassportDTO toDTO(ForeignPassport foreignPassport) {
//         ForeignPassportDTO foreignPassportDTO = new ForeignPassportDTO();
//         foreignPassportDTO.setPhoto(foreignPassport.getPhoto());
//         foreignPassportDTO.setSurname(foreignPassport.getSurname());
//         foreignPassportDTO.setName(foreignPassport.getName());
//         foreignPassportDTO.setPatronymic(foreignPassport.getPatronymic());
//         foreignPassportDTO.setBirthdate(foreignPassport.getBirthdate());
//         foreignPassportDTO.setGender(foreignPassport.getGender());
//         foreignPassportDTO.setNumber(foreignPassport.getNumber());
//         foreignPassportDTO.setCitizenship(foreignPassport.getCitizenship());
//         foreignPassportDTO.setBirthplace(foreignPassport.getBirthplace());
//         foreignPassportDTO.setExpirationDate(foreignPassport.getExpirationDate());
//         foreignPassportDTO.setWhoIssued(foreignPassport.getWhoIssued());
//         foreignPassportDTO.setIssueDate(foreignPassport.getIssueDate());
//         return foreignPassportDTO;
//     }

//     public static ForeignPassport toEntity(ForeignPassportDTO foreignPassportDTO) {
//         ForeignPassport foreignPassport = new ForeignPassport();
//         foreignPassport.setPhoto(foreignPassportDTO.getPhoto());
//         foreignPassport.setSurname(foreignPassportDTO.getSurname());
//         foreignPassport.setName(foreignPassportDTO.getName());
//         foreignPassport.setPatronymic(foreignPassportDTO.getPatronymic());
//         foreignPassport.setBirthdate(foreignPassportDTO.getBirthdate());
//         foreignPassport.setGender(foreignPassportDTO.getGender());
//         foreignPassport.setNumber(foreignPassportDTO.getNumber());
//         foreignPassport.setCitizenship(foreignPassportDTO.getCitizenship());
//         foreignPassport.setBirthplace(foreignPassportDTO.getBirthplace());
//         foreignPassport.setExpirationDate(foreignPassportDTO.getExpirationDate());
//         foreignPassport.setWhoIssued(foreignPassportDTO.getWhoIssued());
//         foreignPassport.setIssueDate(foreignPassportDTO.getIssueDate());
//         return foreignPassport;
//     }
// }



public class ForeignPassportMapper {

    public static ForeignPassportDTO toDTO(ForeignPassport foreignPassport) {
        ForeignPassportDTO foreignPassportDTO = new ForeignPassportDTO();
        foreignPassportDTO.setPhoto(foreignPassport.getPhoto());
        foreignPassportDTO.setSurname(foreignPassport.getSurname());
        foreignPassportDTO.setName(foreignPassport.getName());
        foreignPassportDTO.setPatronymic(foreignPassport.getPatronymic());
        foreignPassportDTO.setBirthdate(foreignPassport.getBirthdate());
        foreignPassportDTO.setGender(foreignPassport.getGender());
        foreignPassportDTO.setNumber(foreignPassport.getNumber());
        foreignPassportDTO.setCitizenship(foreignPassport.getCitizenship());
        foreignPassportDTO.setBirthplace(foreignPassport.getBirthplace());
        foreignPassportDTO.setExpirationDate(foreignPassport.getExpirationDate());
        foreignPassportDTO.setWhoIssued(foreignPassport.getWhoIssued());
        foreignPassportDTO.setIssueDate(foreignPassport.getIssueDate());

        foreignPassportDTO.setSurnameLatin(foreignPassport.getSurnameLatin());
        foreignPassportDTO.setNameLatin(foreignPassport.getNameLatin());
        foreignPassportDTO.setCitizenshipLatin(foreignPassport.getCitizenshipLatin());
        foreignPassportDTO.setBirthplaceLatin(foreignPassport.getBirthplaceLatin());

        return foreignPassportDTO;
    }

    public static ForeignPassport toEntity(ForeignPassportDTO foreignPassportDTO) {
        ForeignPassport foreignPassport = new ForeignPassport();
        foreignPassport.setPhoto(foreignPassportDTO.getPhoto());
        foreignPassport.setSurname(foreignPassportDTO.getSurname());
        foreignPassport.setName(foreignPassportDTO.getName());
        foreignPassport.setPatronymic(foreignPassportDTO.getPatronymic());
        foreignPassport.setBirthdate(foreignPassportDTO.getBirthdate());
        foreignPassport.setGender(foreignPassportDTO.getGender());
        foreignPassport.setNumber(foreignPassportDTO.getNumber());
        foreignPassport.setCitizenship(foreignPassportDTO.getCitizenship());
        foreignPassport.setBirthplace(foreignPassportDTO.getBirthplace());
        foreignPassport.setExpirationDate(foreignPassportDTO.getExpirationDate());
        foreignPassport.setWhoIssued(foreignPassportDTO.getWhoIssued());
        foreignPassport.setIssueDate(foreignPassportDTO.getIssueDate());

        foreignPassport.setSurnameLatin(foreignPassportDTO.getSurnameLatin());
        foreignPassport.setNameLatin(foreignPassportDTO.getNameLatin());
        foreignPassport.setCitizenshipLatin(foreignPassportDTO.getCitizenshipLatin());
        foreignPassport.setBirthplaceLatin(foreignPassportDTO.getBirthplaceLatin());

        return foreignPassport;
    }
}
