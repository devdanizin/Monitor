package com.devdaniel.monitor.controller;

import com.devdaniel.monitor.model.MonitoredSite;
import com.devdaniel.monitor.repository.MonitoredRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sites")
@RequiredArgsConstructor
public class SiteController {

    private final MonitoredRepository repository;

    @GetMapping
    public List<MonitoredSite> getSites() {
        return repository.findAll();
    }

    @PostMapping
    public MonitoredSite createSite(@RequestBody MonitoredSite site) {
        return repository.save(site);
    }

    @DeleteMapping("/{id}")
    public void deleteSite(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
