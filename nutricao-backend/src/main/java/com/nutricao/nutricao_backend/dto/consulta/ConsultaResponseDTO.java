package com.nutricao.nutricao_backend.dto.consulta;

import com.nutricao.nutricao_backend.dto.nutricionista.NutricionistaDTO;
import com.nutricao.nutricao_backend.dto.paciente.PacienteAgendaDTO;
import com.nutricao.nutricao_backend.enums.TipoConsulta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaResponseDTO {

    private Long id;

    private String data;

    private TipoConsulta tipoConsulta;

    private String resumo;

    private String observacoes;

    private ConsultaAvaliacaoResponseDTO avaliacao;

    private NutricionistaDTO nutricionista;

    private PacienteAgendaDTO paciente;

}