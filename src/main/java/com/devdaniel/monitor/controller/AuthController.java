package com.devdaniel.monitor.controller;

import com.devdaniel.monitor.model.User;
import com.devdaniel.monitor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Erro: Usuário já existe!";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Usuário registrado com sucesso!";
    }

    @DeleteMapping("/all")
    public void deletar(@PathVariable Long id) {
        userRepository.deleteAll();
    }
}
