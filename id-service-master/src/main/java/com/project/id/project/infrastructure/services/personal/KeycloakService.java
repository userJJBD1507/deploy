package com.project.id.project.infrastructure.services.personal;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.id.project.application.DTOs.personal.PersonalDataDTO;


@Service
public class KeycloakService {

    private static final String REALM_NAME = "eselpo";

    @Autowired
    private Keycloak keycloak;
    Logger logger = LoggerFactory.getLogger(KeycloakService.class);

    public String getUserIdByUsername(String username) {
        List<UserRepresentation> users = keycloak.realm(REALM_NAME)
            .users()
            .searchByUsername(username, true);

        if (users.isEmpty()) {
            logger.error("User with username '{}' not found", username);
            return null;
        }
        return users.get(0).getId();
    }
    // public void updateKeycloakUser(String userId, UserRepresentation userRepresentation) {
    //     try {
    //         keycloak.realm(REALM_NAME)
    //             .users()
    //             .get(userId)
    //             .update(userRepresentation);
    //         logger.info("Successfully updated Keycloak user with ID: {}", userId);
    //     } catch (Exception e) {
    //         logger.error("Error updating Keycloak user with ID: {}", userId, e);
    //     }
    // }
    public void updateKeycloakUser(String userId, PersonalDataDTO entity) {
        try {
            UsersResource usersResource = keycloak.realm(REALM_NAME).users();
            if (usersResource == null) {
                return;
            }
            UserRepresentation user = usersResource.get(userId).toRepresentation();
            if (user == null) {
                return;
            }
            user.setUsername(entity.getInvocation());
            user.setFirstName(entity.getName());
            user.setLastName(entity.getSurname());
            System.out.println("user " + user.toString());
            usersResource.get(userId).update(user);
            logger.info("Successfully updated Keycloak user with ID: {}", userId);
        } catch (Exception e) {
            logger.error("Error updating Keycloak user with ID: {}", userId, e);
        }
    }
    public void deleteKeycloakUser(String userId) {
        try {
            keycloak.realm(REALM_NAME)
                .users()
                .get(userId)
                .remove();
            logger.info("Successfully deleted Keycloak user with ID: {}", userId);
        } catch (Exception e) {
            logger.error("Error deleting Keycloak user with ID: {}", userId, e);
        }
    }
}

