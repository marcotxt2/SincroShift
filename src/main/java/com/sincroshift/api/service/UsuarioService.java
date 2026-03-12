package com.sincroshift.api.service;

import com.sincroshift.api.dto.UsuarioRequestDTO;
import com.sincroshift.api.dto.UsuarioResponseDTO;
import com.sincroshift.api.model.Usuario;
import com.sincroshift.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioResponseDTO salvarUsuario(UsuarioRequestDTO dto) {

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setPerfil(dto.perfil());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(usuarioSalvo);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        // Busca todos os usuários, converte cada um para DTO e retorna uma lista
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
    }
}
