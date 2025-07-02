package com.devdaniel.monitor.security;

import com.devdaniel.monitor.exception.AccountPlanException;
import com.devdaniel.monitor.model.User;
import com.devdaniel.monitor.repository.UserRepository;
import com.devdaniel.monitor.util.PlanUtil;
import com.devdaniel.monitor.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;
    private final UserValidator userValidator;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + name));

        if(!PlanUtil.isActive(user)) {
            throw new AccountPlanException("Seu plano está desativado. Faça contato com o suporte.");
        }
        if (user.getRole() == null) {
            throw new UsernameNotFoundException("Usuário sem papel (role) definido: " + name);
        }
        if(userValidator.isUserExpired(user)) {
            throw new AccountPlanException("Plano expirado. Faça a renovação com o suporte.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}