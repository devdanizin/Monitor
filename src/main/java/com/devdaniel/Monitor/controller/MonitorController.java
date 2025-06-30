package com.devdaniel.Monitor.controller;

import com.devdaniel.Monitor.model.MonitorTask;
import com.devdaniel.Monitor.repository.MonitorRepository;
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
