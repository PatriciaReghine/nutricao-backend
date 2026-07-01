package com.nutricao.nutricao_backend.resources;

import com.nutricao.nutricao_backend.dto.agenda.AgendaStatusDTO;
import com.nutricao.nutricao_backend.dto.agenda.AgendamentoStatusDTO;
import com.nutricao.nutricao_backend.dto.agenda.AgendarConsultaDTO;
import com.nutricao.nutricao_backend.dto.consulta.ConsultaAgendaDTO;
import com.nutricao.nutricao_backend.services.AgendamentoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoResource {

    @Autowired
    private AgendamentoService agendamentoService;

    // Listar Agendamentos

    @GetMapping
    public ResponseEntity<List<ConsultaAgendaDTO>> listar(
            @RequestParam String data,
            @RequestParam Long nutricionistaId) {

        return ResponseEntity.ok(
                agendamentoService.listarAgendamentosDoDia(nutricionistaId, data)
        );
    }

    // Agendar Consulta

    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','SECRETARIA','NUTRICIONISTA')"
    )
    @PostMapping
    public ResponseEntity<?> agendar(
            @Valid @RequestBody AgendarConsultaDTO dto,
            @RequestHeader("Authorization") String token
    ) {

        token = token.replace("Bearer ", "");

        agendamentoService.agendarConsulta(dto, token);

        return ResponseEntity.ok("Consulta agendada com sucesso");
    }

    //  Cancelar Consulta

    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','SECRETARIA')"
    )
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {

        agendamentoService.cancelar(id);

        return ResponseEntity.ok("Consulta cancelada");
    }

    //  Atualizar Status

        @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(
            @PathVariable Long id,
            @RequestBody AgendamentoStatusDTO dto) {

        agendamentoService.atualizarStatus(id, dto.getStatus());

        return ResponseEntity.ok("Status atualizado");
    }
}