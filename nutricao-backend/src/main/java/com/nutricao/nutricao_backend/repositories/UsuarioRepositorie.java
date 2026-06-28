package com.nutricao.nutricao_backend.repositories;

import com.nutricao.nutricao_backend.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositorie extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByPerfil_NomePerfil(String nomePerfil);

    List<Usuario> findByNomeContainingIgnoreCaseOrEmailContainingIgnoreCase(String nome, String email);

    List<Usuario> findByAtivo(Boolean ativo);

    List<Usuario> findAllByOrderByIdDesc();

    List<Usuario> findByPerfil_NomePerfilOrderByNomeAsc(
            String nomePerfil
    );
    List<Usuario> findByPerfil_NomePerfilAndAtivoTrueOrderByNomeAsc(
            String nomePerfil
    );
    Long countByAtivoTrue();

}
