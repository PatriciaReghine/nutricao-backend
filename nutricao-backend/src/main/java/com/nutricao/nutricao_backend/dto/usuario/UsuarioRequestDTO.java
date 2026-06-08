package com.nutricao.nutricao_backend.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;
    private String login;

    @NotBlank(message = "Senha é obrigatória")

    private String senha;
    private String crn;
    private String especialidade;

    @NotNull(message = "Perfil obrigatório")
    private Long perfilId;

}
