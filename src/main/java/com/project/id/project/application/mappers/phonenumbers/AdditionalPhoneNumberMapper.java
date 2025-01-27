package com.project.id.project.application.mappers.phonenumbers;

import com.project.id.project.application.DTOs.phonenumbers.AdditionalPhoneNumberDTO;
import com.project.id.project.core.phones.entities.AdditionalPhoneNumber;

public class AdditionalPhoneNumberMapper {

    public static AdditionalPhoneNumberDTO toDTO(AdditionalPhoneNumber phone) {
        AdditionalPhoneNumberDTO dto = new AdditionalPhoneNumberDTO();
        dto.setPhoneNumber(phone.getPhoneNumber());
        return dto;
    }

    public static AdditionalPhoneNumber toEntity(AdditionalPhoneNumberDTO dto) {
        AdditionalPhoneNumber entity = new AdditionalPhoneNumber();
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }
}
