package com.nutricao.nutricao_backend.dto.agenda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaRequestDTO {
    private Long usuarioId;
    private Long pacienteId;
    private String data;
    private String horario;
}
