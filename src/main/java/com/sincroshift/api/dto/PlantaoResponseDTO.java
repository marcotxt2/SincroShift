package com.sincroshift.api.dto;

import com.sincroshift.api.model.Plantao;
import com.sincroshift.api.model.enums.StatusPlantao;

import java.time.LocalDateTime;

public record PlantaoResponseDTO(Long id, String nomeUsuario, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim,
                                 String setor, StatusPlantao status) {
    public PlantaoResponseDTO(Plantao plantao) {
        this(plantao.getId(), plantao.getUsuarioAtual().getNome(), plantao.getDataHoraInicio(), plantao.getDataHoraFim(),
                plantao.getSetor(), plantao.getStatus());
    }
}
