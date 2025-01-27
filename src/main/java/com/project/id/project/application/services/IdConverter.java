package com.project.id.project.application.services;

import com.project.id.project.core.Id;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdConverter implements Converter<String, Id> {
    @Override
    public Id convert(String source) {
        try {
            return new Id(UUID.fromString(source));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format: " + source, e);
        }
    }
}
