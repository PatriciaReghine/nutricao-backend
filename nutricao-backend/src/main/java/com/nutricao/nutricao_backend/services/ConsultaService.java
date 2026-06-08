package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.dto.consulta.ConsultaAvaliacaoResponseDTO;
import com.nutricao.nutricao_backend.dto.consulta.ConsultaHistoricoDTO;
import com.nutricao.nutricao_backend.dto.consulta.ConsultaRequestDTO;
import com.nutricao.nutricao_backend.dto.consulta.ConsultaResponseDTO;
import com.nutricao.nutricao_backend.dto.nutricionista.NutricionistaDTO;
import com.nutricao.nutricao_backend.dto.paciente.PacienteAgendaDTO;
import com.nutricao.nutricao_backend.entidades.*;
import com.nutricao.nutricao_backend.enums.StatusConsulta;
import com.nutricao.nutricao_backend.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepositorie consultaRepositorie;

    @Autowired
    private PacienteRepositorie pacienteRepositorie;

    @Autowired
    private ItensAgendaRepositorie itensAgendaRepositorie;

    @Autowired
    private AvaliacaoFisicaRepositorie avaliacaoFisicaRepositorie;

    @Autowired
    private ProntuarioRepositorie prontuarioRepositorie;

    // ===============================
    // 🧾 SALVAR CONSULTA (ATENDIMENTO)
    // ===============================
    public ConsultaResponseDTO salvar(ConsultaRequestDTO dto) {

        // 🔹 busca item da agenda
        ItensAgenda item = itensAgendaRepositorie
                .findById(dto.getItemAgendaId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Agendamento não encontrado"
                        )
                );

        boolean consultaExiste =
                consultaRepositorie
                        .existsByItemAgendaId(
                                dto.getItemAgendaId()
                        );

        if (consultaExiste) {

            throw new RuntimeException(
                    "Já existe consulta para este agendamento"
            );
        }

        // 🔹 pega paciente do agendamento
        Paciente paciente = item.getPaciente();

        if (paciente == null) {
            throw new RuntimeException(
                    "Paciente não encontrado no agendamento"
            );
        }

        // 🔹 criar avaliação física
        AvaliacaoFisica avaliacao = new AvaliacaoFisica();

        avaliacao.setPeso(
                dto.getAvaliacao().getPeso()
        );

        avaliacao.setAltura(
                dto.getAvaliacao().getAltura()
        );

        avaliacao.setCircunferenciaAbdominal(
                dto.getAvaliacao().getCircunferenciaAbdominal()
        );

        avaliacao.setCircunferenciaQuadril(
                dto.getAvaliacao().getCircunferenciaQuadril()
        );

        avaliacao.setPlanejamentoAlimentar(
                dto.getAvaliacao().getPlanoAlimentar()
        );

        avaliacao.setPercentualGordura(
                dto.getAvaliacao().getPercentualGordura()
        );
        avaliacao.setImc(
                dto.getAvaliacao().getImc()
        );

        // 🔹 busca prontuário do paciente
        Prontuario prontuario =
                prontuarioRepositorie
                        .findByPacienteId(
                                paciente.getId()
                        )
                        .orElse(null);

        avaliacao.setProntuario(
                prontuario
        );

        avaliacao = avaliacaoFisicaRepositorie
                .save(avaliacao);

        // 🔹 criar consulta
        Consulta consulta = new Consulta();

        consulta.setPaciente(paciente);

        consulta.setItemAgenda(
                item);

        consulta.setTipoConsulta(
                dto.getTipoConsulta()
        );

        consulta.setResumo(
                dto.getResumo()
        );

        consulta.setObservacoes(
                dto.getObservacoes()
        );

        consulta.setData(
                LocalDate.now()
        );

        consulta.setAvaliacaoFisica(
                avaliacao
        );

        Consulta consultaSalva =
                consultaRepositorie.save(consulta);

        // 🔹 atualiza status do agendamento
        item.setStatusConsulta(StatusConsulta.REALIZADA);

        itensAgendaRepositorie.save(item);

        return toDTO(consultaSalva);
    }

    // ===============================
    // ===============================
// 🔍 BUSCAR CONSULTA POR ID
// ===============================
    public ConsultaResponseDTO buscarPorId(Long id) {

        Consulta consulta = consultaRepositorie.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Consulta não encontrada"
                        )
                );

        return toDTO(consulta);
    }

    // ===============================
// 🔄 MAPPER (COM IMC)
// ===============================
    private ConsultaResponseDTO toDTO(Consulta consulta) {

        ConsultaResponseDTO dto =
                new ConsultaResponseDTO();

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

        dto.setObservacoes(
                consulta.getObservacoes()
        );

        AvaliacaoFisica avaliacao =
                consulta.getAvaliacaoFisica();

        ConsultaAvaliacaoResponseDTO avaliacaoDTO =
                new ConsultaAvaliacaoResponseDTO();

        avaliacaoDTO.setPeso(
                avaliacao.getPeso()
        );

        avaliacaoDTO.setAltura(
                avaliacao.getAltura()
        );

        avaliacaoDTO.setCircunferenciaAbdominal(
                avaliacao.getCircunferenciaAbdominal()
        );

        avaliacaoDTO.setCircunferenciaQuadril(
                avaliacao.getCircunferenciaQuadril()
        );

        avaliacaoDTO.setPlanoAlimentar(
                avaliacao.getPlanejamentoAlimentar()
        );

        avaliacaoDTO.setPercentualGordura(
                avaliacao.getPercentualGordura()
        );

        avaliacaoDTO.setImc(
                avaliacao.getImc()
        );

        // 🔹 NUTRICIONISTA
        Usuario nutricionista =
                consulta.getItemAgenda()
                        .getAgenda()
                        .getUsuario();

        NutricionistaDTO nutricionistaDTO =
                new NutricionistaDTO();

        nutricionistaDTO.setId(
                nutricionista.getId()
        );

        nutricionistaDTO.setNome(
                nutricionista.getNome()
        );

        nutricionistaDTO.setCrn(
                nutricionista.getCrn()
        );

        nutricionistaDTO.setEspecialidade(
                nutricionista.getEspecialidade()
        );

        dto.setNutricionista(
                nutricionistaDTO
        );

        Paciente paciente =
                consulta.getPaciente();

        if (paciente != null) {

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

            pacienteDTO.setCidade(
                    paciente.getCidade().getNome()
            );

            dto.setPaciente(
                    pacienteDTO
            );
        }

        dto.setAvaliacao(avaliacaoDTO);

        return dto;
    }

    public ConsultaResponseDTO atualizar(
            Long id,
            ConsultaRequestDTO dto
    ) {

        Consulta consulta = consultaRepositorie
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Consulta não encontrada"
                        )
                );

        consulta.setTipoConsulta(
                dto.getTipoConsulta()
        );

        consulta.setResumo(
                dto.getResumo()
        );

        consulta.setObservacoes(
                dto.getObservacoes()
        );

        AvaliacaoFisica avaliacao =
                consulta.getAvaliacaoFisica();

        avaliacao.setPeso(
                dto.getAvaliacao().getPeso()
        );

        avaliacao.setAltura(
                dto.getAvaliacao().getAltura()
        );

        avaliacao.setCircunferenciaAbdominal(
                dto.getAvaliacao()
                        .getCircunferenciaAbdominal()
        );

        avaliacao.setCircunferenciaQuadril(
                dto.getAvaliacao()
                        .getCircunferenciaQuadril()
        );

        avaliacao.setPercentualGordura(
                dto.getAvaliacao()
                        .getPercentualGordura()
        );

        avaliacao.setPlanejamentoAlimentar(
                dto.getAvaliacao()
                        .getPlanoAlimentar()
        );

        // recalcula IMC
        if (
                avaliacao.getAltura() != null
                        && avaliacao.getAltura() != 0
        ) {

            avaliacao.setImc(
                    dto.getAvaliacao().getImc()
            );
        }

        avaliacaoFisicaRepositorie.save(avaliacao);

        Consulta consultaAtualizada =
                consultaRepositorie.save(consulta);

        return toDTO(consultaAtualizada);

    }

    //    BUSCAR ULTIMA CONSULTA

    public ConsultaResponseDTO buscarUltimaConsulta(
            Long pacienteId
    ) {

        Consulta consulta = consultaRepositorie
                .findTopByItemAgendaPacienteIdAndVisivelTrueOrderByDataDesc(
                        pacienteId
                )
                .orElse(null);

        if (consulta == null) {
            return null;
        }

        return toDTO(consulta);
    }

    public List<ConsultaHistoricoDTO> listarHistorico(
            Long pacienteId
    ) {

        return consultaRepositorie
                .findByPacienteIdAndVisivelTrueOrderByDataDesc(
                        pacienteId
                )
                .stream()
                .map(consulta -> {

                    ConsultaHistoricoDTO dto =
                            new ConsultaHistoricoDTO();

                    dto.setId(
                            consulta.getId()
                    );

                    dto.setData(
                            consulta.getData().toString()
                    );

                    dto.setTipoConsulta(
                            consulta.getTipoConsulta().toString()
                    );

                    dto.setResumo(
                            consulta.getResumo()
                    );

                    prontuarioRepositorie
                            .findByPacienteId(
                                    consulta.getPaciente().getId()
                            )
                            .ifPresent(prontuario -> {

                                dto.setNumeroProntuario(
                                        prontuario.getNumeroProntuario()
                                );

                            });


                    return dto;

                }).toList();
    }
    public void alterarVisibilidade(
            Long id,
            Boolean visivel
    ) {

        Consulta consulta =
                consultaRepositorie.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Consulta não encontrada"
                                )
                        );

        consulta.setVisivel(visivel);

        consultaRepositorie.save(consulta);
    }

    public List<ConsultaResponseDTO> listarOcultas(
            Long pacienteId
    ) {

        List<Consulta> consultas =
                consultaRepositorie
                        .findByPacienteIdAndVisivelFalseOrderByDataDesc(
                                pacienteId
                        );

        return consultas.stream()
                .map(this::toDTO)
                .toList();
    }
}


