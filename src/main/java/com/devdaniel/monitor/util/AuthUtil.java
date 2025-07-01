package com.devdaniel.monitor.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUtil {
    public static String getLoggedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails details) {
            return details.getUsername();
        } else {
            return principal.toString();
        }
    }
}