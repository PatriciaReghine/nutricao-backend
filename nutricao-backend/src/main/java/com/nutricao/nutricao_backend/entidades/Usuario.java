package com.nutricao.nutricao_backend.entidades;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String senhaHash;
    private String crn;
    private String assinatura;
    private String especialidade;
    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

    @OneToMany(mappedBy = "usuario")
    private List<Protuario> prontuarios;

    @OneToMany(mappedBy = "usuario")
    private List<Agenda> agendas;

    public Usuario() {
    }


    public Usuario(Long id, String nome, String email, String telefone, String senhaHash, String crn, String especialidade, String assinatura) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senhaHash = senhaHash;
        this.crn = crn;
        this.assinatura = assinatura;
        this.especialidade = especialidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Agenda> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<Agenda> agendas) {
        this.agendas = agendas;
    }

    public List<Protuario> getProntuarios() {
        return prontuarios;
    }

    public void setProntuarios(List<Protuario> prontuarios) {
        this.prontuarios = prontuarios;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(nome, usuario.nome) && Objects.equals(email, usuario.email) && Objects.equals(telefone, usuario.telefone) && Objects.equals(senhaHash, usuario.senhaHash) && Objects.equals(crn, usuario.crn) && Objects.equals(assinatura, usuario.assinatura) && Objects.equals(especialidade, usuario.especialidade) && Objects.equals(perfil, usuario.perfil) && Objects.equals(prontuarios, usuario.prontuarios) && Objects.equals(agendas, usuario.agendas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, telefone, senhaHash, crn, assinatura, especialidade, perfil, prontuarios, agendas);
    }
}




