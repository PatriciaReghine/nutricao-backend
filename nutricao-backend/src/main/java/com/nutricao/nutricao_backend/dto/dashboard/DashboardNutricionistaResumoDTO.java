package com.nutricao.nutricao_backend.dto.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardNutricionistaResumoDTO {

    private Long nutricionistaId;

    private String nome;

    private String crn;

    private Long agendadas;

    private Long confirmadas;

    private Long canceladas;

    private Long ausentes;

    private Long realizadas;

    private Long totalHoje;

    private String proximaConsulta;
}