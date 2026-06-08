package com.nutricao.nutricao_backend.dto.prontuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProntuarioDTO {
        private Long id;
        private String numeroProntuario;

        private String objetivo;
        private String informacoesClinicas;
        private String restricaoAlimentar;
}