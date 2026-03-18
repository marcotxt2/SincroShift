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

import java.util.List;
import java.util.Objects;

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
        plantao.setStatus(StatusPlantao.DISPONIVEL);
        plantao.setCriador(usuario);

        Plantao plantaosalvo = plantaoRepository.save(plantao);
        return new PlantaoResponseDTO(plantaosalvo);

    }
    public List<PlantaoResponseDTO> listarDisponiveis(){
        List<Plantao> plantoesDisponiveis = plantaoRepository.findByStatus(StatusPlantao.DISPONIVEL);
        return plantoesDisponiveis.stream().map(PlantaoResponseDTO::new).toList();
    }

    public List<PlantaoResponseDTO> listarMeusPlantoes(String emailUsuarioLogado){
        Usuario usuario = usuarioRepository.findByEmail(emailUsuarioLogado).orElseThrow();
        Long idUsuarioAtual = usuario.getId();
        List<Plantao> plantoesUsuario = plantaoRepository.findByUsuarioAtualId(idUsuarioAtual);
        return plantoesUsuario.stream().map(PlantaoResponseDTO::new).toList();
    }

//    public PlantaoResponseDTO demonstrarInteresse(Long plantaoId, String emailInteressado){
//        Plantao plantao = plantaoRepository.findById(plantaoId).orElseThrow();
//        Usuario usuario = usuarioRepository.findByEmail(emailInteressado).orElseThrow();
//        if (plantao.getStatus() != StatusPlantao.DISPONIVEL){
//            throw new RuntimeException("Este plantão não está disponivel para troca.");
//        }
//        if (Objects.equals(plantao.getUsuarioAtual().getId(), usuario.getId())){
//            throw new RuntimeException("Você não pode demonstrar interesse no própio plantão");
//        }
//        plantao.setStatus(StatusPlantao.EM_NEGOCIACAO);
//        plantao.setUsuarioInteressado(usuario);
//        Plantao plantaoAtualizado = plantaoRepository.save(plantao);
//        return new PlantaoResponseDTO(plantaoAtualizado);
//    }
}
