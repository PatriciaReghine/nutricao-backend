package com.nutricao.nutricao_backend.dto.consulta;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaAvaliacaoResponseDTO {

    private Double peso;

    private Double altura;

    private Double imc;

    private Double circunferenciaAbdominal;

    private Double circunferenciaQuadril;

    @Size(max = 1000)
    private String planoAlimentar;

    private Double percentualGordura;
}