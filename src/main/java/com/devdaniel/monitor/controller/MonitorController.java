package com.devdaniel.monitor.controller;

import com.devdaniel.monitor.model.MonitorTask;
import com.devdaniel.monitor.repository.MonitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitor")
@RequiredArgsConstructor
public class MonitorController {

    private final MonitorRepository repository;

    @GetMapping("/todos")
    public List<MonitorTask> getAllMonitors() {
        return repository.findAll();
    }
}
