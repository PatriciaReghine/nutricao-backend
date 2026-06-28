package com.nutricao.nutricao_backend.dto.relatorios;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RelatorioUsuarioDTO {

    private String nome;

    private String perfil;

    private String status;

    private String telefone;

    private String email;

    private String crn;

    private String especialidade;

    public RelatorioUsuarioDTO() {
    }

}