package com.nutricao.nutricao_backend.dto.consulta;

import com.nutricao.nutricao_backend.dto.paciente.PacienteAgendaDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaSlotDTO {

    private Long id;
    private String agendamentoStatus;
    private PacienteAgendaDTO paciente;
}