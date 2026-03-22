package com.nutricao.nutricao_backend.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String nome;
    private String cpf;
    private String endereco;
    private Date data_nascimento;
    private String email;
    private Date data_cadastro;
    private String genero;
    private String bairro;
    private Integer numero;
    private String cep;

    public Paciente(){}

    public Paciente(Long id,String nome, String cpf, String endereco, Date data_nascimento,
                    String email, Date data_cadastro, String genero, String bairro, Integer numero, String cep){

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.data_nascimento = data_nascimento;
        this.email = email;
        this.data_cadastro = data_cadastro;
        this.genero = genero;
        this.bairro = bairro;
        this.numero = numero;
        this.cep = cep;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Date getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(Date data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
        Paciente paciente = (Paciente) o;
        return Objects.equals(id, paciente.id) && Objects.equals(nome, paciente.nome) && Objects.equals(cpf, paciente.cpf) && Objects.equals(endereco, paciente.endereco) && Objects.equals(data_nascimento, paciente.data_nascimento) && Objects.equals(email, paciente.email) && Objects.equals(data_cadastro, paciente.data_cadastro) && Objects.equals(genero, paciente.genero) && Objects.equals(bairro, paciente.bairro) && Objects.equals(numero, paciente.numero) && Objects.equals(cep, paciente.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf, endereco, data_nascimento, email, data_cadastro, genero, bairro, numero, cep);
    }
}

