package com.nutricao.nutricao_backend.dto.relatorios;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RelatorioConsultaResponseDTO {

    private Long total;

    private List<RelatorioConsultaDTO> consultas;
}