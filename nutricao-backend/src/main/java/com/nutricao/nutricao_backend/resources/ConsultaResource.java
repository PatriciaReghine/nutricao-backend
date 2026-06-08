package com.nutricao.nutricao_backend.resources;

import com.nutricao.nutricao_backend.dto.consulta.ConsultaHistoricoDTO;
import com.nutricao.nutricao_backend.dto.consulta.ConsultaRequestDTO;
import com.nutricao.nutricao_backend.dto.consulta.ConsultaResponseDTO;
import com.nutricao.nutricao_backend.dto.consulta.ConsultaVisibilidadeDTO;
import com.nutricao.nutricao_backend.entidades.Consulta;
import com.nutricao.nutricao_backend.services.ConsultaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaResource {

    @Autowired
    private ConsultaService consultaService;

    // ===============================
    // 🧾 SALVAR CONSULTA (ATENDIMENTO)
    // ===============================

    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','NUTRICIONISTA')"
    )
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> salvar(@RequestBody @Valid ConsultaRequestDTO dto) {

        return ResponseEntity.ok(
                consultaService.salvar(dto)
        );
    }

    // ===============================
    // 🔍 BUSCAR DETALHE DA CONSULTA
    // ===============================

    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','NUTRICIONISTA')"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(
                consultaService.buscarPorId(id)
        );
    }


    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','NUTRICIONISTA')"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ConsultaRequestDTO dto
    ) {

        return ResponseEntity.ok(
                consultaService.atualizar(id, dto)
        );
    }
    @GetMapping("/ultima/{pacienteId}")
    public ResponseEntity<?> buscarUltimaConsulta(
            @PathVariable Long pacienteId
    ) {

        ConsultaResponseDTO consulta =
                consultaService.buscarUltimaConsulta(
                        pacienteId
                );

        if (consulta == null) {

            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity.ok(
                consulta
        );
    }

    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','NUTRICIONISTA')"
    )
    @GetMapping
    public ResponseEntity<List<ConsultaHistoricoDTO>> listarHistorico(
            @RequestParam Long pacienteId
    ) {

        return ResponseEntity.ok(
                consultaService.listarHistorico(
                        pacienteId
                )
        );
    }
    @PutMapping("/{id}/visibilidade")
    public ResponseEntity<Void> alterarVisibilidade(
            @PathVariable Long id,
            @RequestBody ConsultaVisibilidadeDTO dto
    ) {

        consultaService.alterarVisibilidade(
                id,
                dto.getVisivel()
        );

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/ocultas")
    public ResponseEntity<List<ConsultaResponseDTO>> listarOcultas(
            @RequestParam Long pacienteId
    ) {

        return ResponseEntity.ok(
                consultaService.listarOcultas(
                        pacienteId
                )
        );
    }
}