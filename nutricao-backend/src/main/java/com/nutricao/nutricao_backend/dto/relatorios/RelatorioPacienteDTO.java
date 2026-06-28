package com.nutricao.nutricao_backend.dto.relatorios;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RelatorioPacienteDTO {

    private Long id;

    private String numeroProntuario;

    private String nome;

    private Integer idade;

    private String genero;

    private String telefone;

    private String cidade;

    private LocalDate dataCadastro;

    public RelatorioPacienteDTO() {
    }

}