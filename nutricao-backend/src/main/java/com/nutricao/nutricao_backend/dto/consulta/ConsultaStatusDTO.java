package com.nutricao.nutricao_backend.dto.consulta;

import com.nutricao.nutricao_backend.enums.StatusConsulta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaStatusDTO {

    private StatusConsulta status;
}