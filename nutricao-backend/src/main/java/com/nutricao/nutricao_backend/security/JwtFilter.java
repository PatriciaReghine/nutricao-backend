package com.nutricao.nutricao_backend.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        String method = request.getMethod();

        // ROTAS PÚBLICAS
        if (uri.equals("/usuario/login") ||
                (uri.equals("/usuario") && method.equals("POST"))) {

            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        // BLOQUEIA SE NÃO TIVER TOKEN
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = header.replace("Bearer ", "");

        try {
            Claims claims = JwtService.validarTokenCompleto(token);

            //  PEGA DADOS DO TOKEN CORRETAMENTE
            String email = claims.getSubject();
            String perfil = claims.get("perfil", String.class);

            // SALVA USUÁRIO LOGADO
            UsuarioLogado.set(email, perfil);

            //  REGRAS DE ACESSO

            //  AVALIAÇÃO → só nutricionista
            if (uri.contains("/avaliacao") && !perfil.equals("NUTRICIONISTA")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //  LIBERA SE PASSOU EM TUDO
        filterChain.doFilter(request, response);

        //  LIMPA CONTEXTO
        UsuarioLogado.clear();
    }
}