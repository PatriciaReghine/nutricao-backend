package com.nutricao.nutricao_backend.dto.prontuario;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProntuarioResponseDTO {
    private Long id;
    private String numeroProntuario;
    private String nome;
    private String genero;
    private String objetivo;
    private String informacoesClinicas;
    private String restricaoAlimentar;
    private LocalDate dataNascimento;
}
