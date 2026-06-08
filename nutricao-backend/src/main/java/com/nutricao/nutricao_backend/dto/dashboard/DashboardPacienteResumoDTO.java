package com.nutricao.nutricao_backend.dto.dashboard;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardPacienteResumoDTO {

    private Long id;

    private String nome;

    private LocalDate dataNascimento;

}