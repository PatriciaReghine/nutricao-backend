package com.nutricao.nutricao_backend.repositories;

import com.nutricao.nutricao_backend.entidades.ItensAgenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ItensAgendaRepositorie extends JpaRepository<ItensAgenda,Long> {
    boolean existsByAgendaIdAndHorario(Long agendaId, LocalTime horario);

    List<ItensAgenda> findByAgendaIdOrderByHorarioAsc(Long agendaId);
    Optional<ItensAgenda> findByAgendaIdAndHorario(Long agendaId, LocalTime horario);

    List<ItensAgenda> findByAgendaUsuarioIdAndAgendaData(
            Long usuarioId,
            LocalDate data
    );

    List<ItensAgenda> findByAgendaUsuarioIdAndAgendaDataAndHorario(
            Long usuarioId,
            LocalDate data,
            LocalTime horario
    );
}
