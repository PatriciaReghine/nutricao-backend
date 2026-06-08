package com.nutricao.nutricao_backend.dto.paciente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteBuscaDTO {

    private Long id;
    private String nome;
    private String cpf;
}