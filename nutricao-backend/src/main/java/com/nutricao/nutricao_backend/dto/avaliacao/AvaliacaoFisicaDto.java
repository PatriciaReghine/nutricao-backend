package com.nutricao.nutricao_backend.dto.avaliacao;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class AvaliacaoFisicaDto {
        public Double peso;
        public Double altura;
        public Double circunferenciaAbdominal;
        public Double circunferenciaQuadril;
        public String planejamentoAlimentar;

        @JsonFormat(pattern = "yyyy-MM-dd")
        public LocalDate dataInicio;

        @JsonFormat(pattern = "yyyy-MM-dd")
        public LocalDate dataFinal;

        public Long idProntuario;

}
