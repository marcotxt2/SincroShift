package com.sincroshift.api.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {
    // Chave secreta usada para assinar e verificar tokens JWT
    private final String secretKey = "sua-chave-secreta-super-segura-que-deve-ser-bem-longa";



    // Gera um token JWT com o nome de usuário e validade de 1 hora
    public String generateToken(String username, Long id) {
        return Jwts.builder()
                .setSubject(username)
                .claim("id", id) // Injetamos o ID diretamente no corpo do Token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public Long extractId(String token) {
        // Lemos a claim "id" e garantimos que é convertida para o formato Long
        return extractClaims(token).get("id", Long.class);
    }

    // Extrai as claims do token JWT (informações adicionais do token)
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .parseClaimsJws(token)
                .getBody();
    }

    // Extrai o nome de usuário do token JWT
    public String extractUsername(String token) {
        // Obtém o assunto (nome de usuário) das claims do token
        return extractClaims(token).getSubject();
    }

    // Verifica se o token JWT está expirado
    public boolean isTokenExpired(String token) {
        // Compara a data de expiração do token com a data atual
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Valida o token JWT verificando o nome de usuário e se o token não está expirado
    public boolean validateToken(String token, String username) {
        // Extrai o nome de usuário do token
        final String extractedUsername = extractUsername(token);
        // Verifica se o nome de usuário do token corresponde ao fornecido e se o token não está expirado
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
