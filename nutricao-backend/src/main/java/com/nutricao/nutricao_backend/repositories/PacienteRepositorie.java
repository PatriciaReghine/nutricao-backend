package com.nutricao.nutricao_backend.repositories;

import com.nutricao.nutricao_backend.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepositorie extends JpaRepository<Paciente,Long> {
    List<Paciente> findAllByOrderByDataCadastroDesc();
    List<Paciente> findByNomeContainingIgnoreCaseOrCpfContaining(String nome, String cpf);

    List<Paciente> findByNomeContainingIgnoreCaseOrCpfContainingOrderByDataCadastroDesc(String nome, String cpf);
    List<Paciente> findByNomeContainingIgnoreCase(String nome);
}
