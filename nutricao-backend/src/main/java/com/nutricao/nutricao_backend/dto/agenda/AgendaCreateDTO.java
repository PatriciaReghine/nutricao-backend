package com.nutricao.nutricao_backend.dto.agenda;

import com.nutricao.nutricao_backend.enums.StatusAgenda;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaCreateDTO {

    @NotBlank(message = "Data é obrigatória")
    private String data;

    @NotNull(message = "Nutricionista é obrigatório")
    private Long nutricionistaId;
    private StatusAgenda status;
}