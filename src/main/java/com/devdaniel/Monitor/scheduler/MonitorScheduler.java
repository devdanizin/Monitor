package com.devdaniel.Monitor.scheduler;

import com.devdaniel.Monitor.service.MonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonitorScheduler {

    private final MonitorService monitorService;

    @Scheduled(fixedRate = 300000) // a cada 5 minutos
    public void verificarUrls() {
        monitorService.checkAllRegisteredUrls();
    }
}
