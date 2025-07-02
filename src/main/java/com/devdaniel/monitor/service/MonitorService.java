package com.devdaniel.monitor.service;

import com.devdaniel.monitor.mapper.MonitorTaskMapper;
import com.devdaniel.monitor.model.MonitorTask;
import com.devdaniel.monitor.model.MonitoredSite;
import com.devdaniel.monitor.model.User;
import com.devdaniel.monitor.repository.MonitorRepository;
import com.devdaniel.monitor.repository.MonitoredRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitorService {

    private final MonitorRepository monitorRepository;
    private final MonitoredRepository siteRepository;
    private final AlertService alertService;
    private final MonitorTaskMapper taskMapper;

    @Transactional
    public void checkAllRegisteredUrls() {
        List<MonitoredSite> sites = siteRepository.findAll();
        System.out.println("⏱ Verificando " + sites.size() + " sites...");

        for (MonitoredSite site : sites) {
            String url = site.getUrl();
            MonitorTask resultado = verificarUrl(url);
            resultado.setSite(site);
            monitorRepository.save(resultado);

            boolean falhou = (resultado.getStatusCode() == 0 || resultado.getResponseTime() > 3000);

            if (falhou) {
                site.setFalhasConsecutivas(site.getFalhasConsecutivas() + 1);

                if (site.getFalhasConsecutivas() >= 5) {
                    User dono = site.getUser();
                    String mensagem = "🔴 Atenção: o site " + url + " está com problemas há 5 tentativas consecutivas.";
                    alertService.sendAlertToUser(dono, mensagem);
                    site.setFalhasConsecutivas(0);
                }
            } else {
                site.setFalhasConsecutivas(0);
            }

            siteRepository.save(site);
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
