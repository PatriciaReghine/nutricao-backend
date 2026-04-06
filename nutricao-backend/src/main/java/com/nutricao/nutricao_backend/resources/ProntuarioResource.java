package com.nutricao.nutricao_backend.resources;


import com.nutricao.nutricao_backend.entidades.Prontuario;
import com.nutricao.nutricao_backend.repositories.ProntuarioRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/prontuario")
public class ProntuarioResource {

    @Autowired
    ProntuarioRepositorie prontuarioRepositorie;

    @GetMapping
    public ResponseEntity<List<Prontuario>> findAllProntuarios(){
        List<Prontuario> listaProntuarios = prontuarioRepositorie.findAll();
        return ResponseEntity.ok().body(listaProntuarios);
    }
}
