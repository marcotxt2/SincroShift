package com.sincroshift.api.model;

import com.sincroshift.api.model.enums.StatusSolicitacao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_solicitacoes_troca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SolicitacaoTroca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plantao_id", nullable = false)
    private Plantao plantao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Usuario solicitante; // O colega que clicou em "Tenho Interesse"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSolicitacao status; // Enum: PENDENTE, APROVADA, RECUSADA

    @Column(nullable = false)
    private LocalDateTime dataSolicitacao;
}
