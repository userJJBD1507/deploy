package com.project.id.project.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import com.project.id.project.application.services.enc.EncryptionUtil;
import com.project.id.project.application.services.enc.Hashes;
import com.project.id.project.application.services.enc.HashesRepository;
import com.project.id.project.application.services.s3storage.StorageService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class EncryptionUtilTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private EncryptionUtil encryptionUtil;

    private static final String VALUE_TO_ENCRYPT = "SensitiveData";
    private static final String ENCRYPTED_VALUE = "IV:EncryptedData";
    private static final String COOKIE_KEY = "flh";
    private static final String COOKIE_VALUE = "TestKeyData";

    @BeforeEach
    void setUp() {
        Cookie cookie = new Cookie(COOKIE_KEY, COOKIE_VALUE);
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
    }

    @Test
    void encrypt_ShouldReturnEncryptedString() throws Exception {
        // Arrange
        String valueToEncrypt = VALUE_TO_ENCRYPT;

        // Act
        String encryptedValue = encryptionUtil.encrypt(valueToEncrypt);

        // Assert
        assertNotNull(encryptedValue);
        String[] parts = encryptedValue.split(":");
        assertEquals(2, parts.length);
        assertFalse(parts[0].isEmpty());
        assertFalse(parts[1].isEmpty());
    }

    @Test
    void decrypt_ShouldReturnOriginalValue() throws Exception {
        // Arrange
        String encryptedValue = encryptionUtil.encrypt(VALUE_TO_ENCRYPT);
    
        // Act
        String decryptedValue = encryptionUtil.decrypt(encryptedValue);
    
        // Assert
        assertNotNull(decryptedValue);
        assertEquals(VALUE_TO_ENCRYPT, decryptedValue);
    }
    

    @Test
    void encrypt_ShouldReturnNull_WhenExceptionOccurs() {
        // Arrange
        when(request.getCookies()).thenThrow(new RuntimeException("Test exception"));

        // Act
        String encryptedValue = encryptionUtil.encrypt(VALUE_TO_ENCRYPT);

        // Assert
        assertNull(encryptedValue);
    }

    @Test
    void decrypt_ShouldReturnNull_WhenExceptionOccurs() {
        // Arrange
        String encryptedValue = ENCRYPTED_VALUE;
        when(request.getCookies()).thenThrow(new RuntimeException("Test exception"));

        // Act
        String decryptedValue = encryptionUtil.decrypt(encryptedValue);

        // Assert
        assertNull(decryptedValue);
    }
}
