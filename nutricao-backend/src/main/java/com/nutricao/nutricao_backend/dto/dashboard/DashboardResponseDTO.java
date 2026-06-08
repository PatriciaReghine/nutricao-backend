package com.nutricao.nutricao_backend.dto.dashboard;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DashboardResponseDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DashboardPacienteDTO pacienteSelecionado;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<DashboardHistoricoDTO> historico;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalUsuarios;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalPacientes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long agendamentosMes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long consultasHoje;

    private List<DashboardAgendaDTO> agendamentosHoje;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<DashboardNutricionistaResumoDTO> resumoNutricionistas;
}