package com.nutricao.nutricao_backend.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String especialidade;
    private Boolean ativo = true;

    public Usuario() {}

    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;


    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
   private List<Prontuario> prontuarios;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Agenda> agendas;

    public Usuario(Long id, String nome, String email, String telefone, String senhaHash, String crn, String especialidade, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senhaHash = senhaHash;
        this.crn = crn;
        this.especialidade = especialidade;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public List<Prontuario> getProntuarios() {
        return prontuarios;
    }

    public void setProntuarios(List<Prontuario> prontuarios) {
        this.prontuarios = prontuarios;
    }

    public List<Agenda> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<Agenda> agendas) {
        this.agendas = agendas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(nome, usuario.nome) && Objects.equals(email, usuario.email) && Objects.equals(telefone, usuario.telefone) && Objects.equals(senhaHash, usuario.senhaHash) && Objects.equals(crn, usuario.crn) && Objects.equals(especialidade, usuario.especialidade) && Objects.equals(ativo, usuario.ativo) && Objects.equals(perfil, usuario.perfil) && Objects.equals(prontuarios, usuario.prontuarios) && Objects.equals(agendas, usuario.agendas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, telefone, senhaHash, crn, especialidade, ativo, perfil, prontuarios, agendas);
    }
}




