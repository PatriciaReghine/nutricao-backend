package com.nutricao.nutricao_backend.dto.agenda;

import com.nutricao.nutricao_backend.dto.paciente.PacienteResponseDTO;
import com.nutricao.nutricao_backend.enums.StatusConsulta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaDetalheDTO {

    private Long id;
    private String data;
    private String hora;
    private StatusConsulta status;
    private PacienteResponseDTO paciente;
}