package com.sincroshift.api.dto;

import com.sincroshift.api.model.Usuario;
import com.sincroshift.api.model.enums.StatusPlantao;

import java.time.LocalDateTime;

public record PlantaoResponseDTO(Long id, Usuario usuarioAtual, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, String setor, StatusPlantao status) {
}
