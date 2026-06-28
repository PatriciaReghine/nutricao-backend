package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.dto.agenda.AgendarConsultaDTO;
import com.nutricao.nutricao_backend.dto.consulta.ConsultaAgendaDTO;
import com.nutricao.nutricao_backend.dto.consulta.ConsultaSlotDTO;
import com.nutricao.nutricao_backend.dto.paciente.PacienteAgendaDTO;
import com.nutricao.nutricao_backend.entidades.*;
import com.nutricao.nutricao_backend.enums.StatusAgenda;
import com.nutricao.nutricao_backend.enums.StatusConsulta;
import com.nutricao.nutricao_backend.repositories.*;

import com.nutricao.nutricao_backend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendaRepositorie agendaRepositorie;

    @Autowired
    private ItensAgendaRepositorie itensAgendaRepositorie;

    @Autowired
    private PacienteRepositorie pacienteRepositorie;

    @Autowired
    private UsuarioRepositorie usuarioRepositorie;

    // ===============================
    // 📅 LISTAR AGENDAMENTOS DO DIA
    // ===============================
    public List<ConsultaAgendaDTO> listarAgendamentosDoDia(
            Long nutricionistaId,
            String dataStr) {

        LocalDate data = LocalDate.parse(dataStr);

        Agenda agenda = agendaRepositorie
                .findByUsuarioIdAndData(nutricionistaId, data)
                .orElse(null);

        List<String> horarios = List.of(
                "07:00","07:30","08:00","08:30",
                "09:00","09:30","10:00","10:30",
                "11:00","11:30","13:00","13:30",
                "14:00","14:30","15:00","15:30",
                "16:00","16:30","17:00","17:30"
        );

        if (agenda == null) {

            return horarios.stream().map(hora -> {

                ConsultaAgendaDTO dto =
                        new ConsultaAgendaDTO();

                dto.setHora(hora);
                dto.setSlotStatus("DISPONIVEL");

                return dto;

            }).toList();
        }

        List<ItensAgenda> itens =
                itensAgendaRepositorie
                        .findByAgendaIdOrderByHorarioAsc(
                                agenda.getId()
                        );

        return horarios.stream().map(hora -> {

            ConsultaAgendaDTO dto =
                    new ConsultaAgendaDTO();

            dto.setHora(hora);

            List<ItensAgenda> itensHorario =
                    itens.stream()
                            .filter(i ->
                                    i.getHorario()
                                            .toString()
                                            .equals(hora)
                            )
                            .toList();

            if (itensHorario.isEmpty()) {

                dto.setSlotStatus("DISPONIVEL");

                return dto;
            }

            dto.setSlotStatus("OCUPADO");

            List<ConsultaSlotDTO> agendamentos =
                    itensHorario.stream()
                            .map(item -> {

                                ConsultaSlotDTO agendamento =
                                        new ConsultaSlotDTO();

                                agendamento.setId(
                                        item.getId()
                                );

                                if (item.getStatusConsulta() != null) {

                                    agendamento.setAgendamentoStatus(
                                            item.getStatusConsulta().name()
                                    );
                                }

                                if (item.getPaciente() != null) {

                                    Paciente paciente =
                                            item.getPaciente();

                                    PacienteAgendaDTO pacienteDTO =
                                            new PacienteAgendaDTO();

                                    pacienteDTO.setId(
                                            paciente.getId()
                                    );

                                    pacienteDTO.setNome(
                                            paciente.getNome()
                                    );

                                    pacienteDTO.setCpf(
                                            paciente.getCpf()
                                    );

                                    pacienteDTO.setTelefone(
                                            paciente.getTelefone()
                                    );

                                    pacienteDTO.setEmail(
                                            paciente.getEmail()
                                    );

                                    pacienteDTO.setGenero(
                                            paciente.getGenero()
                                    );

                                    pacienteDTO.setDataNascimento(
                                            paciente.getDataNascimento()
                                    );

                                    pacienteDTO.setDataCadastro(
                                            paciente.getDataCadastro()
                                    );

                                    pacienteDTO.setLogradouro(
                                            paciente.getLogradouro()
                                    );

                                    pacienteDTO.setNumero(
                                            paciente.getNumero()
                                    );

                                    pacienteDTO.setBairro(
                                            paciente.getBairro()
                                    );

                                    if (paciente.getCidade() != null) {

                                        pacienteDTO.setCidade(
                                                paciente.getCidade().getNome()
                                        );
                                    }

                                    agendamento.setPaciente(
                                            pacienteDTO
                                    );
                                }

                                return agendamento;

                            }).toList();

            dto.setAgendamentos(
                    agendamentos
            );

            return dto;

        }).toList();
    }

// ===============================
// 📌 AGENDAR CONSULTA
// ===============================
    public void agendarConsulta(
            AgendarConsultaDTO dto,
            String token
    ) {

        String perfil =
                JwtService.getPerfil(token);

        Long usuarioLogadoId =
                JwtService.getIdFromToken(token);

        // 🔥 REGRA:
        // nutricionista só agenda na própria agenda
        Long nutricionistaId =
                dto.getNutricionistaId();

        if ("NUTRICIONISTA".equalsIgnoreCase(perfil)) {

            nutricionistaId = usuarioLogadoId;
        }

        LocalDate data =
                LocalDate.parse(dto.getData());

        LocalTime horario =
                LocalTime.parse(dto.getHora());

        // 🔥 variável final para usar no lambda
        final Long nutricionistaFinalId =
                nutricionistaId;

        // procura agenda ou cria automaticamente
        Agenda agenda = agendaRepositorie
                .findByUsuarioIdAndData(
                        nutricionistaId,
                        data
                )
                .orElseGet(() -> {

                    Usuario usuario = usuarioRepositorie
                            .findById(nutricionistaFinalId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Usuário não encontrado"
                                    )
                            );

                    // 🔥 VALIDA SE ESTÁ ATIVA
                    if (!usuario.getAtivo()) {

                        throw new RuntimeException(
                                "Não é possível agendar para uma nutricionista inativa"
                        );
                    }


                    Agenda nova = new Agenda();

                    nova.setUsuario(usuario);
                    nova.setData(data);
                    nova.setStatus(StatusAgenda.ABERTA);

                    return agendaRepositorie.save(nova);
                });

        // reabre agenda automaticamente
        if (agenda.getStatus() == StatusAgenda.FINALIZADA) {

            agenda.setStatus(StatusAgenda.ABERTA);

            agendaRepositorie.save(agenda);
        }

        // procura horário
        List<ItensAgenda> itensMesmoHorario =
                itensAgendaRepositorie
                        .findByAgendaIdOrderByHorarioAsc(
                                agenda.getId()
                        )
                        .stream()
                        .filter(i ->
                                i.getHorario().equals(horario)
                        )
                        .toList();

        boolean horarioOcupado =
                itensMesmoHorario.stream()
                        .anyMatch(i ->

                                i.getStatusConsulta() != StatusConsulta.CANCELADA

                                        &&

                                        i.getStatusConsulta() != StatusConsulta.AUSENTE
                        );

        if (horarioOcupado) {

            throw new RuntimeException(
                    "Horário já ocupado"
            );
        }

        // cria item se não existir
        ItensAgenda item = new ItensAgenda();

        item.setAgenda(agenda);

        item.setHorario(horario);



        // busca paciente
        Paciente paciente = pacienteRepositorie
                .findById(dto.getPacienteId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Paciente não encontrado"
                        )
                );
        boolean pacienteMesmoHorario =
                itensMesmoHorario.stream()
                        .anyMatch(agendamento ->

                                agendamento.getPaciente() != null

                                        &&

                                        agendamento.getPaciente().getId()
                                                .equals(paciente.getId())

                                        &&

                                        agendamento.getStatusConsulta() != StatusConsulta.CANCELADA
                                        &&

                                        agendamento.getStatusConsulta() != StatusConsulta.AUSENTE
                        );

        if (pacienteMesmoHorario) {

            throw new RuntimeException(
                    "Paciente já possui agendamento neste horário."
            );
        }

        // salva consulta
        item.setPaciente(paciente);

        item.setStatusConsulta(StatusConsulta.AGENDADA);

        itensAgendaRepositorie.save(item);
    }

    // ===============================
    // ❌ CANCELAR CONSULTA
    // ===============================
    public void cancelar(Long id) {

        ItensAgenda item = itensAgendaRepositorie
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Consulta não encontrada"
                        )
                );

        if (item.getAgenda().getStatus() == StatusAgenda.FINALIZADA){

            throw new RuntimeException(
                    "Agenda finalizada"
            );
        }

        // 🔥 libera horário
        item.setStatusConsulta(
                StatusConsulta.CANCELADA
        );

        itensAgendaRepositorie.save(item);
    }

    // ===============================
    // 🔄 ATUALIZAR STATUS
    // ===============================
    public void atualizarStatus(
            Long id,
            StatusConsulta status
    ) {

        ItensAgenda item = itensAgendaRepositorie
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Consulta não encontrada"
                        )
                );

        item.setStatusConsulta(status);


        itensAgendaRepositorie.save(item);
    }
}