package com.devdaniel.monitor.service;

import com.devdaniel.monitor.model.MonitorTask;
import com.devdaniel.monitor.model.MonitoredSite;
import com.devdaniel.monitor.repository.MonitorRepository;
import com.devdaniel.monitor.repository.MonitoredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MonitorService {

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private MonitoredRepository siteRepository;

    @Autowired
    private AlertService alertService;

    public void checkAllRegisteredUrls() {
        List<MonitoredSite> sites = siteRepository.findAll();
        System.out.println("⏱ Verificando " + sites.size() + " sites em 5 minutos...");

        for (MonitoredSite site : sites) {
            String url = site.getUrl();
            MonitorTask resultado = verificarUrl(url);
            monitorRepository.save(resultado);

            if (resultado.getStatusCode() == 0 || resultado.getResponseTime() > 3000) {
                alertService.sendEmail("Possível problema de queda de assinatura. Favor verificar na hospedagem e na alocadora do domínio. Endereço: " + url);
            }
        }
    }

    public MonitorTask verificarUrl(String url) {
        try {
            long start = System.currentTimeMillis();
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(3000);
            connection.connect();
            int status = connection.getResponseCode();
            long duration = System.currentTimeMillis() - start;

            return new MonitorTask(url, status, duration, LocalDateTime.now());
        } catch (Exception e) {
            return new MonitorTask(url, 0, -1, LocalDateTime.now());
        }
    }
}
