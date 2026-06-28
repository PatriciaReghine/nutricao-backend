package com.nutricao.nutricao_backend.dto.relatorios;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RelatorioPacienteResponseDTO {

    private Long total;

    private List<RelatorioPacienteDTO> pacientes;

    public RelatorioPacienteResponseDTO() {
    }
}