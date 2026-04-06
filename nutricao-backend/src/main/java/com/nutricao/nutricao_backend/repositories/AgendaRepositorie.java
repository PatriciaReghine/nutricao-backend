package com.nutricao.nutricao_backend.repositories;

import com.nutricao.nutricao_backend.entidades.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepositorie extends JpaRepository<Agenda, Long> {
}
