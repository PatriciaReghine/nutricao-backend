package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.entidades.Usuario;
import com.nutricao.nutricao_backend.repositories.UsuarioRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepositorie usuarioRepositorie;

    public List<Usuario> findAllUsuario(){
        return usuarioRepositorie.findAll();
    }
    public Usuario buscarPeloId(Long id){
        Optional<Usuario> objeto = usuarioRepositorie.findById(id);
        return objeto.get();
    }
}
