package com.nutricao.nutricao_backend.dto.agenda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendarConsultaDTO {

    @NotBlank(message = "Data é obrigatória")
    private String data;

    @NotBlank(message = "Horário é obrigatório")
    private String hora;

    @NotNull(message = "Nutricionista é obrigatório")
    private Long nutricionistaId;

    @NotNull(message = "Paciente é obrigatório")
    private Long pacienteId;
}