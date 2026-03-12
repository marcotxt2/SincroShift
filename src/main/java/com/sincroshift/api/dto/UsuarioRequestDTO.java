package com.sincroshift.api.dto;

import com.sincroshift.api.model.enums.TipoPerfil;

public record UsuarioRequestDTO(
        String nome,
        String email,
        String senha,
        TipoPerfil perfil
) {}