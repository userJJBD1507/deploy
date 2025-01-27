package com.project.id.project.application.mappers.documents;

import com.project.id.project.application.DTOs.documents.TaxPayerIdentificationNumberDTO;
import com.project.id.project.core.documents.entities.TaxPayerIdentificationNumber;

public class TaxPayerIdentificationNumberMapper {

    public static TaxPayerIdentificationNumberDTO toDTO(TaxPayerIdentificationNumber taxPayerIdentificationNumber) {
        TaxPayerIdentificationNumberDTO dto = new TaxPayerIdentificationNumberDTO();
        dto.setPhoto(taxPayerIdentificationNumber.getPhoto());
        dto.setSurname(taxPayerIdentificationNumber.getSurname());
        dto.setName(taxPayerIdentificationNumber.getName());
        dto.setPatronymic(taxPayerIdentificationNumber.getPatronymic());
        dto.setBirthdate(taxPayerIdentificationNumber.getBirthdate());
        dto.setGender(taxPayerIdentificationNumber.getGender());
        dto.setTaxpayerIdentificationNumber(taxPayerIdentificationNumber.getTaxpayerIdentificationNumber());
        dto.setIssueDate(taxPayerIdentificationNumber.getIssueDate());
        dto.setBirthPlace(taxPayerIdentificationNumber.getBirthPlace());
        dto.setEffectiveUntil(taxPayerIdentificationNumber.getEffectiveUntil());
        dto.setIssuingAuthority(taxPayerIdentificationNumber.getIssuingAuthority());
        return dto;
    }

    public static TaxPayerIdentificationNumber toEntity(TaxPayerIdentificationNumberDTO dto) {
        TaxPayerIdentificationNumber entity = new TaxPayerIdentificationNumber();
        entity.setPhoto(dto.getPhoto());
        entity.setSurname(dto.getSurname());
        entity.setName(dto.getName());
        entity.setPatronymic(dto.getPatronymic());
        entity.setBirthdate(dto.getBirthdate());
        entity.setGender(dto.getGender());
        entity.setTaxpayerIdentificationNumber(dto.getTaxpayerIdentificationNumber());
        entity.setIssueDate(dto.getIssueDate());
        entity.setBirthPlace(dto.getBirthPlace());
        entity.setEffectiveUntil(dto.getEffectiveUntil());
        entity.setIssuingAuthority(dto.getIssuingAuthority());
        return entity;
    }
}
