package com.nutricao.nutricao_backend.entidades;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "AvaliacaoFisica")
public class AvaliacaoFisica implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private DecimalFormat peso;
    private DecimalFormat altura;
    private DecimalFormat imc;
    private DecimalFormat circunferencia_abdominal;
    private DecimalFormat circunferencia_quadril;
    private String planejamento_alimentar;
    private Date data_inicio;
    private Date data_final;

    public AvaliacaoFisica(){}

    public AvaliacaoFisica( Long id, DecimalFormat peso, DecimalFormat altura, DecimalFormat imc, DecimalFormat circunferencia_abdominal, DecimalFormat circunferencia_quadril, String planejamento_alimentar, Date data_final, Date data_inicio){
        this.id = id;
        this.peso = peso;
        this.altura = altura;
        this.imc = imc;
        this.circunferencia_abdominal = circunferencia_abdominal;
        this.circunferencia_quadril = circunferencia_quadril;
        this.planejamento_alimentar = planejamento_alimentar;
        this.data_final = data_final;
        this.data_inicio = data_inicio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DecimalFormat getPeso() {
        return peso;
    }

    public void setPeso(DecimalFormat peso) {
        this.peso = peso;
    }

    public DecimalFormat getAltura() {
        return altura;
    }

    public void setAltura(DecimalFormat altura) {
        this.altura = altura;
    }

    public DecimalFormat getImc() {
        return imc;
    }

    public void setImc(DecimalFormat imc) {
        this.imc = imc;
    }

    public DecimalFormat getCircunferencia_abdominal() {
        return circunferencia_abdominal;
    }

    public void setCircunferencia_abdominal(DecimalFormat circunferencia_abdominal) {
        this.circunferencia_abdominal = circunferencia_abdominal;
    }

    public DecimalFormat getCircunferencia_quadril() {
        return circunferencia_quadril;
    }

    public void setCircunferencia_quadril(DecimalFormat circunferencia_quadril) {
        this.circunferencia_quadril = circunferencia_quadril;
    }

    public String getPlanejamento_alimentar() {
        return planejamento_alimentar;
    }

    public void setPlanejamento_alimentar(String planejamento_alimentar) {
        this.planejamento_alimentar = planejamento_alimentar;
    }

    public Date getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(Date data_inicio) {
        this.data_inicio = data_inicio;
    }

    public Date getData_final() {
        return data_final;
    }

    public void setData_final(Date data_final) {
        this.data_final = data_final;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AvaliacaoFisica that = (AvaliacaoFisica) o;
        return Objects.equals(id, that.id) && Objects.equals(peso, that.peso) && Objects.equals(altura, that.altura) && Objects.equals(imc, that.imc) && Objects.equals(circunferencia_abdominal, that.circunferencia_abdominal) && Objects.equals(circunferencia_quadril, that.circunferencia_quadril) && Objects.equals(planejamento_alimentar, that.planejamento_alimentar) && Objects.equals(data_inicio, that.data_inicio) && Objects.equals(data_final, that.data_final);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, peso, altura, imc, circunferencia_abdominal, circunferencia_quadril, planejamento_alimentar, data_inicio, data_final);
    }
}
