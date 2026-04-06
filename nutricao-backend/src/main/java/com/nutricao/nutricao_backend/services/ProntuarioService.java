package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.entidades.Prontuario;
import com.nutricao.nutricao_backend.repositories.ProntuarioRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProntuarioService {
    @Autowired
    private ProntuarioRepositorie protuarioRepositorie;

    public List<Prontuario> findAllUsers(){
        return protuarioRepositorie.findAll();
    }
}
