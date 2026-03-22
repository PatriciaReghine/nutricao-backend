package com.nutricao.nutricao_backend.entidades;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "agenda")
public class Agenda implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private Date dia_semana;

    public Agenda(){}

    public Agenda( Long id, Date dia_semana){
        this.id = id;
        this.dia_semana = dia_semana;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(Date dia_semana) {
        this.dia_semana = dia_semana;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Agenda agenda = (Agenda) o;
        return Objects.equals(id, agenda.id) && Objects.equals(dia_semana, agenda.dia_semana);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dia_semana);
    }
}
