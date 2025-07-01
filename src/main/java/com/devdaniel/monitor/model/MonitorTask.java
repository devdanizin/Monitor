package com.devdaniel.monitor.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonitorTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private int statusCode;
    private long responseTime;
    private LocalDateTime checkedAt;

    // Construtor manual (sem id)
    public MonitorTask(String url, int statusCode, long responseTime, LocalDateTime checkedAt) {
        this.url = url;
        this.statusCode = statusCode;
        this.responseTime = responseTime;
        this.checkedAt = checkedAt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private MonitoredSite site;
}