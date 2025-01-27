package com.project.id.project.application.services.Security;

import com.project.id.project.application.DTOs.personal.PersonalDataDTO;
import com.project.id.project.application.mappers.personal.PersonalDataMapper;
import com.project.id.project.application.services.enc.Hashes;
import com.project.id.project.application.services.enc.HashesRepository;
import com.project.id.project.application.services.s3storage.StorageService;
import com.project.id.project.core.personal.PersonalData;
import com.project.id.project.core.repositories.EntityRepositories.EntityPersonalDataRepository;
import com.project.id.project.infrastructure.repositories.JpaPersonalDataRepository;
import com.project.id.project.infrastructure.services.personal.PersonalDataService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;
import java.util.Optional;

// @Component
// public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

//     @Autowired
//     private PersonalDataService personalDataService;

//     @Autowired
//     private EntityPersonalDataRepository entityPersonalDataRepository;

//     private PersonalDataMapper personalDataMapper = new PersonalDataMapper();

//     @Override
//     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

//         OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//         OAuth2User user = oauthToken.getPrincipal();

//         String username = (String) user.getAttributes().get("preferred_username");
//         String firstName = (String) user.getAttributes().get("given_name");
//         String lastName = (String) user.getAttributes().get("family_name");

//         Optional<PersonalData> existingUser = entityPersonalDataRepository.findByInvocation(username);

//         if (existingUser.isPresent()) {
//             System.out.println("User already exists, skipping registration: " + username);
//             response.sendRedirect("/");
//         } else {
//             PersonalDataDTO personalDataDTO = new PersonalDataDTO();
//             personalDataDTO.setName(firstName);
//             personalDataDTO.setSurname(lastName);
//             personalDataDTO.setInvocation(username);

//             personalDataService.create(personalDataDTO);

//             System.out.println("User created: " + personalDataDTO);
//             response.sendRedirect("/");
//         }
//     }
// }



// @Component
// public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

//     @Autowired
//     @Lazy
//     private PersonalDataService personalDataService;
//     @Autowired
//     @Lazy
//     private EntityPersonalDataRepository entityPersonalDataRepository;
//     @Autowired
//     @Lazy
//     private HashService hashService;
//     @Override
//     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

//         OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//         OAuth2User user = oauthToken.getPrincipal();

//         String username = (String) user.getAttributes().get("preferred_username");
//         String firstName = (String) user.getAttributes().get("given_name");
//         String lastName = (String) user.getAttributes().get("family_name");

//         Optional<PersonalData> existingUser = entityPersonalDataRepository.findByInvocation(username);

//         String hashedData = DigestUtils.sha256Hex(username);
//         if (existingUser.isPresent()) {
//             System.out.println("User already exists, skipping registration: " + username);
//             Cookie cookie = new Cookie("flh", hashedData);
//             cookie.setHttpOnly(true);
//             cookie.setPath("/");
//             cookie.setMaxAge(315360000);
//             response.addCookie(cookie);
//             String secondLevelHash = DigestUtils.sha256Hex(hashedData);
//         if (hashService.getHashByUsername(username).isEmpty()) {
//             hashService.saveHash(username, secondLevelHash);
//         }
//             response.sendRedirect("/");
//         } else {
//             PersonalDataDTO personalDataDTO = new PersonalDataDTO();
//             personalDataDTO.setName(firstName);
//             personalDataDTO.setSurname(lastName);
//             personalDataDTO.setInvocation(username);

//             personalDataService.create(personalDataDTO);
//             Cookie cookie = new Cookie("flh", hashedData);
//             cookie.setHttpOnly(true);
//             cookie.setPath("/");
//             cookie.setMaxAge(315360000);
//             response.addCookie(cookie);
//             String secondLevelHash = DigestUtils.sha256Hex(hashedData);
//             if (hashService.getHashByUsername(username) != "") {
//                 hashService.saveHash(username, secondLevelHash);
//             }
//             System.out.println("User created: " + personalDataDTO);
//             response.sendRedirect("/");
//         }
//     }
// }




@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private PersonalDataService personalDataService;
    @Autowired
    private EntityPersonalDataRepository entityPersonalDataRepository;
    @Autowired
    private HashesRepository hashesRepository;
    @Autowired
    private StorageService storageService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User user = oauthToken.getPrincipal();

        String username = (String) user.getAttributes().get("preferred_username");
        String firstName = (String) user.getAttributes().get("given_name");
        String lastName = (String) user.getAttributes().get("family_name");

        Optional<PersonalData> existingUser = entityPersonalDataRepository.findByInvocation(username);
        String hashedData = DigestUtils.sha256Hex(username);
        if (existingUser.isPresent()) {
            System.out.println("User already exists, skipping registration: " + username);
            
            Cookie cookie = new Cookie("flh", hashedData);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(315360000);
            response.addCookie(cookie);
            
            // String secondLevelHash = DigestUtils.sha256Hex(hashedData);
            // // Ensure the second level hash is only saved if it doesn't already exist
            // if (hashesRepository.getHashWithUname(username) == null) {
            //     // hashService.saveHash(username, secondLevelHash);
            //     Hashes hashes = new Hashes();
            //     hashes.setUsername(username);
            //     hashes.setHash(secondLevelHash);
            //     hashesRepository.save(hashes);
            //     storageService.uploadWithExistingFilename(hashes.getHash().getBytes(), hashes.getUsername());
            // }
            response.sendRedirect("/");
        } else {
            PersonalDataDTO personalDataDTO = new PersonalDataDTO();
            personalDataDTO.setName(firstName);
            personalDataDTO.setSurname(lastName);
            personalDataDTO.setInvocation(username);

            personalDataService.create(personalDataDTO);

            Cookie cookie = new Cookie("flh", hashedData);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(315360000);
            response.addCookie(cookie);


            System.out.println("User created: " + personalDataDTO);
            response.sendRedirect("/");
        }
    }
}