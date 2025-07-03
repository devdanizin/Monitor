package com.devdaniel.monitor.controller;

import com.devdaniel.monitor.model.User;
import com.devdaniel.monitor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/usuarios")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserRepository userRepo;

    @GetMapping
    public List<User> listar() {
        return userRepo.findAll();
    }

    @PostMapping
    public User criar(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("O campo username é obrigatório");
        }
        user.setPlanExpiry(LocalDateTime.now().plusSeconds(2)); //testes, depois alterar para months

        if (user.getPlan() == null) {
            user.setPlan(true);
        }

        return userRepo.save(user);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        userRepo.deleteById(id);
    }

    @DeleteMapping("/all")
    public void deletarTodos() {
        userRepo.deleteAll();
    }

    @PatchMapping("/{id}/plan")
    public User atualizarPlan(@PathVariable Long id, @RequestBody Map<String, Boolean> planRequest) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Boolean plan = planRequest.get("plan");
        if (plan == null) {
            throw new IllegalArgumentException("Campo 'plan' é obrigatório no corpo da requisição");
        }

        user.setPlan(plan);

        if (plan) {
            LocalDateTime now = LocalDateTime.now();
            if (user.getPlanExpiry() == null || user.getPlanExpiry().isBefore(now)) {
                user.setPlanExpiry(now.plusMonths(1));
            } else {
                user.setPlanExpiry(user.getPlanExpiry().plusMonths(1));
            }
        } else {
            user.setPlanExpiry(null);
        }

        return userRepo.save(user);
    }

    @PatchMapping("/{id}/renew")
    public User renovarAssinatura(@PathVariable Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (user.getPlanExpiry() == null) {
            user.setPlanExpiry(LocalDateTime.now().plusMonths(1));
        } else {
            user.setPlanExpiry(user.getPlanExpiry().plusMonths(1));
        }

        return userRepo.save(user);
    }
}
