package com.devdaniel.monitor.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record MonitorTaskRequest(
        String url,
        int statusCode,
        long responseTime,
        LocalDateTime checkedAt,
        Long siteId
) {}