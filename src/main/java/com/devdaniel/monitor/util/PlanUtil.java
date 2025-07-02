package com.devdaniel.monitor.util;

import com.devdaniel.monitor.model.User;
import com.devdaniel.monitor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlanUtil {

    private static UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository repo) {
        PlanUtil.userRepository = repo;
    }

    public static boolean isActive(User user) {
        return userRepository.findByUsername(user.getUsername())
                .map(u -> Boolean.TRUE.equals(u.getPlan()))
                .orElse(false);
    }
}