package com.devdaniel.monitor.security;

import com.devdaniel.monitor.model.User;
import com.devdaniel.monitor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + name));

        if (user.getRole() == null) {
            throw new UsernameNotFoundException("Usuário sem papel (role) definido: " + name);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}