package com.sincroshift.api.controller;

import com.sincroshift.api.dto.PlantaoRequestDTO;
import com.sincroshift.api.dto.PlantaoResponseDTO;
import com.sincroshift.api.dto.SolicitacaoResponseDTO;
import com.sincroshift.api.model.SolicitacaoTroca;
import com.sincroshift.api.service.PlantaoService;
import com.sincroshift.api.service.SolicitacaoTrocaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/plantoes")
public class PlantaoController {
    @Autowired
    private PlantaoService plantaoService;

    @Autowired
    private SolicitacaoTrocaService solicitacaoTrocaService;

    @PostMapping
    public ResponseEntity<PlantaoResponseDTO> cadastrar(@RequestBody PlantaoRequestDTO dto, Principal principal){
        PlantaoResponseDTO plantaoSalvo = plantaoService.cadastrarPlantao(dto, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(plantaoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<PlantaoResponseDTO>> listarPlantoes(){
        List<PlantaoResponseDTO> lista = plantaoService.listarDisponiveis();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/meus")
    public ResponseEntity<List<PlantaoResponseDTO>> meusPlantoes(Principal principal){
        List<PlantaoResponseDTO> lista = plantaoService.listarMeusPlantoes(principal.getName());
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/{id}/candidatar")
    public ResponseEntity<SolicitacaoResponseDTO> solicitarPlantao(@PathVariable Long id, Principal principal){
        SolicitacaoResponseDTO solicitante =  solicitacaoTrocaService.candidatar(id, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitante);
    }

    @GetMapping("/{id}/candidatos")
    public ResponseEntity<List<SolicitacaoResponseDTO>> listarSolicitacoes(@PathVariable Long id){
        List<SolicitacaoResponseDTO> solicitacoes = solicitacaoTrocaService.listarCandidatos(id);
        return ResponseEntity.ok(solicitacoes);
    }

    @PutMapping("/solicitacoes/{id}/aprovar")
    public ResponseEntity<Void> aprovarCandidatura(@PathVariable Long id){
        solicitacaoTrocaService.aprovarCandidatura(id);
        return ResponseEntity.noContent().build();
    }
}
