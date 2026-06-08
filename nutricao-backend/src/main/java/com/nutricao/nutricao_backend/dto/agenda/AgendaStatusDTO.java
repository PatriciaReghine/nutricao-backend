package com.nutricao.nutricao_backend.dto.agenda;

import com.nutricao.nutricao_backend.entidades.ItensAgenda;
import com.nutricao.nutricao_backend.enums.StatusAgenda;
import com.nutricao.nutricao_backend.enums.StatusConsulta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaStatusDTO {

    private StatusAgenda status;
}