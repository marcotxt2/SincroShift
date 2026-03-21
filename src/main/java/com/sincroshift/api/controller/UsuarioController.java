package com.sincroshift.api.controller;

import com.sincroshift.api.config.JwtUtil;
import com.sincroshift.api.dto.LoginRequestDTO;
import com.sincroshift.api.dto.UsuarioRequestDTO;
import com.sincroshift.api.dto.UsuarioResponseDTO;
import com.sincroshift.api.model.Usuario;
import com.sincroshift.api.repository.UsuarioRepository;
import com.sincroshift.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(@RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO resposta = usuarioService.salvarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.senha())
        );
        Usuario usuario = usuarioRepository.findByEmail(dto.email()).orElseThrow();
        String token = jwtUtil.generateToken(authentication.getName(), usuario.getId());
        return ResponseEntity.ok(token);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }
}
