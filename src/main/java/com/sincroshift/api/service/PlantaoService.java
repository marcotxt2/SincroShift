package com.sincroshift.api.service;

import com.sincroshift.api.dto.PlantaoRequestDTO;
import com.sincroshift.api.dto.PlantaoResponseDTO;
import com.sincroshift.api.model.Plantao;
import com.sincroshift.api.model.Usuario;
import com.sincroshift.api.model.enums.StatusPlantao;
import com.sincroshift.api.repository.PlantaoRepository;
import com.sincroshift.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantaoService {
    @Autowired
    private PlantaoRepository plantaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public PlantaoResponseDTO cadastrarPlantao(PlantaoRequestDTO dto, String emailUsuarioLogado) {

        Usuario usuario  = usuarioRepository.findByEmail(emailUsuarioLogado).orElseThrow();

        Plantao plantao = new Plantao();
        plantao.setDataHoraInicio(dto.dataHoraInicio());
        plantao.setDataHoraFim(dto.dataHoraFim());
        plantao.setSetor(dto.setor());
        plantao.setUsuarioAtual(usuario);
        plantao.setStatus(StatusPlantao.DISPONIVEL);

        Plantao plantaosalvo = plantaoRepository.save(plantao);
        return new PlantaoResponseDTO(plantaosalvo);

    }
}
