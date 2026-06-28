package com.nutricao.nutricao_backend.dto.relatorios;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RelatorioUsuarioResponseDTO {

    private Long total;

    private List<RelatorioUsuarioDTO> usuarios;

    public RelatorioUsuarioResponseDTO() {
    }

}