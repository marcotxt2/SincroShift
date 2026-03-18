package com.sincroshift.api.controller;

import com.sincroshift.api.dto.SolicitacaoResponseDTO;
import com.sincroshift.api.service.PlantaoService;
import com.sincroshift.api.service.SolicitacaoTrocaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitacaoController {
    @Autowired
    private PlantaoService plantaoService;

    @Autowired
    private SolicitacaoTrocaService solicitacaoTrocaService;

    @GetMapping("/minhas")
    public ResponseEntity<List<SolicitacaoResponseDTO>> minhasCandidaturas(Principal principal) {
        List<SolicitacaoResponseDTO> candidaturas = solicitacaoTrocaService.listarMinhasCandidaturas(principal.getName());
        return ResponseEntity.ok(candidaturas);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarCandidatura(@PathVariable Long id, Principal principal) {
        solicitacaoTrocaService.cancelarMinhaCandidatura(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Void> aprovarCandidatura(@PathVariable Long id, Principal principal){
        solicitacaoTrocaService.aprovarCandidatura(id,  principal.getName());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/recusar")
    public ResponseEntity<Void> recusarCandidatura(@PathVariable Long id, Principal principal){
        solicitacaoTrocaService.recusarCandidatura(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

}
