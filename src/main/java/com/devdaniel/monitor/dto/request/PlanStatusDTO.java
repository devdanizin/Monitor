package com.devdaniel.monitor.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlanStatusDTO {
    private boolean expirado;
    private boolean vencendo;
    private int diasRestantes;
}