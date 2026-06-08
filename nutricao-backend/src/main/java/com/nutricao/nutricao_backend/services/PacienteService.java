package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.dto.paciente.PacienteBuscaDTO;
import com.nutricao.nutricao_backend.dto.paciente.PacienteRequestDTO;
import com.nutricao.nutricao_backend.dto.paciente.PacienteResponseDTO;
import com.nutricao.nutricao_backend.dto.prontuario.ProntuarioDTO;
import com.nutricao.nutricao_backend.entidades.Cidade;
import com.nutricao.nutricao_backend.entidades.Paciente;
import com.nutricao.nutricao_backend.repositories.PacienteRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepositorie pacienteRepositorie;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private ProntuarioService prontuarioService;

    // ===============================
    // 🔹 LISTAR TODOS
    // ===============================
    public List<PacienteResponseDTO> findAll(Integer limit, String busca) {

        List<Paciente> lista;

        if (busca != null && !busca.trim().isEmpty()) {

            String buscaTratada = busca.trim();

            lista = pacienteRepositorie
                    .findByNomeContainingIgnoreCaseOrCpfContainingOrderByDataCadastroDesc(buscaTratada, buscaTratada);

        } else {
            lista = pacienteRepositorie.findAllByOrderByDataCadastroDesc();
        }

        if (limit != null) {
            lista = lista.stream().limit(limit).toList();
        }

        return lista.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ===============================
    // 🔹 LISTAR ÚLTIMOS 5
    // ===============================


    // ===============================
    // 🔹 SALVAR
    // ===============================
    public PacienteResponseDTO salvar(PacienteRequestDTO dto) {

        Paciente paciente = new Paciente();

        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setEmail(dto.getEmail());
        paciente.setCep(dto.getCep());
        paciente.setLogradouro(dto.getLogradouro());
        paciente.setNumero(dto.getNumero());
        paciente.setBairro(dto.getBairro());
        paciente.setGenero(dto.getGenero());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setTelefone(dto.getTelefone());
        if (
                dto.getCep() != null
                        && !dto.getCep().isBlank()
        ) {

            Cidade cidade =
                    cidadeService.buscarCidadePorCep(
                            dto.getCep()
                    );

            paciente.setCidade(cidade);
        }

        paciente = pacienteRepositorie.save(paciente);

        // 🔥 cria prontuário automático
        prontuarioService.criarProntuario(paciente, null);

        return toResponseDTO(paciente);
    }

    // ===============================
    // 🔹 ATUALIZAR (PUT)
    // ===============================
    public PacienteResponseDTO atualizar(Long id, PacienteRequestDTO dto) {

        Paciente paciente = pacienteRepositorie.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        // 🔹 atualização segura (não sobrescreve com null)

        System.out.println("ANTES: " + paciente.getNome());
            paciente.setNome(dto.getNome());
            paciente.setEmail(dto.getEmail());
            paciente.setTelefone(dto.getTelefone());
            paciente.setLogradouro(dto.getLogradouro());
            paciente.setNumero(dto.getNumero());
            paciente.setBairro(dto.getBairro());
            paciente.setGenero(dto.getGenero());
            paciente.setDataNascimento(dto.getDataNascimento());

            paciente.setCep(dto.getCep());
            paciente.setCidade(cidadeService.buscarCidadePorCep(dto.getCep()));


        System.out.println("PACIENTE PARA SALVAR: " + paciente.getNome());

        paciente = pacienteRepositorie.save(paciente);

        System.out.println("SALVO: " + paciente.getNome());

        return toResponseDTO(paciente);
    }

    // ===============================
    // 🔁 CONVERSÃO ENTITY → DTO
    // ===============================
    private PacienteResponseDTO toResponseDTO(Paciente paciente) {

        PacienteResponseDTO dto = new PacienteResponseDTO();

        dto.setId(paciente.getId());
        dto.setNome(paciente.getNome());
        dto.setCpf(paciente.getCpf());
        dto.setEmail(paciente.getEmail());

        dto.setLogradouro(paciente.getLogradouro());
        dto.setBairro(paciente.getBairro());
        dto.setNumero(paciente.getNumero());
        dto.setCep(paciente.getCep());
        dto.setTelefone(paciente.getTelefone());
        dto.setDataNascimento(paciente.getDataNascimento());
        dto.setDataCadastro(paciente.getDataCadastro());
        dto.setGenero(paciente.getGenero());

        if (paciente.getCidade() != null) {
            dto.setCidade(paciente.getCidade().getNome());
        }

        if (paciente.getProntuario() != null) {

            ProntuarioDTO prontuarioDTO = new ProntuarioDTO();
            prontuarioDTO.setId(paciente.getProntuario().getId());
            prontuarioDTO.setNumeroProntuario(paciente.getProntuario().getNumeroProntuario());

            prontuarioDTO.setObjetivo(paciente.getProntuario().getObjetivo());
            prontuarioDTO.setInformacoesClinicas(paciente.getProntuario().getInformacoesClinicas());
            prontuarioDTO.setRestricaoAlimentar(paciente.getProntuario().getRestricaoAlimentar());

            dto.setProntuario(prontuarioDTO);
        }

        return dto;
    }

    public List<PacienteBuscaDTO> buscarPacientes(String nome) {

        List<Paciente> pacientes =
                pacienteRepositorie
                        .findByNomeContainingIgnoreCase(nome);

        return pacientes.stream().map(p -> {

            PacienteBuscaDTO dto =
                    new PacienteBuscaDTO();

            dto.setId(p.getId());
            dto.setNome(p.getNome());
            dto.setCpf(p.getCpf());

            return dto;

        }).toList();
    }
}