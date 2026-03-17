package com.sincroshift.api.dto;

import com.sincroshift.api.model.SolicitacaoTroca;
import com.sincroshift.api.model.enums.StatusSolicitacao;

import java.time.LocalDateTime;

public record SolicitacaoResponseDTO(Long id, String nomeSolicitante, LocalDateTime dataSolicitacao, StatusSolicitacao status) {
    public SolicitacaoResponseDTO(SolicitacaoTroca solicitacaoTroca) {
        this(solicitacaoTroca.getId(), solicitacaoTroca.getSolicitante().getNome(), solicitacaoTroca.getDataSolicitacao(), solicitacaoTroca.getStatus());
    }
}
