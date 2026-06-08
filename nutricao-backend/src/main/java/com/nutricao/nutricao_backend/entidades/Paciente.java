package com.nutricao.nutricao_backend.entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String logradouro;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
    private String telefone;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCadastro;



    private String genero;
    private String bairro;
    private Integer numero;
    private String cep;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = LocalDate.now();
    }

    @OneToOne(mappedBy = "paciente")
    private Prontuario prontuario;

    @OneToMany(mappedBy = "paciente")
    @JsonIgnore
    private List<ItensAgenda> agendamentos;

    @ManyToOne
    @JoinColumn(name = "id_cidade", nullable = true)
    private Cidade cidade;

    public Paciente() {
    }

    public Paciente(Long id, String nome, String cpf, String logradouro, LocalDate dataNascimento,
                    String email, LocalDate dataCadastro, String genero,
                    String bairro, Integer numero, String cep, String telefone) {

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.logradouro = logradouro;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.dataCadastro = dataCadastro;
        this.genero = genero;
        this.bairro = bairro;
        this.numero = numero;
        this.cep = cep;
        this.telefone = telefone;
    }



    // getters e setters (mantém os seus)


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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public List<ItensAgenda> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<ItensAgenda> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(id, paciente.id) && Objects.equals(nome, paciente.nome) && Objects.equals(cpf, paciente.cpf) && Objects.equals(logradouro, paciente.logradouro) && Objects.equals(dataNascimento, paciente.dataNascimento) && Objects.equals(telefone, paciente.telefone) && Objects.equals(email, paciente.email) && Objects.equals(dataCadastro, paciente.dataCadastro) && Objects.equals(genero, paciente.genero) && Objects.equals(bairro, paciente.bairro) && Objects.equals(numero, paciente.numero) && Objects.equals(cep, paciente.cep) && Objects.equals(prontuario, paciente.prontuario) && Objects.equals(agendamentos, paciente.agendamentos) && Objects.equals(cidade, paciente.cidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf, logradouro, dataNascimento, telefone, email, dataCadastro, genero, bairro, numero, cep, prontuario, agendamentos, cidade);
    }
}