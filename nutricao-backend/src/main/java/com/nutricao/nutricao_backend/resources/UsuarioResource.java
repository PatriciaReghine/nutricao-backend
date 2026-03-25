package com.nutricao.nutricao_backend.resources;

import com.nutricao.nutricao_backend.entidades.Usuario;
import com.nutricao.nutricao_backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "usuario")

public class UsuarioResource {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAllUsuarios(){
        List<Usuario> listaUsuarios = usuarioService.findAllUsuario();
        return ResponseEntity.ok().body(listaUsuarios);
    }
}
