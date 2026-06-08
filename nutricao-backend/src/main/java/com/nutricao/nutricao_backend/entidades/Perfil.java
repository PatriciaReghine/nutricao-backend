package com.nutricao.nutricao_backend.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "perfil")
public class Perfil implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomePerfil;

    @JsonIgnore
    @OneToMany(mappedBy = "perfil")
    private List<Usuario> usuarios;

    public Perfil(){}

    public Perfil (Long id, String nomePerfil){
        this.id = id;
        this.nomePerfil = nomePerfil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePerfil() {
        return nomePerfil;
    }

    public void setNomePerfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Perfil perfil = (Perfil) o;
        return Objects.equals(id, perfil.id) && Objects.equals(nomePerfil, perfil.nomePerfil) && Objects.equals(usuarios, perfil.usuarios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomePerfil, usuarios);
    }
}
