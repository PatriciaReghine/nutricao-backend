package com.nutricao.nutricao_backend.dto.consulta;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

@JsonPropertyOrder({
        "hora",
        "slotStatus",
        "agendamentos"
})

public class ConsultaAgendaDTO {

    private String hora;

    private String slotStatus;

    private List<ConsultaSlotDTO> agendamentos;;}