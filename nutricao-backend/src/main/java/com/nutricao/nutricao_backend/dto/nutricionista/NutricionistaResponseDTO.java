package com.nutricao.nutricao_backend.dto.nutricionista;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NutricionistaResponseDTO {
    private Long id;
    private String nome;
    private String crn;

    public NutricionistaResponseDTO(Long id, String nome, String crn) {
        this.id = id;
        this.nome = nome;
        this.crn = crn;
    }
}
