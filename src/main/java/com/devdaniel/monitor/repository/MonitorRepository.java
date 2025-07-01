package com.devdaniel.monitor.repository;

import com.devdaniel.monitor.model.MonitorTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitorRepository extends JpaRepository<MonitorTask, Long> {
}
