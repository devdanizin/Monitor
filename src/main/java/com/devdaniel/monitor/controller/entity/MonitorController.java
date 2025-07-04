package com.devdaniel.monitor.controller.entity;

import com.devdaniel.monitor.model.MonitorTask;
import com.devdaniel.monitor.model.User;
import com.devdaniel.monitor.repository.MonitorRepository;
import com.devdaniel.monitor.repository.UserRepository;
import com.devdaniel.monitor.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitor")
@RequiredArgsConstructor
public class MonitorController {

    private final UserRepository userRepository;
    private final MonitorRepository repository;

    @GetMapping("/todos")
    public List<MonitorTask> getAllMonitors() {
        User user = userRepository.findByUsername(AuthUtil.getLoggedUsername()).orElseThrow();
        return repository.findBySiteUser(user);
    }

    @DeleteMapping("/all")
    public void deleteAllMonitors() {
        repository.deleteAll();
    }

}
