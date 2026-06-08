package com.nutricao.nutricao_backend.repositories;

import com.nutricao.nutricao_backend.entidades.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepositorie extends JpaRepository<Cidade,Long> {
    Cidade findByNomeNormalizadoAndUf(String nomeNormalizado, String uf);
    boolean existsByNomeAndUf(String nome, String uf);
}
