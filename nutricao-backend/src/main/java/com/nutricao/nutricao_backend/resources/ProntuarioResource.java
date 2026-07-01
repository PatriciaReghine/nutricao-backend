package com.nutricao.nutricao_backend.resources;


import com.nutricao.nutricao_backend.dto.prontuario.ProntuarioRequestDTO;
import com.nutricao.nutricao_backend.dto.prontuario.ProntuarioResponseDTO;
import com.nutricao.nutricao_backend.repositories.ProntuarioRepositorie;
import com.nutricao.nutricao_backend.services.ProntuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/prontuarios")
public class ProntuarioResource {

    @Autowired
    ProntuarioRepositorie prontuarioRepositorie;

    @Autowired
    private ProntuarioService prontuarioService;


    // Buscar por paciente
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<ProntuarioResponseDTO> buscarPorPaciente(@PathVariable Long idPaciente) {
        return ResponseEntity.ok(prontuarioService.buscarPorPaciente(idPaciente));
    }

    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','NUTRICIONISTA')"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProntuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(prontuarioService.buscarPorId(id));
    }

    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','NUTRICIONISTA')"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @RequestBody ProntuarioRequestDTO dto) {

        prontuarioService.atualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','NUTRICIONISTA')"
    )

    @PutMapping("/paciente/{idPaciente}")
    public ResponseEntity<Void> atualizarPorPaciente(
            @PathVariable Long idPaciente,
            @RequestBody ProntuarioRequestDTO dto) {

        prontuarioService.atualizarPorPaciente(idPaciente, dto);
        return ResponseEntity.noContent().build();
    }
}
