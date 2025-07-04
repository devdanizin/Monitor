package com.devdaniel.monitor.dto.request;

import com.devdaniel.monitor.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public record UserDTO(
        String username,
        Boolean plan,
        LocalDateTime planExpiry
) {
    public UserDTO(Optional<User> user) {
        this(user.get().getUsername(), user.get().getPlan(), user.get().getPlanExpiry());
    }
}