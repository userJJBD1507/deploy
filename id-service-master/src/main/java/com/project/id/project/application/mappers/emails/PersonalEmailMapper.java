package com.project.id.project.application.mappers.emails;

import com.project.id.project.application.DTOs.emails.PersonalEmailDTO;
import com.project.id.project.core.emails.entities.PersonalEmail;

public class PersonalEmailMapper {

    public static PersonalEmailDTO toDTO(PersonalEmail email) {
        PersonalEmailDTO dto = new PersonalEmailDTO();
        dto.setEmail(email.getEmail());
        return dto;
    }

    public static PersonalEmail toEntity(PersonalEmailDTO dto) {
        PersonalEmail entity = new PersonalEmail();
        entity.setEmail(dto.getEmail());
        return entity;
    }
}
