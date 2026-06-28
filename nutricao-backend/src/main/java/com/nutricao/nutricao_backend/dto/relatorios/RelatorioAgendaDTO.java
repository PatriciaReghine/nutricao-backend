package com.nutricao.nutricao_backend.dto.relatorios;

import com.nutricao.nutricao_backend.enums.StatusAgenda;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RelatorioAgendaDTO {

    private LocalDate data;

    private String nutricionista;

    private StatusAgenda status;

    private Integer horariosOcupados;

    private Integer horariosLivres;

}