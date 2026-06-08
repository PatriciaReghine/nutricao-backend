package com.nutricao.nutricao_backend.dto.paciente;

import com.nutricao.nutricao_backend.dto.prontuario.ProntuarioDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PacienteResponseDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String logradouro;
    private String bairro;
    private Integer numero;
    private String email;
    private String cidade;
    private String cep;
    private String telefone;
    private LocalDate dataNascimento;
    private LocalDate dataCadastro;
    private String genero;
    private ProntuarioDTO prontuario;

}
