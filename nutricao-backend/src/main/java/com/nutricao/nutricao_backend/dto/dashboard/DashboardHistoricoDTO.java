package com.nutricao.nutricao_backend.dto.dashboard;

import com.nutricao.nutricao_backend.enums.TipoConsulta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardHistoricoDTO {

    private Long id;

    private String data;

    private TipoConsulta tipoConsulta;

    private String resumo;
}