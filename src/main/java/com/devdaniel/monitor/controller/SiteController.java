package com.devdaniel.monitor.controller;

import com.devdaniel.monitor.model.MonitoredSite;
import com.devdaniel.monitor.model.User;
import com.devdaniel.monitor.repository.MonitoredRepository;
import com.devdaniel.monitor.repository.UserRepository;
import com.devdaniel.monitor.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/sites")
@RequiredArgsConstructor
public class SiteController {

    private final UserRepository userRepository;
    private final MonitoredRepository repository;

    @GetMapping
    public List<MonitoredSite> getSites() {
        User user = userRepository.findByUsername(AuthUtil.getLoggedUsername()).orElseThrow();
        return repository.findByUser(user);
    }

    @PostMapping
    public MonitoredSite createSite(@RequestBody MonitoredSite site) {
        User user = userRepository.findByUsername(AuthUtil.getLoggedUsername()).orElseThrow();
        List<MonitoredSite> sites = repository.findByUser(user);
        if(sites.size() < user.getLimited()) {
            site.setUser(user);
            return repository.save(site);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Seu limite de sites acabou.");
    }

    @DeleteMapping("/{id}")
    public void deleteSite(@PathVariable Long id) {
        User user = userRepository.findByUsername(AuthUtil.getLoggedUsername()).orElseThrow();

        MonitoredSite site = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "URL não encontrada"));

        if (!site.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode deletar essa URL");
        }

        repository.delete(site);
    }
}
