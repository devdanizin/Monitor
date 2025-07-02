package com.devdaniel.monitor.validator;

import com.devdaniel.monitor.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class UserValidator {

    public boolean isUserExpired(User user) {
        if (user.getCreatedAt() == null) return false;
        long months = ChronoUnit.MONTHS.between(user.getCreatedAt(), LocalDateTime.now());
        return months >= 1;
    }
}