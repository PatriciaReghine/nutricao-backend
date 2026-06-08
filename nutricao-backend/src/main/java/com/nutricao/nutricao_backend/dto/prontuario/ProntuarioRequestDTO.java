package com.nutricao.nutricao_backend.dto.prontuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProntuarioRequestDTO {

    private String objetivo;
    private String informacoesClinicas;
    private String restricaoAlimentar;
}
