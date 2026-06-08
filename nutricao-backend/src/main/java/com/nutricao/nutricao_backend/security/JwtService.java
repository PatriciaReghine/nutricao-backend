package com.nutricao.nutricao_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import com.nutricao.nutricao_backend.entidades.Usuario;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtService {

    private static final String SECRET_KEY = "12345678901234567890123456789012"; // mínimo 32 chars
    private static final long EXPIRATION = 1000 * 60 * 60; // 1 hora

    // 🔐 GERA CHAVE SEGURA
    private static Key getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 🚀 GERAR TOKEN COM ID + PERFIL
    public static String gerarToken(Usuario usuario) {

        Map<String, Object> claims = new HashMap<>();

        // 🔥 adiciona dados no token
        claims.put("perfil", usuario.getPerfil().getNomePerfil());
        claims.put("id", usuario.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getKey())
                .compact();
    }

    // 🔍 VALIDAR TOKEN
    public static boolean validarToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 📧 EXTRAIR EMAIL
    public static String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    // 🔐 EXTRAIR PERFIL
    public static String getPerfil(String token) {
        return getClaims(token).get("perfil", String.class);
    }

    // 🆔 EXTRAIR ID
    public static Long getIdFromToken(String token) {
        return ((Number) getClaims(token).get("id")).longValue();
    }

    // 🔧 MÉTODO INTERNO PARA LER CLAIMS
    private static Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}