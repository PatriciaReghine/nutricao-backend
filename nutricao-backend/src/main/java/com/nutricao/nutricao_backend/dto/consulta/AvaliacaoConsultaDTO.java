package com.nutricao.nutricao_backend.dto.consulta;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliacaoConsultaDTO {

    @NotNull(message = "Peso é obrigatório")
    private Double peso;

    @NotNull(message = "Altura é obrigatória")
    private Double altura;

    private Double imc;

    private Double circunferenciaAbdominal;

    private Double circunferenciaQuadril;

    private String planoAlimentar;

    private Double percentualGordura;
}