package com.devdaniel.monitor.repository;

import com.devdaniel.monitor.model.MonitoredSite;
import com.devdaniel.monitor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoredRepository extends JpaRepository<MonitoredSite, Long> {

    List<MonitoredSite> findByUser(User user);

}
