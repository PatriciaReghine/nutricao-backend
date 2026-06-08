package com.nutricao.nutricao_backend.dto.consulta;

import com.nutricao.nutricao_backend.enums.TipoConsulta;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaRequestDTO {

    private Long itemAgendaId;

    @NotNull(message = "Tipo de consulta é obrigatório")
    private TipoConsulta tipoConsulta;

    private String resumo;

    private String observacoes;

    @Valid
    @NotNull(message = "Avaliação é obrigatória")
    private AvaliacaoConsultaDTO avaliacao;
}
