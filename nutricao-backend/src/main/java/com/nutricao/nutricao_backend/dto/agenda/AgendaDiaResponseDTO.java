package com.nutricao.nutricao_backend.dto.agenda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaDiaResponseDTO {

    private Boolean existe;
    private AgendaDiaDTO agenda;
}