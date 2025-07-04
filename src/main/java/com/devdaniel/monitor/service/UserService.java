package com.devdaniel.monitor.service;

import com.devdaniel.monitor.dto.request.PlanStatusDTO;
import com.devdaniel.monitor.exception.NotFoundException;
import com.devdaniel.monitor.model.User;
import com.devdaniel.monitor.repository.UserRepository;
import com.devdaniel.monitor.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + username));
    }

    public PlanStatusDTO getPlanStatusForCurrentUser() {
        String username = AuthUtil.getLoggedUsername();
        User user = findByUsername(username);

        if (user.getPlanExpiry() == null) {
            return new PlanStatusDTO(true, false, 0);
        }

        LocalDateTime now = LocalDateTime.now();
        long dias = ChronoUnit.DAYS.between(now, user.getPlanExpiry());
        boolean expirado = user.getPlanExpiry().isBefore(now);
        boolean vencendo = !expirado && dias <= 5;

        return new PlanStatusDTO(expirado, vencendo, (int) Math.max(0, dias));
    }

}
