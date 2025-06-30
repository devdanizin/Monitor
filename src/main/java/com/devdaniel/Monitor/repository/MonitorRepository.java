package com.devdaniel.Monitor.repository;

import com.devdaniel.Monitor.model.MonitorTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitorRepository extends JpaRepository<MonitorTask, Long> {
}
