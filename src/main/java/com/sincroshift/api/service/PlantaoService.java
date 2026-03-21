package com.sincroshift.api.service;

import com.sincroshift.api.dto.PlantaoRequestDTO;
import com.sincroshift.api.dto.PlantaoResponseDTO;
import com.sincroshift.api.model.Plantao;
import com.sincroshift.api.model.Usuario;
import com.sincroshift.api.model.enums.StatusPlantao;
import com.sincroshift.api.repository.PlantaoRepository;
import com.sincroshift.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
public class PlantaoService {
    @Autowired
    private PlantaoRepository plantaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public PlantaoResponseDTO cadastrarPlantao(PlantaoRequestDTO dto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        Plantao plantao = new Plantao();
        plantao.setDataHoraInicio(dto.dataHoraInicio());
        plantao.setDataHoraFim(dto.dataHoraFim());
        plantao.setSetor(dto.setor());
        plantao.setStatus(StatusPlantao.DISPONIVEL);
        plantao.setCriador(usuario);
        Plantao plantaosalvo = plantaoRepository.save(plantao);
        return new PlantaoResponseDTO(plantaosalvo);
    }

    public List<PlantaoResponseDTO> listarDisponiveis() {
        List<Plantao> plantoesDisponiveis = plantaoRepository.findByStatus(StatusPlantao.DISPONIVEL);
        return plantoesDisponiveis.stream().map(PlantaoResponseDTO::new).toList();
    }

    public List<PlantaoResponseDTO> listarMeusPlantoes(Long usuarioId) {
        List<Plantao> plantoesUsuario = plantaoRepository.findByUsuarioAtualId(usuarioId);
        return plantoesUsuario.stream().map(PlantaoResponseDTO::new).toList();
    }

    public void cancelarPlantao(Long plantaoId, Long usuarioId) {
        Plantao plantao = plantaoRepository.findById(plantaoId).orElseThrow();
        if (Objects.equals(plantao.getStatus(), StatusPlantao.OCUPADO)) {
            throw new RuntimeException("Alguém já se planejou para este plantão.");
        }
        if (!usuarioId.equals(plantao.getCriador().getId())) {
            throw new RuntimeException("Apenas o dono do plantão pode cancelar ele.");
        }
        plantao.setStatus(StatusPlantao.CANCELADO);
        plantaoRepository.save(plantao);
    }

    public Page<PlantaoResponseDTO> listarMural(Long usuarioId, Pageable pageable) {
        Page<Plantao> plantoesDisponiveis = plantaoRepository.findByStatusAndCriadorIdNot(
                StatusPlantao.DISPONIVEL,
                usuarioId,
                pageable);
        return plantoesDisponiveis.map(PlantaoResponseDTO::new);
    }
}
