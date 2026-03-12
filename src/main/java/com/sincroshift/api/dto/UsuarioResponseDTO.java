package com.sincroshift.api.dto;

import com.sincroshift.api.model.Usuario;
import com.sincroshift.api.model.enums.TipoPerfil;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        TipoPerfil perfil
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getPerfil());
    }
}