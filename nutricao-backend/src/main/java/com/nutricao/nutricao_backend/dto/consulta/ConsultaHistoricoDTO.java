package com.nutricao.nutricao_backend.dto.consulta;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaHistoricoDTO {

    private Long id;

    private String data;

    private String tipoConsulta;

    private String resumo;

    private String numeroProntuario;}