package com.sincroshift.api.controller;

import com.sincroshift.api.dto.PlantaoRequestDTO;
import com.sincroshift.api.dto.PlantaoResponseDTO;
import com.sincroshift.api.service.PlantaoService;
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
}
