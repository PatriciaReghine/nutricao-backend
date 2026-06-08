package com.nutricao.nutricao_backend.dto.agenda;

import com.nutricao.nutricao_backend.enums.StatusConsulta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaResponseDTO {
    private Long id;
    private String horario;
    private String paciente;
    private StatusConsulta status;

    public AgendaResponseDTO(Long id, String horario, String paciente, StatusConsulta status) {
        this.id = id;
        this.horario = horario;
        this.paciente = paciente;
        this.status = status;
    }



}
