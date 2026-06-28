package com.nutricao.nutricao_backend.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150)
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Size(max = 150)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;
    private String login;

    @NotBlank(message = "Senha é obrigatória")
    @Size(max = 100)
    private String senha;

    @Size(max = 10)
    private String crn;

    @Size(max = 100)
    private String especialidade;

    @NotNull(message = "Perfil obrigatório")
    private Long perfilId;

}
