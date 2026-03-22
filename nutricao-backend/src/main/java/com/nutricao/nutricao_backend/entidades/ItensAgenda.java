package com.nutricao.nutricao_backend.entidades;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "itens_agenda")
public class ItensAgenda implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private LocalTime horario_agenda;

    public ItensAgenda(){}

    public ItensAgenda( Long id, LocalTime horario_agenda){
        this.id = id;
        this.horario_agenda = horario_agenda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getHorario_agenda() {
        return horario_agenda;
    }

    public void setHorario_agenda(LocalTime horario_agenda) {
        this.horario_agenda = horario_agenda;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ItensAgenda that = (ItensAgenda) o;
        return Objects.equals(id, that.id) && Objects.equals(horario_agenda, that.horario_agenda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, horario_agenda);
    }
}
