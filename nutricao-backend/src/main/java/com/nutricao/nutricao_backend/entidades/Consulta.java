package com.nutricao.nutricao_backend.entidades;

import com.nutricao.nutricao_backend.enums.TipoConsulta;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resumo;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private TipoConsulta tipoConsulta;

    @Column(nullable = false)
    private Boolean visivel = true;

    // 🔹 paciente
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    // 🔹 tipo consulta (ItensAgenda)

    // 🔹 avaliação física
    @OneToOne
    @JoinColumn(name = "avaliacao_id")
    private AvaliacaoFisica avaliacaoFisica;

    @ManyToOne
    @JoinColumn(name = "item_agenda_id")
    private ItensAgenda itemAgenda;

    public Consulta(){}

    public Consulta (Long id, String resumo, String observacoes, LocalDate data, TipoConsulta tipoConsulta,Boolean visivel){
        this.id = id;
        this.resumo = resumo;
        this.observacoes = observacoes;
        this.data = data;
        this.tipoConsulta = tipoConsulta;
        this.visivel = visivel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Boolean getVisivel() {
        return visivel;
    }

    public void setVisivel(Boolean visivel) {
        this.visivel = visivel;
    }

    public TipoConsulta getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(TipoConsulta tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public AvaliacaoFisica getAvaliacaoFisica() {
        return avaliacaoFisica;
    }

    public void setAvaliacaoFisica(AvaliacaoFisica avaliacaoFisica) {
        this.avaliacaoFisica = avaliacaoFisica;
    }

    public ItensAgenda getItemAgenda() {
        return itemAgenda;
    }

    public void setItemAgenda(ItensAgenda itemAgenda) {
        this.itemAgenda = itemAgenda;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return Objects.equals(id, consulta.id) && Objects.equals(resumo, consulta.resumo) && Objects.equals(observacoes, consulta.observacoes) && Objects.equals(data, consulta.data) && tipoConsulta == consulta.tipoConsulta && Objects.equals(visivel, consulta.visivel) && Objects.equals(paciente, consulta.paciente) && Objects.equals(avaliacaoFisica, consulta.avaliacaoFisica) && Objects.equals(itemAgenda, consulta.itemAgenda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, resumo, observacoes, data, tipoConsulta, visivel, paciente, avaliacaoFisica, itemAgenda);
    }
}