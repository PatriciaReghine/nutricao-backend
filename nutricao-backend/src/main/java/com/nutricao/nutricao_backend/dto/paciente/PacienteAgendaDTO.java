package com.nutricao.nutricao_backend.dto.paciente;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PacienteAgendaDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String genero;
    private LocalDate dataNascimento;
    private LocalDate dataCadastro;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cidade;
}