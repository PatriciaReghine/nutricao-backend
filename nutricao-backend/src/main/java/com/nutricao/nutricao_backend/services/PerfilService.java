package com.nutricao.nutricao_backend.services;


import com.nutricao.nutricao_backend.entidades.Perfil;
import com.nutricao.nutricao_backend.repositories.PerfilRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PerfilService {
    @Autowired
    private PerfilRepositorie perfilRepositorie;

    public List<Perfil>findAllPerfil(){
        return perfilRepositorie.findAll();
    }

    public Perfil buscarID(Long id){
        Optional<Perfil> objeto = perfilRepositorie.findById(id);
        return objeto.get();
    }
}
