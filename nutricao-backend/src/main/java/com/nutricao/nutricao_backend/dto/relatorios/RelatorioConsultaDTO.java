package com.nutricao.nutricao_backend.dto.relatorios;

import com.nutricao.nutricao_backend.enums.StatusConsulta;
import com.nutricao.nutricao_backend.enums.TipoConsulta;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class RelatorioConsultaDTO {

    private LocalDate data;

    private LocalTime hora;

    private String paciente;

    private String nutricionista;

    private TipoConsulta tipoConsulta;

    private StatusConsulta status;
}