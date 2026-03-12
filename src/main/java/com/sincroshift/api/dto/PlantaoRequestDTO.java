package com.sincroshift.api.dto;

import java.time.LocalDateTime;

public record PlantaoRequestDTO(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, String setor) {
}
