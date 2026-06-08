package com.nutricao.nutricao_backend.repositories;

import com.nutricao.nutricao_backend.entidades.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProntuarioRepositorie extends JpaRepository<Prontuario,Long> {
    Optional<Prontuario> findByPacienteId(Long idPaciente);
}
