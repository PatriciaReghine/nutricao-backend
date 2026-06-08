package com.nutricao.nutricao_backend.dto.dashboard;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardAgendaDTO {

    private Long itemAgendaId;

    private String hora;

    private String status;

    private DashboardPacienteResumoDTO paciente;

    private DashboardNutricionistaDTO nutricionista;

    private DashboardConsultaDTO consulta;
}