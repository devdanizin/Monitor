package com.devdaniel.monitor.repository;

import com.devdaniel.monitor.model.MonitorTask;
import com.devdaniel.monitor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitorRepository extends JpaRepository<MonitorTask, Long> {

    List<MonitorTask> findBySiteUser(User user);

}
