package com.project.id.project.application.mappers.phonenumbers;

import com.project.id.project.application.DTOs.phonenumbers.PhoneNumberDTO;
import com.project.id.project.core.phones.entities.PhoneNumber;

public class PhoneNumberMapper {

    public static PhoneNumberDTO toDTO(PhoneNumber phone) {
        PhoneNumberDTO dto = new PhoneNumberDTO();
        dto.setPhoneNumber(phone.getPhoneNumber());
        return dto;
    }

    public static PhoneNumber toEntity(PhoneNumberDTO dto) {
        PhoneNumber entity = new PhoneNumber();
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }
}
