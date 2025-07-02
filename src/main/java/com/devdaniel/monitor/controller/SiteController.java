package com.devdaniel.monitor.controller;

import com.devdaniel.monitor.dto.request.MonitoredSiteRequest;
import com.devdaniel.monitor.dto.response.MonitoredSiteResponse;
import com.devdaniel.monitor.mapper.MonitoredSiteMapper;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sites")
@RequiredArgsConstructor
public class SiteController {

    private final UserRepository userRepository;
    private final MonitoredRepository repository;
    private final MonitoredSiteMapper mapper;

    @GetMapping
    public List<MonitoredSiteResponse> getSites() {
        User user = userRepository.findByUsername(AuthUtil.getLoggedUsername())
                .orElseThrow();

        return repository.findByUser(user).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public MonitoredSiteResponse createSite(@RequestBody MonitoredSiteRequest request) {
        User user = userRepository.findByUsername(AuthUtil.getLoggedUsername())
                .orElseThrow();

        var sites = repository.findByUser(user);
        if (sites.size() >= user.getLimited()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seu limite de sites acabou.");
        }

        MonitoredSite site = mapper.toModel(request);
        site.setUser(user);
        MonitoredSite saved = repository.save(site);

        return mapper.toResponse(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteSite(@PathVariable Long id) {
        User user = userRepository.findByUsername(AuthUtil.getLoggedUsername())
                .orElseThrow();

        MonitoredSite site = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "URL não encontrada"));

        if (!site.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode deletar essa URL");
        }

        repository.delete(site);
    }
}
