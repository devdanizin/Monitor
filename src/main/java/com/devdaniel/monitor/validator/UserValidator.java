package com.devdaniel.monitor.validator;

import com.devdaniel.monitor.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class UserValidator {

    public boolean isUserExpired(User user) {
        if (user.getPlanExpiry() == null) return false;
        return LocalDateTime.now().isAfter(user.getPlanExpiry());
    }
}