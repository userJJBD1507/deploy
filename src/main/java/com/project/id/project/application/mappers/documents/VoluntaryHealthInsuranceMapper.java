package com.project.id.project.application.mappers.documents;

import com.project.id.project.application.DTOs.documents.VoluntaryHealthInsuranceDTO;
import com.project.id.project.core.documents.entities.VoluntaryHealthInsurance;

public class VoluntaryHealthInsuranceMapper {

    public static VoluntaryHealthInsuranceDTO toDTO(VoluntaryHealthInsurance insurance) {
        VoluntaryHealthInsuranceDTO dto = new VoluntaryHealthInsuranceDTO();
        dto.setPhoto(insurance.getPhoto());
        dto.setNumber(insurance.getNumber());
        dto.setSurname(insurance.getSurname());
        dto.setName(insurance.getName());
        dto.setPatronymic(insurance.getPatronymic());
        dto.setBirthdate(insurance.getBirthdate());
        dto.setGender(insurance.getGender());
        dto.setEffectiveFrom(insurance.getEffectiveFrom());
        dto.setEffectiveUntil(insurance.getEffectiveUntil());
        dto.setPolicyHolder(insurance.getPolicyHolder());
        return dto;
    }

    public static VoluntaryHealthInsurance toEntity(VoluntaryHealthInsuranceDTO dto) {
        VoluntaryHealthInsurance entity = new VoluntaryHealthInsurance();
        entity.setPhoto(dto.getPhoto());
        entity.setNumber(dto.getNumber());
        entity.setSurname(dto.getSurname());
        entity.setName(dto.getName());
        entity.setPatronymic(dto.getPatronymic());
        entity.setBirthdate(dto.getBirthdate());
        entity.setGender(dto.getGender());
        entity.setEffectiveFrom(dto.getEffectiveFrom());
        entity.setEffectiveUntil(dto.getEffectiveUntil());
        entity.setPolicyHolder(dto.getPolicyHolder());
        return entity;
    }
}
