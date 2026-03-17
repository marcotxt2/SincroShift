package com.sincroshift.api.service;

import com.sincroshift.api.dto.SolicitacaoResponseDTO;
import com.sincroshift.api.model.Plantao;
import com.sincroshift.api.model.SolicitacaoTroca;
import com.sincroshift.api.model.Usuario;
import com.sincroshift.api.model.enums.StatusPlantao;
import com.sincroshift.api.model.enums.StatusSolicitacao;
import com.sincroshift.api.repository.PlantaoRepository;
import com.sincroshift.api.repository.SolicitacaoTrocaRepository;
import com.sincroshift.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolicitacaoTrocaService {

    @Autowired
    private SolicitacaoTrocaRepository solicitacaoTrocaRepository;

    @Autowired
    private PlantaoRepository plantaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public SolicitacaoResponseDTO candidatar(Long plantaoId, String emailColaborador) {
        Plantao plantao = plantaoRepository.findById(plantaoId).orElseThrow();
        Usuario usuario = usuarioRepository.findByEmail(emailColaborador).orElseThrow();

        if (plantao.getStatus() != StatusPlantao.DISPONIVEL) {
            throw new RuntimeException("O plantão deve estar disponível para se candidatar.");
        }

        SolicitacaoTroca solicitacao = new SolicitacaoTroca();
        solicitacao.setPlantao(plantao);
        solicitacao.setSolicitante(usuario);
        solicitacao.setStatus(StatusSolicitacao.PENDENTE);
        solicitacao.setDataSolicitacao(LocalDateTime.now());
        SolicitacaoTroca solicitacaoSalva = solicitacaoTrocaRepository.save(solicitacao);

        return new SolicitacaoResponseDTO(solicitacaoSalva);
    }

    public List<SolicitacaoResponseDTO> listarCandidatos(Long plantaoId) {
        List<SolicitacaoTroca> solicitacoes = solicitacaoTrocaRepository.findByPlantaoId(plantaoId);
        return solicitacoes.stream().map(SolicitacaoResponseDTO::new).toList();
    }

    @Transactional
    public void aprovarCandidatura(Long solicitacaoId) {
        SolicitacaoTroca solicitacao = solicitacaoTrocaRepository.findById(solicitacaoId).orElseThrow();
        if (!solicitacao.getStatus().equals(StatusSolicitacao.PENDENTE)) {
            throw new RuntimeException("Esta solicitação não está mais pendente.");
        }
        solicitacao.setStatus(StatusSolicitacao.APROVADA);
        Plantao plantao = solicitacao.getPlantao();
        plantao.setUsuarioAtual(solicitacao.getSolicitante());
        plantao.setStatus(StatusPlantao.OCUPADO);
        plantaoRepository.save(plantao);

        List<SolicitacaoTroca> concorrentes = solicitacaoTrocaRepository.findByPlantaoId(plantao.getId());
        for (SolicitacaoTroca concorrente : concorrentes) {
            if (!concorrente.getId().equals(solicitacao.getId())) {
                concorrente.setStatus(StatusSolicitacao.RECUSADA);
            }
        }
        solicitacaoTrocaRepository.saveAll(concorrentes);
    }

}
