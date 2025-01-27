package com.project.id.project.application.mappers.documents;

import com.project.id.project.application.DTOs.documents.BirthCertificateDTO;
import com.project.id.project.core.documents.entities.BirthCertificate;

public class BirthCertificateMapper {

    public static BirthCertificateDTO toDTO(BirthCertificate birthCertificate) {
        BirthCertificateDTO birthCertificateDTO = new BirthCertificateDTO();
        birthCertificateDTO.setPhoto(birthCertificate.getPhoto());
        birthCertificateDTO.setSurname(birthCertificate.getSurname());
        birthCertificateDTO.setName(birthCertificate.getName());
        birthCertificateDTO.setPatronymic(birthCertificate.getPatronymic());
        birthCertificateDTO.setBirthdate(birthCertificate.getBirthdate());
        birthCertificateDTO.setSeriesAndNumber(birthCertificate.getSeriesAndNumber());
        birthCertificateDTO.setCitizenship(birthCertificate.getCitizenship());
        birthCertificateDTO.setBirthplace(birthCertificate.getBirthplace());
        birthCertificateDTO.setBirthRecordData(birthCertificate.getBirthRecordData());
        birthCertificateDTO.setFathersSurname(birthCertificate.getFathersSurname());
        birthCertificateDTO.setFathersName(birthCertificate.getFathersName());
        birthCertificateDTO.setFathersPatronymic(birthCertificate.getFathersPatronymic());
        birthCertificateDTO.setFathersCitizenship(birthCertificate.getFathersCitizenship());
        birthCertificateDTO.setFathersBirthdate(birthCertificate.getFathersBirthdate());
        birthCertificateDTO.setMothersSurname(birthCertificate.getMothersSurname());
        birthCertificateDTO.setMothersName(birthCertificate.getMothersName());
        birthCertificateDTO.setMothersPatronymic(birthCertificate.getMothersPatronymic());
        birthCertificateDTO.setMothersCitizenship(birthCertificate.getMothersCitizenship());
        birthCertificateDTO.setMothersBirthdate(birthCertificate.getMothersBirthdate());
        birthCertificateDTO.setStateRegistrationPlace(birthCertificate.getStateRegistrationPlace());
        birthCertificateDTO.setIssueCertificatePlace(birthCertificate.getIssueCertificatePlace());
        birthCertificateDTO.setIssueString(birthCertificate.getIssueString());
        return birthCertificateDTO;
    }

    public static BirthCertificate toEntity(BirthCertificateDTO birthCertificateDTO) {
        BirthCertificate birthCertificate = new BirthCertificate();
        birthCertificate.setPhoto(birthCertificateDTO.getPhoto());
        birthCertificate.setSurname(birthCertificateDTO.getSurname());
        birthCertificate.setName(birthCertificateDTO.getName());
        birthCertificate.setPatronymic(birthCertificateDTO.getPatronymic());
        birthCertificate.setBirthdate(birthCertificateDTO.getBirthdate());
        birthCertificate.setSeriesAndNumber(birthCertificateDTO.getSeriesAndNumber());
        birthCertificate.setCitizenship(birthCertificateDTO.getCitizenship());
        birthCertificate.setBirthplace(birthCertificateDTO.getBirthplace());
        birthCertificate.setBirthRecordData(birthCertificateDTO.getBirthRecordData());
        birthCertificate.setFathersSurname(birthCertificateDTO.getFathersSurname());
        birthCertificate.setFathersName(birthCertificateDTO.getFathersName());
        birthCertificate.setFathersPatronymic(birthCertificateDTO.getFathersPatronymic());
        birthCertificate.setFathersCitizenship(birthCertificateDTO.getFathersCitizenship());
        birthCertificate.setFathersBirthdate(birthCertificateDTO.getFathersBirthdate());
        birthCertificate.setMothersSurname(birthCertificateDTO.getMothersSurname());
        birthCertificate.setMothersName(birthCertificateDTO.getMothersName());
        birthCertificate.setMothersPatronymic(birthCertificateDTO.getMothersPatronymic());
        birthCertificate.setMothersCitizenship(birthCertificateDTO.getMothersCitizenship());
        birthCertificate.setMothersBirthdate(birthCertificateDTO.getMothersBirthdate());
        birthCertificate.setStateRegistrationPlace(birthCertificateDTO.getStateRegistrationPlace());
        birthCertificate.setIssueCertificatePlace(birthCertificateDTO.getIssueCertificatePlace());
        birthCertificate.setIssueString(birthCertificateDTO.getIssueString());
        return birthCertificate;
    }
}
