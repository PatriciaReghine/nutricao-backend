package com.nutricao.nutricao_backend.dto.cep;

public class CepResponse {
    private String localidade;
    private String uf;

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
