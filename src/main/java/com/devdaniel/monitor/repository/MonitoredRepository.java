package com.devdaniel.monitor.repository;

import com.devdaniel.monitor.model.MonitoredSite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoredRepository extends JpaRepository<MonitoredSite, Long> {
}
