package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.dto.dashboard.*;
import com.nutricao.nutricao_backend.entidades.*;
import com.nutricao.nutricao_backend.enums.StatusConsulta;
import com.nutricao.nutricao_backend.repositories.*;
import com.nutricao.nutricao_backend.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private AgendaRepositorie agendaRepositorie;

    @Autowired
    private ItensAgendaRepositorie itensAgendaRepositorie;

    @Autowired
    private UsuarioRepositorie usuarioRepositorie;

    @Autowired
    private PacienteRepositorie pacienteRepositorie;

    @Autowired
    private ConsultaRepositorie consultaRepositorie;

    @Autowired
    private ProntuarioRepositorie prontuarioRepositorie;

    // ==========================================
    // MÉTODO PRINCIPAL
    // ==========================================
    public DashboardResponseDTO buscarDashboard(
            String dataStr,
            Long pacienteId,
            String token
    ) {

        Long usuarioId =
                JwtService.getIdFromToken(token);

        String perfil =
                JwtService.getPerfil(token);

        LocalDate data =
                LocalDate.parse(dataStr);

        if ("NUTRICIONISTA".equalsIgnoreCase(perfil)) {
            return dashboardNutricionista(
                    usuarioId,
                    data,
                    pacienteId
            );
        }

        if ("SECRETARIA".equalsIgnoreCase(perfil)) {
            return dashboardSecretaria(data);
        }

        if ("ADMINISTRADOR".equalsIgnoreCase(perfil)) {
            return dashboardAdmin(data);
        }

        throw new RuntimeException("Perfil não autorizado");
    }

    // ==========================================
    // NUTRICIONISTA
    // ==========================================
    private DashboardResponseDTO dashboardNutricionista(
            Long usuarioId,
            LocalDate data,
            Long pacienteId
    ) {

        DashboardResponseDTO response =
                new DashboardResponseDTO();

        Agenda agenda = agendaRepositorie
                .findByUsuarioIdAndData(usuarioId, data)
                .orElse(null);

        List<ItensAgenda> itens =
                agenda != null
                        ? itensAgendaRepositorie.findByAgendaIdOrderByHorarioAsc(agenda.getId())
                        : new ArrayList<>();

        List<DashboardAgendaDTO> agendamentos =
                itens.stream().map(item -> {

                    DashboardAgendaDTO dto =
                            new DashboardAgendaDTO();

                    dto.setItemAgendaId(item.getId());

                    dto.setHora(
                            item.getHorario().toString()
                    );

                    dto.setStatus(
                            item.getStatusConsulta().name()
                    );

                    if (item.getConsulta() != null) {

                        DashboardConsultaDTO consultaDTO =
                                new DashboardConsultaDTO();

                        consultaDTO.setId(
                                item.getConsulta().getId()
                        );

                        dto.setConsulta(consultaDTO);
                    }

                    if (item.getPaciente() != null) {

                        DashboardPacienteResumoDTO pacienteDTO =
                                new DashboardPacienteResumoDTO();

                        pacienteDTO.setId(
                                item.getPaciente().getId()
                        );

                        pacienteDTO.setNome(
                                item.getPaciente().getNome()
                        );
                        pacienteDTO.setDataNascimento(
                                item.getPaciente().getDataNascimento()
                        );

                        dto.setPaciente(pacienteDTO);
                    }

                    return dto;

                }).toList();

        response.setAgendamentosHoje(
                agendamentos
        );


        // SEM PACIENTE
        if (pacienteId == null) {

            response.setPacienteSelecionado(null);
            response.setHistorico(new ArrayList<>());

            return response;
        }

        // PACIENTE
        Paciente paciente = pacienteRepositorie
                .findById(pacienteId)
                .orElseThrow(() ->
                        new RuntimeException("Paciente não encontrado")
                );

        DashboardPacienteDTO pacienteDTO =
                new DashboardPacienteDTO();

        pacienteDTO.setId(paciente.getId());

        pacienteDTO.setNome(
                paciente.getNome()
        );
        pacienteDTO.setDataNascimento(
                paciente.getDataNascimento()
        );

        pacienteDTO.setGenero(
                paciente.getGenero()
        );

        int idade =
                Period.between(
                        paciente.getDataNascimento(),
                        LocalDate.now()
                ).getYears();

        pacienteDTO.setIdade(idade);

        // PRONTUÁRIO
        prontuarioRepositorie
                .findByPacienteId(pacienteId)
                .ifPresent(prontuario -> {

                    DashboardProntuarioDTO prontuarioDTO =
                            new DashboardProntuarioDTO();

                    prontuarioDTO.setId(
                            prontuario.getId()
                    );
                    prontuarioDTO.setNumero(
                            prontuario.getNumeroProntuario()
                    );

                    prontuarioDTO.setObjetivo(
                            prontuario.getObjetivo()
                    );

                    prontuarioDTO.setInformacoesClinicas(
                            prontuario.getInformacoesClinicas()
                    );

                    prontuarioDTO.setRestricaoAlimentar(
                            prontuario.getRestricaoAlimentar()
                    );

                    pacienteDTO.setProntuario(prontuarioDTO);
                });

        response.setPacienteSelecionado(pacienteDTO);

        // HISTÓRICO
        List<DashboardHistoricoDTO> historico =
                consultaRepositorie
                        .findByPacienteIdAndVisivelTrueOrderByDataDesc(pacienteId)
                        .stream()
                        .map(consulta -> {

                            DashboardHistoricoDTO dto =
                                    new DashboardHistoricoDTO();

                            dto.setId(
                                    consulta.getId()
                            );

                            dto.setData(
                                    consulta.getData().toString()
                            );

                            dto.setTipoConsulta(
                                    consulta.getTipoConsulta()
                            );

                            dto.setResumo(
                                    consulta.getResumo()
                            );

                            return dto;

                        }).toList();

        response.setAgendamentosHoje(agendamentos);

        response.setHistorico(historico);

        return response;
    }

    // ==========================================
    // SECRETÁRIA
    // ==========================================
    private DashboardResponseDTO dashboardSecretaria(
            LocalDate data
    ) {

        DashboardResponseDTO response =
                new DashboardResponseDTO();

        List<Usuario> nutricionistas =
                usuarioRepositorie.findAll()
                        .stream()
                        .filter(u ->
                                u.getPerfil() != null
                                        &&
                                        "NUTRICIONISTA".equalsIgnoreCase(
                                                u.getPerfil().getNomePerfil()
                                        )
                        )
                        .toList();

        List<DashboardNutricionistaResumoDTO> resumo =
                nutricionistas.stream()
                        .map(nutri -> {

                            Long pendentes =
                                    itensAgendaRepositorie
                                            .findByAgendaUsuarioIdAndAgendaData(
                                                    nutri.getId(),
                                                    data
                                            )
                                            .stream()
                                            .filter(item ->

                                                    item.getStatusConsulta() != null

                                                            &&

                                                            (
                                                                    item.getStatusConsulta().name().equals("AGENDADA")

                                                                            ||

                                                                            item.getStatusConsulta().name().equals("CONFIRMADA")
                                                            )
                                            )
                                            .count();

                            List<ItensAgenda> itensDia =
                                    itensAgendaRepositorie
                                            .findByAgendaUsuarioIdAndAgendaData(
                                                    nutri.getId(),
                                                    data
                                            );

                            Long agendadas =
                                    itensDia.stream()
                                            .filter(item ->
                                                    item.getStatusConsulta() == StatusConsulta.AGENDADA
                                            )
                                            .count();

                            Long confirmadas =
                                    itensDia.stream()
                                            .filter(item ->
                                                    item.getStatusConsulta() == StatusConsulta.CONFIRMADA
                                            )
                                            .count();

                            Long realizadas =
                                    itensDia.stream()
                                            .filter(item ->
                                                    item.getStatusConsulta() == StatusConsulta.REALIZADA
                                            )
                                            .count();

                            Long canceladas =
                                    itensDia.stream()
                                            .filter(item ->
                                                    item.getStatusConsulta() == StatusConsulta.CANCELADA
                                            )
                                            .count();

                            Long ausentes =
                                    itensDia.stream()
                                            .filter(item ->
                                                    item.getStatusConsulta() == StatusConsulta.AUSENTE
                                            )
                                            .count();

                            Long totalHoje =
                                    (long) itensDia.size();

                            String proximaConsulta =
                                    itensDia.stream()

                                            .filter(item ->

                                                    item.getStatusConsulta() != null

                                                            &&

                                                            (
                                                                    item.getStatusConsulta().name().equals("AGENDADA")

                                                                            ||

                                                                            item.getStatusConsulta().name().equals("CONFIRMADA")
                                                            )
                                            )

                                            .sorted(
                                                    java.util.Comparator.comparing(
                                                            ItensAgenda::getHorario
                                                    )
                                            )

                                            .map(item ->
                                                    item.getHorario().toString()
                                            )

                                            .findFirst()

                                            .orElse(null);

                            DashboardNutricionistaResumoDTO dto =
                                    new DashboardNutricionistaResumoDTO();

                            dto.setNutricionistaId(
                                    nutri.getId()
                            );

                            dto.setNome(
                                    nutri.getNome()
                            );

                            dto.setProximaConsulta(
                                    proximaConsulta
                            );
                            dto.setAgendadas(
                                    agendadas
                            );

                            dto.setConfirmadas(
                                    confirmadas
                            );

                            dto.setRealizadas(
                                    realizadas
                            );

                            dto.setCanceladas(
                                    canceladas
                            );

                            dto.setAusentes(
                                    ausentes
                            );

                            dto.setTotalHoje(
                                    totalHoje
                            );

                            dto.setCrn(
                                    nutri.getCrn()
                            );

                            return dto;



                        })
                        .sorted(
                                java.util.Comparator
                                        .comparing(
                                                DashboardNutricionistaResumoDTO::getTotalHoje,
                                                java.util.Comparator.reverseOrder()
                                        )
                                        .thenComparing(
                                                DashboardNutricionistaResumoDTO::getNome
                                        )
                        )
                        .toList();

        response.setResumoNutricionistas(
                resumo
        );

        return response;
    }
    // ==========================================
// ADMIN
// ==========================================
    private DashboardResponseDTO dashboardAdmin(
            LocalDate data
    ) {

        DashboardResponseDTO response =
                new DashboardResponseDTO();

        // Estatísticas gerais
        response.setTotalUsuarios(
                usuarioRepositorie.countByAtivoTrue()
        );

        response.setTotalPacientes(
                pacienteRepositorie.count()
        );

        Long agendamentosMes =
                itensAgendaRepositorie.findAll()
                        .stream()
                        .filter(item ->
                                item.getAgenda() != null
                        )
                        .count();

        response.setAgendamentosMes(
                agendamentosMes
        );

        // Total de consultas do dia (todas as nutricionistas)
        Long consultasHoje =
                itensAgendaRepositorie.findAll()
                        .stream()
                        .filter(item ->

                                item.getAgenda() != null

                                        &&

                                        item.getAgenda()
                                                .getData()
                                                .equals(data)

                                        &&

                                        item.getStatusConsulta() != null
                        )
                        .count();

        response.setConsultasHoje(
                consultasHoje
        );

        // Resumo por nutricionista
        List<Usuario> nutricionistas =
                usuarioRepositorie.findAll()
                        .stream()
                        .filter(usuario ->
                                usuario.getPerfil() != null
                                        &&
                                        "NUTRICIONISTA".equalsIgnoreCase(
                                                usuario.getPerfil().getNomePerfil()
                                        )
                        )
                        .toList();

        List<DashboardNutricionistaResumoDTO> resumo =
                nutricionistas.stream()
                        .map(nutricionista -> {

                            List<ItensAgenda> itensDia =
                                    itensAgendaRepositorie
                                            .findByAgendaUsuarioIdAndAgendaData(
                                                    nutricionista.getId(),
                                                    data
                                            );

                            Long agendadas =
                                    itensDia.stream()
                                            .filter(item ->
                                                    item.getStatusConsulta() != null
                                                            &&
                                                            item.getStatusConsulta().name()
                                                                    .equals("AGENDADA")
                                            )
                                            .count();

                            Long confirmadas =
                                    itensDia.stream()
                                            .filter(item ->
                                                    item.getStatusConsulta() != null
                                                            &&
                                                            item.getStatusConsulta().name()
                                                                    .equals("CONFIRMADA")
                                            )
                                            .count();



                            Long canceladas =
                                    itensDia.stream()
                                            .filter(item ->
                                                    item.getStatusConsulta() != null
                                                            &&
                                                            item.getStatusConsulta().name()
                                                                    .equals("CANCELADA")
                                            )
                                            .count();

                            Long ausentes =
                                    itensDia.stream()
                                            .filter(item ->
                                                    item.getStatusConsulta() != null
                                                            &&
                                                            item.getStatusConsulta().name()
                                                                    .equals("AUSENTE")
                                            )
                                            .count();

                            Long realizadas =
                                    itensAgendaRepositorie
                                            .findByAgendaUsuarioIdAndAgendaData(
                                                    nutricionista.getId(),
                                                    data
                                            )
                                            .stream()
                                            .filter(item ->

                                                    item.getStatusConsulta() ==
                                                            StatusConsulta.REALIZADA
                                            )
                                            .count();

                            Long totalHoje =
                                    (long) itensDia.size();

                            String proximaConsulta =
                                    itensDia.stream()

                                            .filter(item ->

                                                    item.getStatusConsulta() != null

                                                            &&

                                                            (
                                                                    item.getStatusConsulta().name().equals("AGENDADA")

                                                                            ||

                                                                            item.getStatusConsulta().name().equals("CONFIRMADA")
                                                            )
                                            )

                                            .sorted(
                                                    java.util.Comparator.comparing(
                                                            ItensAgenda::getHorario
                                                    )
                                            )

                                            .map(item ->
                                                    item.getHorario().toString()
                                            )

                                            .findFirst()

                                            .orElse(null);

                            DashboardNutricionistaResumoDTO dto =
                                    new DashboardNutricionistaResumoDTO();

                            dto.setNutricionistaId(
                                    nutricionista.getId()
                            );

                            dto.setNome(
                                    nutricionista.getNome()
                            );

                            dto.setCrn(
                                    nutricionista.getCrn()
                            );

                            dto.setAgendadas(
                                    agendadas
                            );

                            dto.setConfirmadas(
                                    confirmadas
                            );

                            dto.setCanceladas(
                                    canceladas
                            );
                            dto.setRealizadas(
                                    realizadas
                            );

                            dto.setTotalHoje(
                                    totalHoje
                            );

                            dto.setProximaConsulta(
                                    proximaConsulta
                            );

                            dto.setAusentes(
                                    ausentes
                            );

                            return dto;

                        })

                        .sorted(
                                java.util.Comparator
                                        .comparing(
                                                DashboardNutricionistaResumoDTO::getTotalHoje,
                                                java.util.Comparator.reverseOrder()
                                        )
                                        .thenComparing(
                                                DashboardNutricionistaResumoDTO::getNome
                                        )
                        )
                        .toList();

        response.setResumoNutricionistas(
                resumo
        );

        return response;
    }


}