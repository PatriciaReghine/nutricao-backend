package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.dto.prontuario.ProntuarioRequestDTO;
import com.nutricao.nutricao_backend.dto.prontuario.ProntuarioResponseDTO;
import com.nutricao.nutricao_backend.entidades.Paciente;
import com.nutricao.nutricao_backend.entidades.Prontuario;
import com.nutricao.nutricao_backend.entidades.Usuario;
import com.nutricao.nutricao_backend.repositories.ProntuarioRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProntuarioService {

    @Autowired
    private ProntuarioRepositorie prontuarioRepositorie;

    // ===============================
    //  CRIAR PRONTUÁRIO AUTOMÁTICO
    // ===============================
    public Prontuario criarProntuario(Paciente paciente, Usuario usuario) {

        Prontuario prontuario = new Prontuario();

        prontuario.setPaciente(paciente);
        prontuario.setUsuario(usuario);
        prontuario.setNumeroProntuario(gerarNumero());

        return prontuarioRepositorie.save(prontuario);
    }

    // ===============================
    //  ATUALIZAR PRONTUÁRIO (EDIÇÃO)
    // ===============================
    public Prontuario atualizar(Long id, ProntuarioRequestDTO dto) {

        Prontuario prontuario = prontuarioRepositorie.findById(id)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));

        prontuario.setObjetivo(dto.getObjetivo());
        prontuario.setRestricaoAlimentar(dto.getRestricaoAlimentar());
        prontuario.setInformacoesClinicas(dto.getInformacoesClinicas());

        return prontuarioRepositorie.save(prontuario);
    }

    // ===============================
    //  BUSCAR PRONTUÁRIO POR ID
    // ===============================
    public ProntuarioResponseDTO buscarPorId(Long id) {

        Prontuario prontuario = prontuarioRepositorie.findById(id)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));

        return toDTO(prontuario);
    }

    // ===============================
    // BUSCAR PRONTUÁRIO PELO PACIENTE
    // ===============================
    public ProntuarioResponseDTO buscarPorPaciente(Long idPaciente) {

        Prontuario prontuario = prontuarioRepositorie.findByPacienteId(idPaciente)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));

        return toDTO(prontuario);
    }

    // ===============================
    //  GERAR NÚMERO DO PRONTUÁRIO
    // ===============================
    private String gerarNumero() {
        long count = prontuarioRepositorie.count() + 1;
        return String.format("%05d", count);
    }

    // ===============================
    // 🔄 MAPPER ENTITY → DTO
    // ===============================
    private ProntuarioResponseDTO toDTO(Prontuario prontuario) {

        ProntuarioResponseDTO dto = new ProntuarioResponseDTO();

        dto.setId(prontuario.getId());

        dto.setNumeroProntuario(prontuario.getNumeroProntuario());
        dto.setDataNascimento(prontuario.getPaciente().getDataNascimento());

        dto.setObjetivo(prontuario.getObjetivo());
        dto.setInformacoesClinicas(prontuario.getInformacoesClinicas());
        dto.setRestricaoAlimentar(prontuario.getRestricaoAlimentar());

        // 🔹 dados vindos do paciente
        dto.setNome(prontuario.getPaciente().getNome());
        dto.setGenero(prontuario.getPaciente().getGenero());

        return dto;
    }

    public Prontuario atualizarPorPaciente(Long idPaciente, ProntuarioRequestDTO dto) {

        Prontuario prontuario = prontuarioRepositorie.findByPacienteId(idPaciente)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado para esse paciente"));

        prontuario.setObjetivo(dto.getObjetivo());
        prontuario.setRestricaoAlimentar(dto.getRestricaoAlimentar());
        prontuario.setInformacoesClinicas(dto.getInformacoesClinicas());

        return prontuarioRepositorie.save(prontuario);
    }
}