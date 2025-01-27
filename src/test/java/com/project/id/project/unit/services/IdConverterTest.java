package com.project.id.project.unit.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.project.id.project.application.services.IdConverter;
import com.project.id.project.core.Id;

import static org.junit.jupiter.api.Assertions.*;

class IdConverterTest {

    private IdConverter idConverter;

    @BeforeEach
    void setUp() {
        idConverter = new IdConverter();
    }

    @Test
    void convert_ValidUuid_ShouldReturnId() {
        // Arrange
        String validUuid = "123e4567-e89b-12d3-a456-426614174000";

        // Act
        Id result = idConverter.convert(validUuid);

        // Assert
        assertNotNull(result, "Conversion result should not be null");
        assertEquals(validUuid, result.id().toString(), "UUID should match the input string");
    }

    @Test
    void convert_InvalidUuid_ShouldThrowIllegalArgumentException() {
        // Arrange
        String invalidUuid = "invalid-uuid-string";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            idConverter.convert(invalidUuid);
        });

        assertEquals("Invalid UUID format: " + invalidUuid, exception.getMessage(), "Exception message should match expected");
    }

    @Test
    void convert_EmptyString_ShouldThrowIllegalArgumentException() {
        // Arrange
        String emptyString = "";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            idConverter.convert(emptyString);
        });

        assertEquals("Invalid UUID format: " + emptyString, exception.getMessage(), "Exception message should match expected");
    }
}
