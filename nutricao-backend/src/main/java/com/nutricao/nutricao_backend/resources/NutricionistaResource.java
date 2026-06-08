package com.nutricao.nutricao_backend.resources;

import com.nutricao.nutricao_backend.dto.nutricionista.NutricionistaResponseDTO;
import com.nutricao.nutricao_backend.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nutricionistas")
public class NutricionistaResource {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<NutricionistaResponseDTO>> listar() {

        return ResponseEntity.ok(
                usuarioService.listarNutricionistas()
        );
    }
}