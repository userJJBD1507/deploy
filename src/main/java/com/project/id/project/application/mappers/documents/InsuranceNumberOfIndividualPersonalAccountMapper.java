package com.project.id.project.application.mappers.documents;

import com.project.id.project.application.DTOs.documents.InsuranceNumberOfIndividualPersonalAccountDTO;
import com.project.id.project.core.documents.entities.InsuranceNumberOfIndividualPersonalAccount;

public class InsuranceNumberOfIndividualPersonalAccountMapper {

    public static InsuranceNumberOfIndividualPersonalAccountDTO toDTO(InsuranceNumberOfIndividualPersonalAccount insurance) {
        InsuranceNumberOfIndividualPersonalAccountDTO dto = new InsuranceNumberOfIndividualPersonalAccountDTO();
        dto.setPhoto(insurance.getPhoto());
        dto.setSurname(insurance.getSurname());
        dto.setName(insurance.getName());
        dto.setPatronymic(insurance.getPatronymic());
        dto.setBirthdate(insurance.getBirthdate());
        dto.setGender(insurance.getGender());
        dto.setRegistrationDate(insurance.getRegistrationDate());
        dto.setBirthPlace(insurance.getBirthPlace());
        dto.setInsuranceNumberOfIndividualPersonalAccount(insurance.getInsuranceNumberOfIndividualPersonalAccount());
        return dto;
    }

    public static InsuranceNumberOfIndividualPersonalAccount toEntity(InsuranceNumberOfIndividualPersonalAccountDTO dto) {
        InsuranceNumberOfIndividualPersonalAccount entity = new InsuranceNumberOfIndividualPersonalAccount();
        entity.setPhoto(dto.getPhoto());
        entity.setSurname(dto.getSurname());
        entity.setName(dto.getName());
        entity.setPatronymic(dto.getPatronymic());
        entity.setBirthdate(dto.getBirthdate());
        entity.setGender(dto.getGender());
        entity.setRegistrationDate(dto.getRegistrationDate());
        entity.setBirthPlace(dto.getBirthPlace());
        entity.setInsuranceNumberOfIndividualPersonalAccount(dto.getInsuranceNumberOfIndividualPersonalAccount());
        return entity;
    }
}
