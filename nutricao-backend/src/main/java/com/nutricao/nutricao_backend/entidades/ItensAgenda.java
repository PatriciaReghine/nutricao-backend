package com.nutricao.nutricao_backend.entidades;

import com.nutricao.nutricao_backend.enums.StatusConsulta;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime horario;

    @Enumerated(EnumType.STRING)
    private StatusConsulta statusConsulta;


    @ManyToOne
    @JoinColumn(name = "id_agenda")
    private Agenda agenda;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @OneToOne(mappedBy = "itemAgenda")
    private Consulta consulta;


    public ItensAgenda() {
    }

    public ItensAgenda(Long id, LocalTime horario, StatusConsulta statusConsulta) {
        this.id = id;
        this.horario = horario;
        this.statusConsulta = statusConsulta;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public StatusConsulta getStatusConsulta() {
        return statusConsulta;
    }

    public void setStatusConsulta(StatusConsulta statusConsulta) {
        this.statusConsulta = statusConsulta;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ItensAgenda that = (ItensAgenda) o;
        return Objects.equals(id, that.id) && Objects.equals(horario, that.horario) && statusConsulta == that.statusConsulta && Objects.equals(agenda, that.agenda) && Objects.equals(paciente, that.paciente) && Objects.equals(consulta, that.consulta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, horario, statusConsulta, agenda, paciente, consulta);
    }
}
