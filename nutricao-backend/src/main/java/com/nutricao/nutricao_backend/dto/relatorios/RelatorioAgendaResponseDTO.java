package com.nutricao.nutricao_backend.dto.relatorios;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RelatorioAgendaResponseDTO {

    private Long total;

    private List<RelatorioAgendaDTO> agendas;

}