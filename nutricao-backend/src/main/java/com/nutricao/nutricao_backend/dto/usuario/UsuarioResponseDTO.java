package com.nutricao.nutricao_backend.dto.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crn;
    private String especialidade;
    private String perfil;
    private Boolean ativo;

    public UsuarioResponseDTO(Long id, String nome, String email, String telefone, String crn, String especialidade, String perfil, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.crn = crn;
        this.especialidade = especialidade;
        this.perfil = perfil;
        this.ativo = ativo;
    }
}
