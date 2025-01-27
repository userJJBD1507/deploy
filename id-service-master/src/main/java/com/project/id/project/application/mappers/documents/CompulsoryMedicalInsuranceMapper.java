package com.project.id.project.application.mappers.documents;

import com.project.id.project.application.DTOs.documents.CompulsoryMedicalInsuranceDTO;
import com.project.id.project.core.documents.entities.CompulsoryMedicalInsurance;

public class CompulsoryMedicalInsuranceMapper {

    public static CompulsoryMedicalInsuranceDTO toDTO(CompulsoryMedicalInsurance compulsoryMedicalInsurance) {
        CompulsoryMedicalInsuranceDTO compulsoryMedicalInsuranceDTO = new CompulsoryMedicalInsuranceDTO();
        compulsoryMedicalInsuranceDTO.setPhoto(compulsoryMedicalInsurance.getPhoto());
        compulsoryMedicalInsuranceDTO.setSurname(compulsoryMedicalInsurance.getSurname());
        compulsoryMedicalInsuranceDTO.setName(compulsoryMedicalInsurance.getName());
        compulsoryMedicalInsuranceDTO.setPatronymic(compulsoryMedicalInsurance.getPatronymic());
        compulsoryMedicalInsuranceDTO.setBirthdate(compulsoryMedicalInsurance.getBirthdate());
        compulsoryMedicalInsuranceDTO.setGender(compulsoryMedicalInsurance.getGender());
        compulsoryMedicalInsuranceDTO.setSeriesAndNumber(compulsoryMedicalInsurance.getSeriesAndNumber());
        compulsoryMedicalInsuranceDTO.setSeriesAndFormNumber(compulsoryMedicalInsurance.getSeriesAndFormNumber());
        return compulsoryMedicalInsuranceDTO;
    }

    public static CompulsoryMedicalInsurance toEntity(CompulsoryMedicalInsuranceDTO compulsoryMedicalInsuranceDTO) {
        CompulsoryMedicalInsurance compulsoryMedicalInsurance = new CompulsoryMedicalInsurance();
        compulsoryMedicalInsurance.setPhoto(compulsoryMedicalInsuranceDTO.getPhoto());
        compulsoryMedicalInsurance.setSurname(compulsoryMedicalInsuranceDTO.getSurname());
        compulsoryMedicalInsurance.setName(compulsoryMedicalInsuranceDTO.getName());
        compulsoryMedicalInsurance.setPatronymic(compulsoryMedicalInsuranceDTO.getPatronymic());
        compulsoryMedicalInsurance.setBirthdate(compulsoryMedicalInsuranceDTO.getBirthdate());
        compulsoryMedicalInsurance.setGender(compulsoryMedicalInsuranceDTO.getGender());
        compulsoryMedicalInsurance.setSeriesAndNumber(compulsoryMedicalInsuranceDTO.getSeriesAndNumber());
        compulsoryMedicalInsurance.setSeriesAndFormNumber(compulsoryMedicalInsuranceDTO.getSeriesAndFormNumber());
        return compulsoryMedicalInsurance;
    }
}
