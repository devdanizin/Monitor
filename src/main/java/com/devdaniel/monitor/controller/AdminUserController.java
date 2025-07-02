package com.devdaniel.monitor.controller;

import com.devdaniel.monitor.model.User;
import com.devdaniel.monitor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
        user.setPlanExpiry(LocalDateTime.now().plusMonths(1));

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
    public User atualizarPlan(@PathVariable Long id, @RequestParam boolean plan) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        user.setPlan(plan);
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