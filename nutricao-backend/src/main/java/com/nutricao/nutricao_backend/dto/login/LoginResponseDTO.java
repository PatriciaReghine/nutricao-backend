package com.nutricao.nutricao_backend.dto.login;

import com.nutricao.nutricao_backend.dto.usuario.UsuarioResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private String token;
    private UsuarioResponseDTO usuario;

    public LoginResponseDTO(String token, UsuarioResponseDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }
}