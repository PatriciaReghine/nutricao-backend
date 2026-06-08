package com.nutricao.nutricao_backend.resources;

import com.nutricao.nutricao_backend.dto.avaliacao.AvaliacaoFisicaDto;
import com.nutricao.nutricao_backend.entidades.AvaliacaoFisica;
import com.nutricao.nutricao_backend.services.AvaliacaoFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/avaliacao")
public class AvaliacaoFisicaResource {

    @Autowired
    AvaliacaoFisicaService avaliacaoFisicaService;

    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','NUTRICIONISTA')"
    )
    @GetMapping
    public ResponseEntity<List<AvaliacaoFisica>> findAll(){
        List<AvaliacaoFisica> ListaAvaliacao = avaliacaoFisicaService.findAll();
        return ResponseEntity.ok().body(ListaAvaliacao);
    }

    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','NUTRICIONISTA')"
    )
    @PostMapping
    public ResponseEntity<AvaliacaoFisica> salvar(@RequestBody AvaliacaoFisicaDto dto) {
        return ResponseEntity.ok(avaliacaoFisicaService.salvar(dto));
    }
}
