package com.nutricao.nutricao_backend.entidades;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "AvaliacaoFisica")
public class AvaliacaoFisica implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double peso;
    private Double altura;
    private Double imc;
    private Double circunferenciaAbdominal;
    private Double circunferenciaQuadril;

    @Column(columnDefinition = "TEXT")
    private String planejamentoAlimentar;
    private Double percentualGordura;

    @ManyToOne
    @JoinColumn(name = "id_prontuario")
    private Prontuario prontuario;

    @OneToOne(mappedBy = "avaliacaoFisica")
    private Consulta consulta;


    public AvaliacaoFisica() {
    }

    public AvaliacaoFisica(Long id, Double peso, Double altura, Double imc, Double circunferenciaAbdominal, Double circunferenciaQuadril, String planejamentoAlimentar, LocalDate dataFinal, LocalDate dataInicio, Double percentualGordura) {
        this.id = id;
        this.peso = peso;
        this.altura = altura;
        this.imc = imc;
        this.circunferenciaAbdominal = circunferenciaAbdominal;
        this.circunferenciaQuadril = circunferenciaQuadril;
        this.planejamentoAlimentar = planejamentoAlimentar;
        this.percentualGordura = percentualGordura;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getImc() {
        return imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }

    public Double getCircunferenciaAbdominal() {
        return circunferenciaAbdominal;
    }

    public void setCircunferenciaAbdominal(Double circunferenciaAbdominal) {
        this.circunferenciaAbdominal = circunferenciaAbdominal;
    }

    public Double getCircunferenciaQuadril() {
        return circunferenciaQuadril;
    }

    public void setCircunferenciaQuadril(Double circunferenciaQuadril) {
        this.circunferenciaQuadril = circunferenciaQuadril;
    }

    public String getPlanejamentoAlimentar() {
        return planejamentoAlimentar;
    }

    public void setPlanejamentoAlimentar(String planejamentoAlimentar) {
        this.planejamentoAlimentar = planejamentoAlimentar;
    }

    public Double getPercentualGordura() {
        return percentualGordura;
    }

    public void setPercentualGordura(Double percentualGordura) {
        this.percentualGordura = percentualGordura;
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
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
        AvaliacaoFisica that = (AvaliacaoFisica) o;
        return Objects.equals(id, that.id) && Objects.equals(peso, that.peso) && Objects.equals(altura, that.altura) && Objects.equals(imc, that.imc) && Objects.equals(circunferenciaAbdominal, that.circunferenciaAbdominal) && Objects.equals(circunferenciaQuadril, that.circunferenciaQuadril) && Objects.equals(planejamentoAlimentar, that.planejamentoAlimentar) && Objects.equals(percentualGordura, that.percentualGordura) && Objects.equals(prontuario, that.prontuario) && Objects.equals(consulta, that.consulta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, peso, altura, imc, circunferenciaAbdominal, circunferenciaQuadril, planejamentoAlimentar, percentualGordura, prontuario, consulta);
    }
}
