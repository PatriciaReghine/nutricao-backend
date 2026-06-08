package com.nutricao.nutricao_backend.dto.agenda;

import com.nutricao.nutricao_backend.enums.StatusConsulta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendamentoStatusDTO {
    private StatusConsulta status;
}