package com.nutricao.nutricao_backend.repositories;

import com.nutricao.nutricao_backend.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorie extends JpaRepository<Usuario, Long> {
}
