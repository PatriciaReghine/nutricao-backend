package com.nutricao.nutricao_backend.dto.dashboard;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DashboardPacienteDTO {

    private Long id;

    private String nome;

    private Integer idade;

    private String genero;

    private DashboardProntuarioDTO prontuario;

    private LocalDate dataNascimento;
}