package com.devdaniel.monitor.controller.entity;

import com.devdaniel.monitor.dto.request.UserPlanStatusDTO;
import com.devdaniel.monitor.model.User;
import com.devdaniel.monitor.repository.UserRepository;
import com.devdaniel.monitor.util.AuthUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        String username = AuthUtil.getLoggedUsername();
        User user = userRepository.findByUsername(username).orElseThrow();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/plan-status")
    public ResponseEntity<UserPlanStatusDTO> getPlanStatus() {
        String username = AuthUtil.getLoggedUsername();
        User user = userRepository.findByUsername(username).orElseThrow();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiry = user.getPlanExpiry();
        boolean expirado = expiry == null || now.isAfter(expiry);
        long diasRestantes = expirado ? 0 : ChronoUnit.DAYS.between(now, expiry);
        boolean vencendo = diasRestantes <= 5 && !expirado;

        long totalDiasPlano = ChronoUnit.DAYS.between(user.getCreatedAt(), expiry != null ? expiry : now);
        long diasUsados = totalDiasPlano - diasRestantes;
        int percentUsado = totalDiasPlano > 0 ? (int) ((diasUsados * 100.0) / totalDiasPlano) : 100;

        UserPlanStatusDTO dto = UserPlanStatusDTO.builder()
                .expirado(expirado)
                .vencendo(vencendo)
                .diasRestantes(diasRestantes)
                .percentUsado(percentUsado)
                .build();

        return ResponseEntity.ok(dto);
    }
}