package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.dto.avaliacao.AvaliacaoFisicaDto;
import com.nutricao.nutricao_backend.entidades.AvaliacaoFisica;
import com.nutricao.nutricao_backend.entidades.Prontuario;
import com.nutricao.nutricao_backend.repositories.AvaliacaoFisicaRepositorie;
import com.nutricao.nutricao_backend.repositories.ProntuarioRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoFisicaService {
    @Autowired
    private AvaliacaoFisicaRepositorie avaliacaoFisicaRepositorie;

    @Autowired
    private ProntuarioRepositorie prontuarioRepositorie;

    public List<AvaliacaoFisica> findAll(){
        return avaliacaoFisicaRepositorie.findAll();
    }

    public AvaliacaoFisica salvar(AvaliacaoFisicaDto dto) {

        Prontuario prontuario = prontuarioRepositorie.findById(dto.idProntuario)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));

        AvaliacaoFisica avaliacao = new AvaliacaoFisica();

        avaliacao.setPeso(dto.peso);
        avaliacao.setAltura(dto.altura);

        Double imc = dto.peso / (dto.altura * dto.altura);

        avaliacao.setImc(
                Math.round(imc * 100.0) / 100.0
        );

        avaliacao.setCircunferenciaAbdominal(dto.circunferenciaAbdominal);
        avaliacao.setCircunferenciaQuadril(dto.circunferenciaQuadril);
        avaliacao.setPlanejamentoAlimentar(dto.planejamentoAlimentar);
        avaliacao.setProntuario(prontuario);

        return avaliacaoFisicaRepositorie.save(avaliacao);
    }


}
