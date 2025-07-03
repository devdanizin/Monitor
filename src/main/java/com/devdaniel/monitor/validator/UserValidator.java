package com.devdaniel.monitor.validator;

import com.devdaniel.monitor.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class UserValidator {

    private LocalDateTime nowBrasilia() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();
    }

    public boolean isUserExpired(User user) {
        if (user.getPlanExpiry() == null) return false;
        return nowBrasilia().isAfter(user.getPlanExpiry());
    }
}
