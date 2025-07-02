package com.devdaniel.monitor.dto.response;

import java.time.LocalDateTime;

public record MonitorTaskResponse(
        Long id,
        String url,
        int statusCode,
        long responseTime,
        LocalDateTime checkedAt,
        MonitoredSiteResponse site
) {}