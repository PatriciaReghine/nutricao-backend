package com.nutricao.nutricao_backend.dto.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardProntuarioDTO {

    private Long id;

    private String numero;

    private String objetivo;

    private String informacoesClinicas;

    private String restricaoAlimentar;
}