package com.sincroshift.api.infra;

import com.sincroshift.api.dto.ErroResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResponseDTO> tratarErrosDeRegraDeNegocio(RuntimeException ex) {
        ErroResponseDTO erro = new ErroResponseDTO(ex.getMessage());
        return ResponseEntity.badRequest().body(erro);
    }
}
