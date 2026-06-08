package com.nutricao.nutricao_backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        System.out.println("===== JWT FILTER =====");
        System.out.println("URL: " + request.getRequestURI());
        System.out.println("METHOD: " + request.getMethod());
        System.out.println("HEADER: " + header);

        try {
            if (header != null && header.startsWith("Bearer ")) {

                String token = header.substring(7);

                System.out.println("TOKEN: " + token);

                boolean valido = JwtService.validarToken(token);
                System.out.println("VALIDO? " + valido);

                if (valido) {

                    String email = JwtService.getEmail(token);
                    String perfil = JwtService.getPerfil(token);

                    System.out.println("EMAIL: " + email);
                    System.out.println("PERFIL: " + perfil);

                    SimpleGrantedAuthority authority =
                            new SimpleGrantedAuthority("ROLE_" + perfil);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    List.of(authority)
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    System.out.println("AUTH SETADA: " + authentication);

                } else {
                    System.out.println("TOKEN INVÁLIDO");
                }

            } else {
                System.out.println("SEM HEADER OU FORMATO ERRADO");
            }

        } catch (Exception e) {
            System.out.println("ERRO NO JWT: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}