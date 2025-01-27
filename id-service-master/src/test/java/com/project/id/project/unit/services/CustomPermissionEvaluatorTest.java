package com.project.id.project.unit.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.project.id.project.application.services.Security.CustomPermissionEvaluator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomPermissionEvaluatorTest {

    private CustomPermissionEvaluator permissionEvaluator;

    @BeforeEach
    void setUp() {
        permissionEvaluator = new CustomPermissionEvaluator();
    }

    @Test
    void hasPermission_AdminRoleWithAdminPermission_ShouldReturnTrue() {
        // Arrange
        GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("admin", null, List.of(adminAuthority));

        // Act
        boolean result = permissionEvaluator.hasPermission(authentication, "admin_section", "Admin Permission");

        // Assert
        assertTrue(result, "Admin with 'Admin Permission' for 'admin_section' should have access");
    }

    @Test
    void hasPermission_UserRoleWithUserPermission_ShouldReturnTrue() {
        // Arrange
        GrantedAuthority userAuthority = new SimpleGrantedAuthority("ROLE_USER");
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("user", null, List.of(userAuthority));

        // Act
        boolean result = permissionEvaluator.hasPermission(authentication, "user_section", "User Permission");

        // Assert
        assertTrue(result, "User with 'User Permission' for 'user_section' should have access");
    }

    @Test
    void hasPermission_AdminRoleWithUserPermission_ShouldReturnFalse() {
        // Arrange
        GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("admin", null, List.of(adminAuthority));

        // Act
        boolean result = permissionEvaluator.hasPermission(authentication, "user_section", "User Permission");

        // Assert
        assertFalse(result, "Admin should not have 'User Permission' for 'user_section'");
    }

    @Test
    void hasPermission_InvalidPermission_ShouldReturnFalse() {
        // Arrange
        GrantedAuthority userAuthority = new SimpleGrantedAuthority("ROLE_USER");
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("user", null, List.of(userAuthority));

        // Act
        boolean result = permissionEvaluator.hasPermission(authentication, "user_section", "Invalid Permission");

        // Assert
        assertFalse(result, "User with 'Invalid Permission' should not have access");
    }

    @Test
    void hasPermission_InvalidTargetDomainObject_ShouldReturnFalse() {
        // Arrange
        GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("admin", null, List.of(adminAuthority));

        // Act
        boolean result = permissionEvaluator.hasPermission(authentication, 12345, "Admin Permission");

        // Assert
        assertFalse(result, "Invalid target domain object should not have access");
    }
}
