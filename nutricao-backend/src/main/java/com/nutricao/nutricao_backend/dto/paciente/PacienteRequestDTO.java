package com.nutricao.nutricao_backend.dto.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PacienteRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    private String cpf;
    private String logradouro;

    private String email;

    @NotBlank(message = "Gênero é obrigatório")
    private String genero;
    private String bairro;
    private Integer numero;
    private String cep;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotNull(message = "Data de nascimento obrigatória")
    @Past(message = "Data inválida")
    private LocalDate dataNascimento;
}
