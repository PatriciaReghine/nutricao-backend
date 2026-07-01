package com.nutricao.nutricao_backend.resources;

import com.nutricao.nutricao_backend.dto.paciente.PacienteBuscaDTO;
import com.nutricao.nutricao_backend.dto.paciente.PacienteRequestDTO;
import com.nutricao.nutricao_backend.dto.paciente.PacienteResponseDTO;
import com.nutricao.nutricao_backend.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pacientes")
public class PacienteResource {

    @Autowired
    PacienteService pacienteService;

    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','SECRETARIA','NUTRICIONISTA')"
    )
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> salvar(
            @RequestBody @Valid PacienteRequestDTO dto,
            org.springframework.security.core.Authentication authentication
    ) {

        System.out.println("AUTH NO CONTROLLER: " + authentication);

        PacienteResponseDTO novo = pacienteService.salvar(dto,authentication);
        return ResponseEntity.ok(novo);
    }

    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','SECRETARIA','NUTRICIONISTA')"
    )
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> findAll(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String busca
    ) {
        List<PacienteResponseDTO> lista = pacienteService.findAll(limit, busca);
        return ResponseEntity.ok(lista);
    }


    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','SECRETARIA','NUTRICIONISTA')"
    )
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PacienteRequestDTO dto) {

        PacienteResponseDTO paciente = pacienteService.atualizar(id, dto);
        return ResponseEntity.ok(paciente);
    }

    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','SECRETARIA','NUTRICIONISTA')"
    )
    @GetMapping("/busca")
    public ResponseEntity<List<PacienteBuscaDTO>>
    buscarPacientes(
            @RequestParam String nome
    ) {

        return ResponseEntity.ok(
                pacienteService.buscarPacientes(nome)
        );
    }
}
