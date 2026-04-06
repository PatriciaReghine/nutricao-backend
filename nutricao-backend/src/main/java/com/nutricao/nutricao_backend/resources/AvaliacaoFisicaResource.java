package com.nutricao.nutricao_backend.resources;

import com.nutricao.nutricao_backend.entidades.AvaliacaoFisica;
import com.nutricao.nutricao_backend.repositories.AvaliacaoFisicaRepositorie;
import com.nutricao.nutricao_backend.services.AvaliacaoFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/avaliacao")
public class AvaliacaoFisicaResource {

    @Autowired
    AvaliacaoFisicaService avaliacaoFisicaService;

    @GetMapping
    public ResponseEntity<List<AvaliacaoFisica>> findAll(){
        List<AvaliacaoFisica> ListaAvaliacao = avaliacaoFisicaService.findAll();
        return ResponseEntity.ok().body(ListaAvaliacao);
    }
}
