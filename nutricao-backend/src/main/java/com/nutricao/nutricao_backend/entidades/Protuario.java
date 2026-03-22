package com.nutricao.nutricao_backend.entidades;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name ="prontuario")
public class Protuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String numero_prontuario;
    private String objetivo;
    private String restricao_alimentar;
    private String condicoes_alimentares;

    public Protuario(){}

    public Protuario(Long id, String numero_prontuario, String objetivo, String restricao_alimentar, String condicoes_alimentares){
        this.id = id;
        this.objetivo  = objetivo;
        this.numero_prontuario = numero_prontuario;
        this.restricao_alimentar = restricao_alimentar;
        this.condicoes_alimentares = condicoes_alimentares;


    }


    public String getRestricao_alimentar() {
        return restricao_alimentar;
    }

    public void setRestricao_alimentar(String restricao_alimentar) {
        this.restricao_alimentar = restricao_alimentar;
    }

    public String getCondicoes_alimentares() {
        return condicoes_alimentares;
    }

    public void setCondicoes_alimentares(String condicoes_alimentares) {
        this.condicoes_alimentares = condicoes_alimentares;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getNumero_prontuario() {
        return numero_prontuario;
    }

    public void setNumero_prontuario(String numero_prontuario) {
        this.numero_prontuario = numero_prontuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Protuario protuario = (Protuario) o;
        return Objects.equals(id, protuario.id) && Objects.equals(numero_prontuario, protuario.numero_prontuario) && Objects.equals(objetivo, protuario.objetivo) && Objects.equals(restricao_alimentar, protuario.restricao_alimentar) && Objects.equals(condicoes_alimentares, protuario.condicoes_alimentares);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero_prontuario, objetivo, restricao_alimentar, condicoes_alimentares);
    }
}
