package com.project.id.project.application.mappers.emails;

import com.project.id.project.application.DTOs.emails.AdditionalEmailDTO;
import com.project.id.project.core.emails.entities.AdditionalEmail;

public class AdditionalEmailMapper {

    public static AdditionalEmailDTO toDTO(AdditionalEmail email) {
        AdditionalEmailDTO dto = new AdditionalEmailDTO();
        dto.setEmail(email.getEmail());
        return dto;
    }

    public static AdditionalEmail toEntity(AdditionalEmailDTO dto) {
        AdditionalEmail entity = new AdditionalEmail();
        entity.setEmail(dto.getEmail());
        return entity;
    }
}
