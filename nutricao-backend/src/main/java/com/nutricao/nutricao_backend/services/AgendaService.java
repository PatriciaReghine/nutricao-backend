package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.dto.agenda.AgendaCreateDTO;
import com.nutricao.nutricao_backend.dto.agenda.AgendaDiaDTO;
import com.nutricao.nutricao_backend.dto.agenda.AgendaDiaResponseDTO;
import com.nutricao.nutricao_backend.dto.agenda.AgendaListResponseDTO;
import com.nutricao.nutricao_backend.dto.nutricionista.NutricionistaAgendaDTO;
import com.nutricao.nutricao_backend.dto.nutricionista.NutricionistaDTO;
import com.nutricao.nutricao_backend.entidades.Agenda;
import com.nutricao.nutricao_backend.entidades.Usuario;
import com.nutricao.nutricao_backend.enums.StatusAgenda;
import com.nutricao.nutricao_backend.enums.StatusConsulta;
import com.nutricao.nutricao_backend.repositories.AgendaRepositorie;
import com.nutricao.nutricao_backend.repositories.UsuarioRepositorie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepositorie agendaRepositorie;

    @Autowired
    private UsuarioRepositorie usuarioRepositorie;

    // Criar/ abrir agenda do dia

    public void criarAgenda(AgendaCreateDTO dto) {

        LocalDate data = LocalDate.parse(dto.getData());

        boolean existe = agendaRepositorie
                .findByUsuarioIdAndData(
                        dto.getNutricionistaId(),
                        data
                )
                .isPresent();

        if (existe) {

            throw new RuntimeException(
                    "Já existe uma agenda para esse dia"
            );
        }

        Usuario usuario = usuarioRepositorie
                .findById(dto.getNutricionistaId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Usuário não encontrado"
                        )
                );

        Agenda agenda = new Agenda();

        agenda.setUsuario(usuario);
        agenda.setData(data);
        agenda.setStatus(dto.getStatus());

        agendaRepositorie.save(agenda);
    }

// Listar Agendas por status

    public List<AgendaListResponseDTO> listarAgendas(
            StatusAgenda status,
            Long nutricionistaId,
            Integer mes,
            Integer ano
    ) {

        // se não vier mês/ano
        if (mes == null || ano == null) {

            mes = LocalDate.now().getMonthValue();

            ano = LocalDate.now().getYear();
        }

        List<Agenda> agendas =
                agendaRepositorie.buscarAgendas(
                        status,
                        mes,
                        ano,
                        nutricionistaId
                );

        return agendas.stream().map(agenda -> {

            AgendaListResponseDTO dto =
                    new AgendaListResponseDTO();

            dto.setId(
                    agenda.getId()
            );

            dto.setData(
                    agenda.getData().toString()
            );

            dto.setStatus(
                    agenda.getStatus()
            );

            NutricionistaAgendaDTO nutricionistaDTO =
                    new NutricionistaAgendaDTO();

            nutricionistaDTO.setId(
                    agenda.getUsuario().getId()
            );

            nutricionistaDTO.setNome(
                    agenda.getUsuario().getNome()
            );

            nutricionistaDTO.setCrn(
                    agenda.getUsuario().getCrn()
            );

            dto.setNutricionista(
                    nutricionistaDTO
            );

            return dto;

        }).toList();
    }

    //  Mudança Status Agenda

    public void atualizarStatusAgenda(
            Long id,
            StatusAgenda status
    ) {

        Agenda agenda = agendaRepositorie
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Agenda não encontrada"
                        )
                );

        agenda.setStatus(status);

        agendaRepositorie.save(agenda);
    }


    // Buscar Agenda do dia

    public AgendaDiaResponseDTO buscarAgendaDoDia(
            String dataStr,
            Long nutricionistaId
    ) {

        LocalDate data = LocalDate.parse(dataStr);

        Agenda agenda = agendaRepositorie
                .findByUsuarioIdAndData(
                        nutricionistaId,
                        data
                )
                .orElse(null);

        AgendaDiaResponseDTO response =
                new AgendaDiaResponseDTO();

        // NÃO EXISTE
        if (agenda == null) {

            response.setExiste(false);

            return response;
        }

        // EXISTE
        response.setExiste(true);

        AgendaDiaDTO dto =
                new AgendaDiaDTO();

        dto.setId(
                agenda.getId()
        );

        dto.setStatus(
                agenda.getStatus()
        );

        dto.setData(
                agenda.getData().toString()
        );

        NutricionistaDTO nutricionistaDTO =
                new NutricionistaDTO();

        nutricionistaDTO.setId(
                agenda.getUsuario().getId()
        );

        nutricionistaDTO.setNome(
                agenda.getUsuario().getNome()
        );

        dto.setNutricionista(
                nutricionistaDTO
        );

        response.setAgenda(dto);

        return response;
    }
}