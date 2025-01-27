package com.project.id.project.core;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.UUID;

@Converter(autoApply = true)
public class IdConverter implements AttributeConverter<Id, String> {

    @Override
    public String convertToDatabaseColumn(Id attribute) {
        return (attribute != null) ? attribute.id().toString() : null;
    }

    @Override
    public Id convertToEntityAttribute(String dbData) {
        return (dbData != null) ? new Id(UUID.fromString(dbData)) : null;
    }
}
