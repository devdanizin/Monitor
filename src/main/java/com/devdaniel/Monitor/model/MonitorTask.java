package com.devdaniel.Monitor.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class MonitorTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String url;

    @NonNull
    private int statusCode;

    @NonNull
    private long responseTime;

    @NonNull
    private LocalDateTime checkedAt;
}
