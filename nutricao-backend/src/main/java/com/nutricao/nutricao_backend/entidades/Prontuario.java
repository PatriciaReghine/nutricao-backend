package com.nutricao.nutricao_backend.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name ="prontuario")
public class Prontuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    @Column(unique = true, nullable = false)
    private String numeroProntuario;

    @Column(columnDefinition = "TEXT")
    private String objetivo;

    @Column(columnDefinition = "TEXT")
    private String restricaoAlimentar;

    @Column(columnDefinition = "TEXT")
    private String informacoesClinicas;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @OneToMany(mappedBy = "prontuario")
    @JsonIgnore
    private List<AvaliacaoFisica> avaliacoesFisicas;

    public Prontuario(){}

    public Prontuario(Long id, String numeroProntuario, String objetivo, String restricaoAlimentar, String informacoesClinicas){
        this.id = id;
        this.objetivo  = objetivo;
        this.numeroProntuario = numeroProntuario;
        this.restricaoAlimentar = restricaoAlimentar;
        this.informacoesClinicas = informacoesClinicas;


    }

    public List<AvaliacaoFisica> getAvaliacoesFisicas() {
        return avaliacoesFisicas;
    }

    public void setAvaliacoesFisicas(List<AvaliacaoFisica> avaliacoesFisicas) {
        this.avaliacoesFisicas = avaliacoesFisicas;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getRestricaoAlimentar() {
        return restricaoAlimentar;
    }

    public void setRestricaoAlimentar(String restricaoAlimentar) {
        this.restricaoAlimentar = restricaoAlimentar;
    }

    public String getInformacoesClinicas() {
        return informacoesClinicas;
    }

    public void setInformacoesClinicas(String informacoesClinicas) {
        this.informacoesClinicas = informacoesClinicas;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getNumeroProntuario() {
        return numeroProntuario;
    }

    public void setNumeroProntuario(String numeroProntuario) {
        this.numeroProntuario = numeroProntuario;
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
        Prontuario that = (Prontuario) o;
        return Objects.equals(id, that.id) && Objects.equals(numeroProntuario, that.numeroProntuario) && Objects.equals(objetivo, that.objetivo) && Objects.equals(restricaoAlimentar, that.restricaoAlimentar) && Objects.equals(informacoesClinicas, that.informacoesClinicas) && Objects.equals(usuario, that.usuario) && Objects.equals(paciente, that.paciente) && Objects.equals(avaliacoesFisicas, that.avaliacoesFisicas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroProntuario, objetivo, restricaoAlimentar, informacoesClinicas, usuario, paciente, avaliacoesFisicas);
    }
}
