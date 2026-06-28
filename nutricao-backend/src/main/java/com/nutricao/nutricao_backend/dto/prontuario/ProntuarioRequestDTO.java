package com.nutricao.nutricao_backend.dto.prontuario;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProntuarioRequestDTO {

    @Size(max = 500)
    private String objetivo;

    @Size(max = 500)
    private String informacoesClinicas;

    @Size(max = 500)
    private String restricaoAlimentar;
}
