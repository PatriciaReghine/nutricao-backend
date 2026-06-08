package com.nutricao.nutricao_backend.repositories;

import com.nutricao.nutricao_backend.entidades.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface ConsultaRepositorie extends JpaRepository <Consulta, Long> {
    List<Consulta> findByPacienteId(Long pacienteId);

    List<Consulta> findByPacienteIdOrderByDataDesc(Long pacienteId);
    boolean existsByItemAgendaId(Long itemAgendaId);

    Optional<Consulta> findTopByItemAgendaPacienteIdOrderByDataDesc(
            Long pacienteId
    );

    List<Consulta> findByPacienteIdAndVisivelTrueOrderByDataDesc(
            Long pacienteId
    );

    List<Consulta> findByPacienteIdAndVisivelFalseOrderByDataDesc(
            Long pacienteId
    );

    Optional<Consulta> findTopByItemAgendaPacienteIdAndVisivelTrueOrderByDataDesc(
            Long pacienteId
    );

}
