package com.nutricao.nutricao_backend.dto.agenda;

import com.nutricao.nutricao_backend.dto.nutricionista.NutricionistaDTO;
import com.nutricao.nutricao_backend.enums.StatusAgenda;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaDiaDTO {

    private Long id;
    private StatusAgenda status;
    private String data;
    private NutricionistaDTO nutricionista;
}