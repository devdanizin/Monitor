package com.devdaniel.monitor.scheduler;

import com.devdaniel.monitor.service.MonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonitorScheduler {

    private final MonitorService monitorService;

    @Scheduled(fixedRate = 60000)
    public void verificarUrls() {
        monitorService.checkAllRegisteredUrls();
        System.out.println("enviando...");
    }
}
