package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.dto.cep.CepResponse;
import com.nutricao.nutricao_backend.dto.IbgeCidadeResponse;
import com.nutricao.nutricao_backend.entidades.Cidade;
import com.nutricao.nutricao_backend.repositories.CidadeRepositorie;
import com.nutricao.nutricao_backend.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CidadeService {
    @Autowired
    private CidadeRepositorie cidadeRepositorie;

    public Cidade buscarCidadePorCep(String cep) {

        if (
                cep == null ||
                        cep.isBlank()
        ) {
            return null;
        }

        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        RestTemplate restTemplate = new RestTemplate();
        CepResponse response =
                restTemplate.getForObject(url, CepResponse.class);

        if (response == null || response.getLocalidade() == null) {
            throw new RuntimeException("CEP inválido");
        }

        String nomeNormalizado =
                StringUtils.normalizar(response.getLocalidade());

        String uf = response.getUf().toUpperCase();

        Cidade cidade = cidadeRepositorie
                .findByNomeNormalizadoAndUf(nomeNormalizado, uf);

        if (cidade == null) {
            throw new RuntimeException("Cidade não encontrada no banco");
        }

        return cidade;
    }
    public void importarCidadesIBGE() {

        String url = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios";

        RestTemplate restTemplate = new RestTemplate();

        IbgeCidadeResponse[] cidadesApi =
                restTemplate.getForObject(url, IbgeCidadeResponse[].class);

        for (IbgeCidadeResponse cidadeApi : cidadesApi) {

            //  VALIDAÇÃO (IMPORTANTE)
            if (cidadeApi.getMicrorregiao() == null ||
                    cidadeApi.getMicrorregiao().getMesorregiao() == null ||
                    cidadeApi.getMicrorregiao().getMesorregiao().getUF() == null) {
                continue;
            }

            String nome = cidadeApi.getNome();

            String uf = cidadeApi.getMicrorregiao()
                    .getMesorregiao()
                    .getUF()
                    .getSigla();


            if (cidadeRepositorie.existsByNomeAndUf(nome, uf)) {
                continue;
            }

            Cidade cidade = new Cidade();
            cidade.setNome(nome);
            cidade.setUf(uf);
            cidade.setNomeNormalizado(StringUtils.normalizar(nome));


            cidadeRepositorie.save(cidade);
        }
    }
}
