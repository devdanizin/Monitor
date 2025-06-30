package com.devdaniel.Monitor.repository;

import com.devdaniel.Monitor.model.MonitoredSite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoredRepository extends JpaRepository<MonitoredSite, Long> {
}
