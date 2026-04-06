package com.nutricao.nutricao_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtService {
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(
            "minha-chave-super-secreta-1234567890".getBytes()
    );

    public static String gerarToken(String email, String perfil) {
        return Jwts.builder()
                .setSubject(email)
                .claim("perfil", perfil) // 🔥 NOVO
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SECRET_KEY)
                .compact();
    }
    public static String validarToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // retorna email
    }
    public static Claims validarTokenCompleto(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
