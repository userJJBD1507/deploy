package com.project.id.project.unit.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.project.id.project.application.DTOs.personal.PersonalDataDTO;
import com.project.id.project.application.services.Security.CustomAuthenticationSuccessHandler;
import com.project.id.project.application.services.enc.Hashes;
import com.project.id.project.application.services.enc.HashesRepository;
import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.services.personal.PersonalDataService;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;


import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationSuccessHandlerTest {

    @Mock
    private PersonalDataService personalDataService;

    @Mock
    private EntityPersonalDataRepository entityPersonalDataRepository;

    @Mock
    private HashesRepository hashesRepository;

    @Mock
    private StorageService storageService;

    @Mock
    private jakarta.servlet.http.HttpServletRequest request;

    @Mock
    private jakarta.servlet.http.HttpServletResponse response;

    @Mock
    private OAuth2AuthenticationToken authentication;

    @Mock
    private OAuth2User oauth2User;

    @InjectMocks
    private CustomAuthenticationSuccessHandler successHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void onAuthenticationSuccess_UserAlreadyExists_ShouldSetCookieAndRedirect() throws Exception {
        // Arrange
        String username = "testuser";
        String firstName = "John";
        String lastName = "Doe";
        String hashedData = DigestUtils.sha256Hex(username);

        when(authentication.getPrincipal()).thenReturn(oauth2User);
        when(oauth2User.getAttributes()).thenReturn(Map.of(
                "preferred_username", username,
                "given_name", firstName,
                "family_name", lastName
        ));
        when(entityPersonalDataRepository.findByInvocation(username))
                .thenReturn(Optional.of(new PersonalData()));

        // Act
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert
        ArgumentCaptor<jakarta.servlet.http.Cookie> cookieCaptor = ArgumentCaptor.forClass(jakarta.servlet.http.Cookie.class);
        verify(response).addCookie(cookieCaptor.capture());
        jakarta.servlet.http.Cookie cookie = cookieCaptor.getValue();

        assertEquals("flh", cookie.getName());
        assertEquals(hashedData, cookie.getValue());
        verify(response).sendRedirect("/");
    }

    @Test
    void onAuthenticationSuccess_NewUser_ShouldCreateUserAndRedirect() throws Exception {
        // Arrange
        String username = "newuser";
        String firstName = "Alice";
        String lastName = "Smith";

        when(authentication.getPrincipal()).thenReturn(oauth2User);
        when(oauth2User.getAttributes()).thenReturn(Map.of(
                "preferred_username", username,
                "given_name", firstName,
                "family_name", lastName
        ));
        when(entityPersonalDataRepository.findByInvocation(username)).thenReturn(Optional.empty());

        // Act
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert
        ArgumentCaptor<PersonalDataDTO> dtoCaptor = ArgumentCaptor.forClass(PersonalDataDTO.class);
        verify(personalDataService).create(dtoCaptor.capture());
        PersonalDataDTO createdDTO = dtoCaptor.getValue();

        assertEquals(username, createdDTO.getInvocation());
        assertEquals(firstName, createdDTO.getName());
        assertEquals(lastName, createdDTO.getSurname());

        verify(response).sendRedirect("/");
    }
}
