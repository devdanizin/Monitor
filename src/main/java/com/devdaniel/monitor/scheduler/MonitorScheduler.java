package com.devdaniel.monitor.scheduler;

import com.devdaniel.monitor.service.MonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonitorScheduler {

    private final MonitorService monitorService;

    //@Scheduled(fixedRate = 60000)
    @Scheduled(fixedRate = 600000)
    public void verificarUrls() {
        monitorService.checkAllRegisteredUrls();
    }
}
