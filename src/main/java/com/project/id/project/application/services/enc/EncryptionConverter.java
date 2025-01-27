package com.project.id.project.application.services.enc;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


// @Converter
// public class EncryptionConverter implements AttributeConverter<String, String> {
//     @Autowired
//     private EncryptionUtil encryptionUtil;
//     @Override
//     public String convertToDatabaseColumn(String s) {
//         return encryptionUtil.encrypt(s);
//     }
//     @Override
//     public String convertToEntityAttribute(String s) {
//         return encryptionUtil.decrypt(s);
//     }
// }


@Converter
public class EncryptionConverter implements AttributeConverter<String, String> {
    private final EncryptionUtil encryptionUtil;

    public EncryptionConverter(@Lazy EncryptionUtil encryptionUtil) {
        this.encryptionUtil = encryptionUtil;
    }

    @Override
    public String convertToDatabaseColumn(String s) {
        System.out.println("11111111111111111111111111111");
        return encryptionUtil.encrypt(s);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        System.out.println("11111111111111111111111111111");
        return encryptionUtil.decrypt(s);
    }
}
