package com.nutricao.nutricao_backend.repositories;

import com.nutricao.nutricao_backend.entidades.Perfil;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepositorie extends JpaRepository<Perfil, Long> {
}
