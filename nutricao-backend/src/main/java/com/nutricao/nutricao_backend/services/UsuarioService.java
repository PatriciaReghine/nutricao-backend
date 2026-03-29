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
    public Usuario salvar(Usuario usuario) {
        return usuarioRepositorie.save(usuario);
    }

    public boolean autenticar(String email, String senha){
        Optional<Usuario> usuarioOpt = usuarioRepositorie.findByEmail(email);
        if(usuarioOpt.isPresent()){
            Usuario usuario = usuarioOpt.get();

            if (usuario.getSenhaHash() != null && usuario.getSenhaHash().equals(senha)) {
                return true;
            }
        }
        return false;
    }
}
