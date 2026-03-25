package com.nutricao.nutricao_backend.resources;

import com.nutricao.nutricao_backend.entidades.Perfil;
import com.nutricao.nutricao_backend.services.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/perfil")

public class PerfilResource {

    @Autowired
    PerfilService perfilService;

    @GetMapping
    public ResponseEntity<List<Perfil>> findAllUsers(){
        List<Perfil> listaDePerfil = perfilService.findAllPerfil();
        return ResponseEntity.ok().body(listaDePerfil);
    }

}
