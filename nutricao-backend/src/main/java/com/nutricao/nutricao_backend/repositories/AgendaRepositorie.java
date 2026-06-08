package com.nutricao.nutricao_backend.repositories;

import com.nutricao.nutricao_backend.entidades.Agenda;
import com.nutricao.nutricao_backend.enums.StatusAgenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AgendaRepositorie extends JpaRepository<Agenda, Long> {
    Optional<Agenda> findByUsuarioIdAndData(Long usuarioId, LocalDate data);

    @Query("""
    SELECT a
    FROM Agenda a
    WHERE a.status = :status

    AND (
        :mes IS NULL
        OR MONTH(a.data) = :mes
    )

    AND (
        :ano IS NULL
        OR YEAR(a.data) = :ano
    )

    AND (
        :nutricionistaId IS NULL
        OR a.usuario.id = :nutricionistaId
    )

    ORDER BY a.data ASC, a.usuario.nome ASC
""")
    List<Agenda> buscarAgendas(
            @Param("status") StatusAgenda status,
            @Param("mes") Integer mes,
            @Param("ano") Integer ano,
            @Param("nutricionistaId") Long nutricionistaId
    );
}
