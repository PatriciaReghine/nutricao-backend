package com.nutricao.nutricao_backend.resources;

import com.nutricao.nutricao_backend.dto.dashboard.DashboardResponseDTO;
import com.nutricao.nutricao_backend.services.DashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardResource {

    @Autowired
    private DashboardService dashboardService;

    @PreAuthorize(
            "hasAnyRole('ADMINISTRADOR','SECRETARIA','NUTRICIONISTA')"
    )
    @GetMapping
    public ResponseEntity<DashboardResponseDTO> listar(
            @RequestParam String data,
            @RequestParam(required = false) Long pacienteId,
            @RequestHeader("Authorization") String token
    ) {

        token = token.replace("Bearer ", "");

        DashboardResponseDTO response =
                dashboardService.buscarDashboard(
                        data,
                        pacienteId,
                        token
                );

        return ResponseEntity.ok(response);
    }
}