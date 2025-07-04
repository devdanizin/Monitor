package com.devdaniel.monitor.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPlanStatusDTO {
    private boolean expirado;
    private boolean vencendo;
    private long diasRestantes;
    private int percentUsado;
}
