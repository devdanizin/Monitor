package com.devdaniel.monitor.repository;

import com.devdaniel.monitor.model.MonitorTask;
import com.devdaniel.monitor.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MonitorRepository extends JpaRepository<MonitorTask, Long> {

    List<MonitorTask> findBySiteUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM MonitorTask mt WHERE mt.site.id = :siteId")
    void deleteByMonitoredSiteId(@Param("siteId") Long siteId);

}
