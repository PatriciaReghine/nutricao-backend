package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.dto.relatorios.*;
import com.nutricao.nutricao_backend.entidades.*;
import com.nutricao.nutricao_backend.enums.StatusAgenda;
import com.nutricao.nutricao_backend.enums.StatusConsulta;
import com.nutricao.nutricao_backend.enums.TipoConsulta;
import com.nutricao.nutricao_backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private ConsultaRepositorie consultaRepositorie;

    @Autowired
    private AgendaRepositorie agendaRepositorie;

    @Autowired
    private UsuarioRepositorie usuarioRepositorie;

    @Autowired
    private PacienteRepositorie pacienteRepositorie;

    @Autowired
    private ProntuarioRepositorie prontuarioRepositorie;

    @Autowired
    private ItensAgendaRepositorie itensAgendaRepositorie;

    public RelatorioConsultaResponseDTO relatorioConsultas(
            LocalDate dataInicio,
            LocalDate dataFim,
            Long nutricionistaId,
            Long pacienteId,
            TipoConsulta tipoConsulta,
            StatusConsulta statusConsulta
    ) {

        List<ItensAgenda> consultas =
                itensAgendaRepositorie.findAll();

// Filtro Inicio

        if (dataInicio != null) {

            consultas = consultas.stream()

                    .filter(item ->
                            item.getAgenda() != null
                                    &&
                                    !item.getAgenda().getData().isBefore(dataInicio)
                    )

                    .toList();
        }

// Filtro Data Fim

        if (dataFim != null) {

            consultas = consultas.stream()

                    .filter(item ->
                            item.getAgenda() != null
                                    &&
                                    !item.getAgenda().getData().isAfter(dataFim)
                    )

                    .toList();
        }


// Filtro Paciente

        if (pacienteId != null) {

            consultas = consultas.stream()

                    .filter(item ->

                            item.getPaciente() != null

                                    &&

                                    item.getPaciente()
                                            .getId()
                                            .equals(pacienteId)
                    )

                    .toList();
        }

// Filtro Nutricionista

        if (nutricionistaId != null) {

            consultas = consultas.stream()

                    .filter(item ->

                            item.getAgenda() != null

                                    &&

                                    item.getAgenda()
                                            .getUsuario() != null

                                    &&

                                    item.getAgenda()
                                            .getUsuario()
                                            .getId()
                                            .equals(nutricionistaId)
                    )

                    .toList();
        }


// Filtro Tipo Consulta

        if (tipoConsulta != null) {

            consultas = consultas.stream()

                    .filter(item ->

                            item.getConsulta() != null

                                    &&

                                    item.getConsulta()
                                            .getTipoConsulta() == tipoConsulta
                    )

                    .toList();
        }


// Filtro Status


        if (statusConsulta != null) {

            consultas = consultas.stream()

                    .filter(item ->
                            item.getStatusConsulta() == statusConsulta
                    )

                    .toList();
        }


        List<RelatorioConsultaDTO> lista =

                consultas.stream()

                        .filter(item ->

                                item.getAgenda() != null

                                        &&

                                        item.getAgenda().getUsuario() != null
                        )

                        .map(item -> {

                            RelatorioConsultaDTO dto =
                                    new RelatorioConsultaDTO();

                            dto.setData(
                                    item.getAgenda().getData()
                            );

                            dto.setHora(
                                    item.getHorario()
                            );

                            dto.setPaciente(

                                    item.getPaciente() != null

                                            ? item.getPaciente().getNome()

                                            : null
                            );

                            dto.setNutricionista(
                                    item.getAgenda()
                                            .getUsuario()
                                            .getNome()
                            );

                            dto.setTipoConsulta(

                                    item.getConsulta() != null

                                            ? item.getConsulta()
                                            .getTipoConsulta()

                                            : null
                            );

                            dto.setStatus(
                                    item.getStatusConsulta()
                            );

                            return dto;

                        })

                        .sorted(

                                Comparator

                                        .comparing(
                                                RelatorioConsultaDTO::getData
                                        )

                                        .thenComparing(
                                                RelatorioConsultaDTO::getHora
                                        )

                        )

                        .toList();


// Response


        RelatorioConsultaResponseDTO response =
                new RelatorioConsultaResponseDTO();

        response.setTotal(
                (long) lista.size()
        );

        response.setConsultas(
                lista
        );

        return response;
    }

    public RelatorioAgendaResponseDTO relatorioAgendas(
            LocalDate dataInicio,
            LocalDate dataFim,
            Long nutricionistaId,
            StatusAgenda status
    ) {


        List<Agenda> agendas =
                agendaRepositorie.findAll();


        final int TOTAL_HORARIOS = 20;

        // Filtro Data Inicial

        if (dataInicio != null) {

            agendas = agendas.stream()

                    .filter(agenda ->
                            !agenda.getData().isBefore(dataInicio)
                    )

                    .toList();
        }


        // Filtro Data Final

        if (dataFim != null) {

            agendas = agendas.stream()

                    .filter(agenda ->
                            !agenda.getData().isAfter(dataFim)
                    )

                    .toList();
        }

        // Filtro Nutricionista

        if (nutricionistaId != null) {

            agendas = agendas.stream()

                    .filter(agenda ->

                            agenda.getUsuario() != null

                                    &&

                                    agenda.getUsuario()
                                            .getId()
                                            .equals(nutricionistaId)
                    )

                    .toList();
        }

        // Filtro Status


        if (status != null) {

            agendas = agendas.stream()

                    .filter(agenda ->
                            agenda.getStatus() == status
                    )

                    .toList();
        }

            // Montar DTO

        List<RelatorioAgendaDTO> lista =

                agendas.stream()

                        .map(agenda -> {

                            RelatorioAgendaDTO dto =
                                    new RelatorioAgendaDTO();

                            dto.setData(
                                    agenda.getData()
                            );

                            dto.setNutricionista(
                                    agenda.getUsuario()
                                            .getNome()
                            );

                            dto.setStatus(
                                    agenda.getStatus()
                            );

                            Integer horariosOcupados =
                                    (int) agenda.getItens()
                                            .stream()
                                            .filter(item ->

                                                    item.getStatusConsulta() == StatusConsulta.AGENDADA

                                                            ||

                                                            item.getStatusConsulta() == StatusConsulta.CONFIRMADA

                                                            ||

                                                            item.getStatusConsulta() == StatusConsulta.REALIZADA

                                            )
                                            .count();

                            Integer horariosLivres =
                                    TOTAL_HORARIOS - horariosOcupados;


                            dto.setHorariosOcupados(
                                    horariosOcupados
                            );

                            dto.setHorariosLivres(
                                    horariosLivres
                            );

                            return dto;

                        })

                        .sorted(

                                Comparator

                                        .comparing(
                                                RelatorioAgendaDTO::getData
                                        )

                                        .thenComparing(
                                                RelatorioAgendaDTO::getNutricionista
                                        )

                        )

                        .toList();


        // Response

        RelatorioAgendaResponseDTO response =
                new RelatorioAgendaResponseDTO();

        response.setTotal(
                (long) lista.size()
        );

        response.setAgendas(
                lista
        );

        return response;



    }

    public RelatorioUsuarioResponseDTO relatorioUsuarios(
            LocalDate dataInicio,
            LocalDate dataFim,
            String perfil,
            String status
    ) {

        List<Usuario> usuarios =
                usuarioRepositorie.findAll();

        // Filtro Data Inicial

        if (dataInicio != null) {

            usuarios = usuarios.stream()

                    .filter(usuario ->
                            !usuario.getDataCadastro().isBefore(dataInicio)
                    )

                    .toList();
        }
            //Filtro Data Final

        if (dataFim != null) {

            usuarios = usuarios.stream()

                    .filter(usuario ->
                            !usuario.getDataCadastro().isAfter(dataFim)
                    )

                    .toList();
        }


        // FIltro Perfil

        if (perfil != null && !perfil.isBlank()) {

            usuarios = usuarios.stream()

                    .filter(usuario ->

                            usuario.getPerfil() != null

                                    &&

                                    usuario.getPerfil()
                                            .getNomePerfil()
                                            .equalsIgnoreCase(perfil)
                    )

                    .toList();
        }


        // Filtro Status

        if (status != null && !status.isBlank()) {

            usuarios = usuarios.stream()

                    .filter(usuario ->

                            status.equalsIgnoreCase("ATIVO")
                                    ? Boolean.TRUE.equals(usuario.getAtivo())
                                    : Boolean.FALSE.equals(usuario.getAtivo())

                    )

                    .toList();
        }

        List<RelatorioUsuarioDTO> lista =

                usuarios.stream()

                        .map(usuario -> {

                            RelatorioUsuarioDTO dto =
                                    new RelatorioUsuarioDTO();

                            dto.setNome(
                                    usuario.getNome()
                            );

                            dto.setPerfil(
                                    usuario.getPerfil()
                                            .getNomePerfil()
                            );

                            dto.setStatus(
                                    usuario.getAtivo()
                                            ? "ATIVO"
                                            : "INATIVO"
                            );

                            dto.setTelefone(
                                    usuario.getTelefone()
                            );

                            dto.setEmail(
                                    usuario.getEmail()
                            );

                            dto.setCrn(
                                    usuario.getCrn()
                            );

                            dto.setEspecialidade(
                                    usuario.getEspecialidade()
                            );

                            return dto;

                        })

                        .sorted(

                                Comparator.comparing(
                                        RelatorioUsuarioDTO::getNome
                                )

                        )

                        .toList();

        RelatorioUsuarioResponseDTO response =
                new RelatorioUsuarioResponseDTO();

        response.setTotal(
                (long) lista.size()
        );

        response.setUsuarios(
                lista
        );

        return response;

    }

    public RelatorioPacienteResponseDTO relatorioPacientes(
            LocalDate dataInicio,
            LocalDate dataFim,
            String cidade,
            String genero,
            String faixaEtaria
    ) {

        List<Paciente> pacientes =
                pacienteRepositorie.findAll();

        // ===============================
        // DATA INÍCIO
        // ===============================

        if (dataInicio != null) {

            pacientes = pacientes.stream()

                    .filter(paciente ->
                            !paciente.getDataCadastro()
                                    .isBefore(dataInicio)
                    )

                    .toList();
        }


        // Data Final

        if (dataFim != null) {

            pacientes = pacientes.stream()

                    .filter(paciente ->
                            !paciente.getDataCadastro()
                                    .isAfter(dataFim)
                    )

                    .toList();
        }


        // Cidade

        if (cidade != null && !cidade.isBlank()) {

            pacientes = pacientes.stream()

                    .filter(paciente ->

                            paciente.getCidade() != null

                                    &&

                                    paciente.getCidade()
                                            .getNome()
                                            .equalsIgnoreCase(cidade)
                    )

                    .toList();
        }


        // Gênero

        if (genero != null && !genero.isBlank()) {

            pacientes = pacientes.stream()

                    .filter(paciente ->

                            paciente.getGenero() != null

                                    &&

                                    paciente.getGenero()
                                            .equalsIgnoreCase(genero)
                    )

                    .toList();
        }

        //Faixa etária

        if (faixaEtaria != null && !faixaEtaria.isBlank()) {

            pacientes = pacientes.stream()

                    .filter(paciente -> {

                        int idade =
                                Period.between(
                                        paciente.getDataNascimento(),
                                        LocalDate.now()
                                ).getYears();

                        return switch (faixaEtaria) {

                            case "0-18" ->
                                    idade >= 0 && idade <= 18;

                            case "19-30" ->
                                    idade >= 19 && idade <= 30;

                            case "31-50" ->
                                    idade >= 31 && idade <= 50;

                            case "50+" ->
                                    idade >= 50;

                            default -> true;
                        };

                    })

                    .toList();
        }

        List<RelatorioPacienteDTO> lista =

                pacientes.stream()

                        .map(paciente -> {

                            RelatorioPacienteDTO dto =
                                    new RelatorioPacienteDTO();

                            dto.setId(
                                    paciente.getId()
                            );

                            prontuarioRepositorie
                                    .findByPacienteId(
                                            paciente.getId()
                                    )
                                    .ifPresent(prontuario ->

                                            dto.setNumeroProntuario(
                                                    prontuario.getNumeroProntuario()
                                            )

                                    );

                            dto.setNome(
                                    paciente.getNome()
                            );

                            dto.setIdade(

                                    Period.between(

                                            paciente.getDataNascimento(),

                                            LocalDate.now()

                                    ).getYears()

                            );

                            dto.setGenero(
                                    paciente.getGenero()
                            );

                            dto.setTelefone(
                                    paciente.getTelefone()
                            );

                            if (paciente.getCidade() != null) {

                                dto.setCidade(
                                        paciente.getCidade()
                                                .getNome()
                                );
                            }

                            dto.setDataCadastro(
                                    paciente.getDataCadastro()
                            );

                            return dto;

                        })

                        .sorted(

                                Comparator.comparing(
                                        RelatorioPacienteDTO::getNome
                                )

                        )

                        .toList();

        RelatorioPacienteResponseDTO response =
                new RelatorioPacienteResponseDTO();

        response.setTotal(
                (long) lista.size()
        );

        response.setPacientes(
                lista
        );

        return response;
    }


    }

