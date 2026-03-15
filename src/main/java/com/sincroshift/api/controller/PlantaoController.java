package com.sincroshift.api.controller;

import com.sincroshift.api.dto.PlantaoRequestDTO;
import com.sincroshift.api.dto.PlantaoResponseDTO;
import com.sincroshift.api.service.PlantaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
}
