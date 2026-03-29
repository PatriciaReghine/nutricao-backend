package com.nutricao.nutricao_backend.repositories;

import com.nutricao.nutricao_backend.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorie extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    Optional<Usuario> findByEmail(String email);
}
