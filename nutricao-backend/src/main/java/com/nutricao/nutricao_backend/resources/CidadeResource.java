package com.nutricao.nutricao_backend.resources;

import com.nutricao.nutricao_backend.services.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
    @RequestMapping("/cidades")
    public class CidadeResource {

        @Autowired
        private CidadeService cidadeService;

        @PostMapping("/importar")
        public ResponseEntity<String> importar() {
            cidadeService.importarCidadesIBGE();
            return ResponseEntity.ok("Cidades importadas com sucesso!");
        }

    @GetMapping
    public List<String> listarCidades() {

        return cidadeService.listarCidades();

    }
    }

