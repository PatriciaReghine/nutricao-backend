package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.entidades.Paciente;
import com.nutricao.nutricao_backend.repositories.PacienteRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepositorie pacienteRepositorie;

    public List<Paciente> findAll(){
        return pacienteRepositorie.findAll();
    }
    public Paciente salvar(Paciente paciente) {
        return pacienteRepositorie.save(paciente);
    }
}
