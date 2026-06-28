package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.dto.relatorios.*;
import com.nutricao.nutricao_backend.entidades.Agenda;
import com.nutricao.nutricao_backend.entidades.Consulta;
import com.nutricao.nutricao_backend.entidades.Paciente;
import com.nutricao.nutricao_backend.entidades.Usuario;
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

    public RelatorioConsultaResponseDTO relatorioConsultas(
            LocalDate dataInicio,
            LocalDate dataFim,
            Long nutricionistaId,
            Long pacienteId,
            TipoConsulta tipoConsulta,
            StatusConsulta statusConsulta
    ) {

        List<Consulta> consultas =
                consultaRepositorie.findAll();

        // ===============================
        // FILTRO DATA INÍCIO
        // ===============================

        if (dataInicio != null) {

            consultas = consultas.stream()

                    .filter(consulta ->
                            !consulta.getData().isBefore(dataInicio)
                    )

                    .toList();
        }

        // ===============================
        // FILTRO DATA FIM
        // ===============================

        if (dataFim != null) {

            consultas = consultas.stream()

                    .filter(consulta ->
                            !consulta.getData().isAfter(dataFim)
                    )

                    .toList();
        }

        // ===============================
        // FILTRO PACIENTE
        // ===============================

        if (pacienteId != null) {

            consultas = consultas.stream()

                    .filter(consulta ->

                            consulta.getPaciente() != null

                                    &&

                                    consulta.getPaciente()
                                            .getId()
                                            .equals(pacienteId)
                    )

                    .toList();
        }

        // ===============================
        // FILTRO NUTRICIONISTA
        // ===============================

        if (nutricionistaId != null) {

            consultas = consultas.stream()

                    .filter(consulta ->

                            consulta.getItemAgenda() != null

                                    &&

                                    consulta.getItemAgenda().getAgenda() != null

                                    &&

                                    consulta.getItemAgenda()
                                            .getAgenda()
                                            .getUsuario() != null

                                    &&

                                    consulta.getItemAgenda()
                                            .getAgenda()
                                            .getUsuario()
                                            .getId()
                                            .equals(nutricionistaId)
                    )

                    .toList();
        }

        // ===============================
        // FILTRO TIPO CONSULTA
        // ===============================

        if (tipoConsulta != null) {

            consultas = consultas.stream()

                    .filter(consulta ->
                            consulta.getTipoConsulta() == tipoConsulta
                    )

                    .toList();
        }

        // ===============================
        // FILTRO STATUS CONSULTA
        // ===============================

        if (statusConsulta != null) {

            consultas = consultas.stream()

                    .filter(consulta ->

                            consulta.getItemAgenda() != null

                                    &&

                                    consulta.getItemAgenda()
                                            .getStatusConsulta() == statusConsulta
                    )

                    .toList();
        }

        // ===============================
        // MONTA DTO
        // ===============================

        List<RelatorioConsultaDTO> lista =
                consultas.stream()

                        .map(consulta -> {

                            RelatorioConsultaDTO dto =
                                    new RelatorioConsultaDTO();

                            dto.setData(
                                    consulta.getData()
                            );

                            dto.setHora(
                                    consulta.getItemAgenda()
                                            .getHorario()
                            );

                            dto.setPaciente(
                                    consulta.getPaciente()
                                            .getNome()
                            );

                            dto.setNutricionista(
                                    consulta.getItemAgenda()
                                            .getAgenda()
                                            .getUsuario()
                                            .getNome()
                            );

                            dto.setTipoConsulta(
                                    consulta.getTipoConsulta()
                            );

                            dto.setStatus(
                                    consulta.getItemAgenda()
                                            .getStatusConsulta()
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

        // ===============================
        // RESPONSE
        // ===============================

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

        // ===============================
        // FILTRO DATA INÍCIO
        // ===============================

        if (dataInicio != null) {

            agendas = agendas.stream()

                    .filter(agenda ->
                            !agenda.getData().isBefore(dataInicio)
                    )

                    .toList();
        }

        // ===============================
        // FILTRO DATA FIM
        // ===============================

        if (dataFim != null) {

            agendas = agendas.stream()

                    .filter(agenda ->
                            !agenda.getData().isAfter(dataFim)
                    )

                    .toList();
        }

        // ===============================
        // FILTRO NUTRICIONISTA
        // ===============================

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

        // ===============================
        // FILTRO STATUS
        // ===============================

        if (status != null) {

            agendas = agendas.stream()

                    .filter(agenda ->
                            agenda.getStatus() == status
                    )

                    .toList();
        }

        // ===============================
        // MONTA DTO
        // ===============================

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

        // ===============================
        // RESPONSE
        // ===============================

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
            String perfil,
            Boolean status
    ) {

        List<Usuario> usuarios =
                usuarioRepositorie.findAll();

        // ===============================
        // FILTRO PERFIL
        // ===============================

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

        // ===============================
        // FILTRO STATUS
        // ===============================

        if (status != null) {

            usuarios = usuarios.stream()

                    .filter(usuario ->

                            usuario.getAtivo()
                                    .equals(status)
                    )

                    .toList();
        }

        // ===============================
        // DTO
        // ===============================

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

        // ===============================
        // DATA FIM
        // ===============================

        if (dataFim != null) {

            pacientes = pacientes.stream()

                    .filter(paciente ->
                            !paciente.getDataCadastro()
                                    .isAfter(dataFim)
                    )

                    .toList();
        }

        // ===============================
        // CIDADE
        // ===============================

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

        // ===============================
        // GÊNERO
        // ===============================

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

// FAIXA ETÁRIA
// ===============================

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

