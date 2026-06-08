package com.nutricao.nutricao_backend.dto.login;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank(message = "Email obrigatório")
    private String email;

    @NotBlank(message = "Senha obrigatória")
    private String senha;

    public LoginDTO() {}

    public LoginDTO(String email, String senha){
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
