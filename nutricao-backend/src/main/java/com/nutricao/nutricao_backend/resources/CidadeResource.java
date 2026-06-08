package com.nutricao.nutricao_backend.resources;

import com.nutricao.nutricao_backend.services.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
    @RequestMapping("/cidade")
    public class CidadeResource {

        @Autowired
        private CidadeService cidadeService;

        @PostMapping("/importar")
        public ResponseEntity<String> importar() {
            cidadeService.importarCidadesIBGE();
            return ResponseEntity.ok("Cidades importadas com sucesso!");
        }
    }

