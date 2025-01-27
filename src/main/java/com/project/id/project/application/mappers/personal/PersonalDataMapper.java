package com.project.id.project.application.mappers.personal;

import com.project.id.project.application.DTOs.personal.PersonalDataDTO;
import com.project.id.project.core.personal.PersonalData;

public class PersonalDataMapper {

    public static PersonalDataDTO toDTO(PersonalData personalData) {
        PersonalDataDTO dto = new PersonalDataDTO();
        dto.setAvatar(personalData.getAvatar());
        dto.setInvocation(personalData.getInvocation());
        dto.setName(personalData.getName());
        dto.setSurname(personalData.getSurname());
        dto.setGender(personalData.getGender());
        dto.setBirthdate(personalData.getBirthdate());
        dto.setLocality(personalData.getLocality());
        dto.setTimeZone(personalData.getTimeZone());
        return dto;
    }

    public static PersonalData toEntity(PersonalDataDTO dto) {
        PersonalData entity = new PersonalData();
        entity.setAvatar(dto.getAvatar());
        entity.setInvocation(dto.getInvocation());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setGender(dto.getGender());
        entity.setBirthdate(dto.getBirthdate());
        entity.setLocality(dto.getLocality());
        entity.setTimeZone(dto.getTimeZone());
        return entity;
    }
}
