package com.nutricao.nutricao_backend.repositories;

import com.nutricao.nutricao_backend.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepositorie extends JpaRepository<Paciente,Long> {
}
