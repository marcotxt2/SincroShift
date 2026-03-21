package com.sincroshift.api.controller;

import com.sincroshift.api.config.JwtUtil;
import com.sincroshift.api.dto.SolicitacaoResponseDTO;
import com.sincroshift.api.service.PlantaoService;
import com.sincroshift.api.service.SolicitacaoTrocaService;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/minhas")
    public ResponseEntity<List<SolicitacaoResponseDTO>> minhasCandidaturas(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long usuarioId = jwtUtil.extractId(token);
        List<SolicitacaoResponseDTO> candidaturas = solicitacaoTrocaService.listarMinhasCandidaturas(usuarioId);
        return ResponseEntity.ok(candidaturas);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarCandidatura(@PathVariable Long id, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long usuarioId = jwtUtil.extractId(token);
        solicitacaoTrocaService.cancelarMinhaCandidatura(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Void> aprovarCandidatura(@PathVariable Long id, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long usuarioId = jwtUtil.extractId(token);
        solicitacaoTrocaService.aprovarCandidatura(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/recusar")
    public ResponseEntity<Void> recusarCandidatura(@PathVariable Long id, HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Long usuarioId = jwtUtil.extractId(token);
        solicitacaoTrocaService.recusarCandidatura(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

}
