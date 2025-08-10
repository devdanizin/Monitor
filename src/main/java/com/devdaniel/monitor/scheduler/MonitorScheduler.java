package com.devdaniel.monitor.scheduler;

import com.devdaniel.monitor.service.MonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class MonitorScheduler {

    private final MonitorService monitorService;

    //@Scheduled(fixedRate = 60000)
    @Scheduled(fixedRate = 600000)
    public void verificarUrls() {
        monitorService.checkAllRegisteredUrls();
    }

    @Scheduled(fixedRateString = "2", timeUnit = TimeUnit.DAYS)
    public void clearDb() {
        monitorService.deleteAll();
        System.out.println("Banco de dados limpo com sucesso.!");
    }
}