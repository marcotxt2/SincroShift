package com.sincroshift.api.repository;

import com.sincroshift.api.model.SolicitacaoTroca;
import com.sincroshift.api.model.enums.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoTrocaRepository extends JpaRepository<SolicitacaoTroca,Long> {
    List<SolicitacaoTroca> findByStatus(StatusSolicitacao status);
    List<SolicitacaoTroca> findBySolicitanteId(Long solicitanteId);
}
