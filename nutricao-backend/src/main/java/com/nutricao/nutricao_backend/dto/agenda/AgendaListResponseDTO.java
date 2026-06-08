package com.nutricao.nutricao_backend.dto.agenda;

import com.nutricao.nutricao_backend.dto.nutricionista.NutricionistaAgendaDTO;
import com.nutricao.nutricao_backend.enums.StatusAgenda;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaListResponseDTO {

    private Long id;
    private String data;
    private StatusAgenda status;

    private NutricionistaAgendaDTO nutricionista;

}