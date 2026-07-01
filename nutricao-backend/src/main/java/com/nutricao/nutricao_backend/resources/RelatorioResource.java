package com.nutricao.nutricao_backend.resources;

import com.nutricao.nutricao_backend.dto.relatorios.RelatorioAgendaResponseDTO;
import com.nutricao.nutricao_backend.dto.relatorios.RelatorioConsultaResponseDTO;
import com.nutricao.nutricao_backend.dto.relatorios.RelatorioPacienteResponseDTO;
import com.nutricao.nutricao_backend.dto.relatorios.RelatorioUsuarioResponseDTO;
import com.nutricao.nutricao_backend.enums.StatusAgenda;
import com.nutricao.nutricao_backend.enums.StatusConsulta;
import com.nutricao.nutricao_backend.enums.TipoConsulta;
import com.nutricao.nutricao_backend.services.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/relatorios")
public class RelatorioResource {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/consultas")
    public RelatorioConsultaResponseDTO consultarRelatorioConsultas(

            @RequestParam(required = false)
            LocalDate dataInicio,

            @RequestParam(required = false)
            LocalDate dataFim,

            @RequestParam(required = false)
            Long nutricionistaId,

            @RequestParam(required = false)
            Long pacienteId,

            @RequestParam(required = false)
            TipoConsulta tipoConsulta,

            @RequestParam(required = false)
            StatusConsulta statusConsulta

    ) {

        return relatorioService.relatorioConsultas(
                dataInicio,
                dataFim,
                nutricionistaId,
                pacienteId,
                tipoConsulta,
                statusConsulta
        );
    }

    @GetMapping("/agendas")
    public RelatorioAgendaResponseDTO consultarRelatorioAgendas(

            @RequestParam(required = false)
            LocalDate dataInicio,

            @RequestParam(required = false)
            LocalDate dataFim,

            @RequestParam(required = false)
            Long nutricionistaId,

            @RequestParam(required = false)
            StatusAgenda status

    ) {

        return relatorioService.relatorioAgendas(
                dataInicio,
                dataFim,
                nutricionistaId,
                status
        );

    }

    @GetMapping("/usuarios")
    public RelatorioUsuarioResponseDTO consultarRelatorioUsuarios(

            @RequestParam(required = false)
            String perfil,

            @RequestParam(required = false)
            String status,

            @RequestParam(required = false) LocalDate dataInicio,

            @RequestParam(required = false) LocalDate dataFim

    ) {

        return relatorioService.relatorioUsuarios(
                dataInicio,
                dataFim,
                perfil,
                status
        );

    }


    @GetMapping("/pacientes")
    public RelatorioPacienteResponseDTO consultarRelatorioPacientes(

            @RequestParam(required = false)
            LocalDate dataInicio,

            @RequestParam(required = false)
            LocalDate dataFim,

            @RequestParam(required = false)
            String cidade,

            @RequestParam(required = false)
            String genero,

            @RequestParam(required = false)
            String faixaEtaria

    ) {

        return relatorioService.relatorioPacientes(
                dataInicio,
                dataFim,
                cidade,
                genero,
                faixaEtaria
        );
    }
}
