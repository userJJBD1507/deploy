package com.project.id.project.application.services.Security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import java.io.Serializable;
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject instanceof String && permission instanceof String) {
            String target = (String) targetDomainObject;
            String perm = (String) permission;
            if ("admin_section".equals(target) && "Admin Permission".equals(perm)) {
                return authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            }
            if ("user_section".equals(target) && "User Permission".equals(perm)) {
                return authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}