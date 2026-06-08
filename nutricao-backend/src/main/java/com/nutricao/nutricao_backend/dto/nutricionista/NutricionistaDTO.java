package com.nutricao.nutricao_backend.dto.nutricionista;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NutricionistaDTO {

    private Long id;
    private String nome;
    private String crn;

    private String especialidade;
}