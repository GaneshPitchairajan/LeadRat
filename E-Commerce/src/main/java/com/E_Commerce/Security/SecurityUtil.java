
package com.E_Commerce.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

public final class SecurityUtil {

    private SecurityUtil() {
        // utility class
    }

    /**
     * Get currently logged-in username
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }

        return principal.toString();
    }

    /**
     * Check if current user has a role
     */
    public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null) {
            return false;
        }

        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals(role));
    }

    /**
     * ADMIN check
     */
    public static boolean isAdmin() {
        return hasRole("ROLE_ADMIN");
    }

    /**
     * CUSTOMER check
     */
    public static boolean isCustomer() {
        return hasRole("ROLE_CUSTOMER");
    }
}
