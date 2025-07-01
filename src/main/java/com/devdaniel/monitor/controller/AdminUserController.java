package com.devdaniel.monitor.controller;

import com.devdaniel.monitor.model.User;
import com.devdaniel.monitor.model.UserRole;
import com.devdaniel.monitor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/usuarios")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @GetMapping
    public List<User> listar() {
        return userRepo.findAll();
    }

    @PostMapping
    public User criar(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("O campo username é obrigatório");
        }
        return userRepo.save(user);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        userRepo.deleteById(id);
    }
}
