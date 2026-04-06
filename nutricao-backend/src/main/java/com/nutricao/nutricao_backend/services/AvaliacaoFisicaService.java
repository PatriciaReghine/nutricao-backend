package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.entidades.AvaliacaoFisica;
import com.nutricao.nutricao_backend.repositories.AvaliacaoFisicaRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoFisicaService {
    @Autowired
    private AvaliacaoFisicaRepositorie avaliacaoFisicaRepositorie;

    public List<AvaliacaoFisica> findAll(){
        return avaliacaoFisicaRepositorie.findAll();
    }
}
