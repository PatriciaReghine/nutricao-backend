package com.nutricao.nutricao_backend.resources;

import com.nutricao.nutricao_backend.entidades.Paciente;
import com.nutricao.nutricao_backend.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/paciente")
public class PacienteResource {

    @Autowired
    PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> findAll(){
        List<Paciente> ListaPacientes = pacienteService.findAll();
        return ResponseEntity.ok().body(ListaPacientes);
    }
    @PostMapping
    public ResponseEntity<Paciente> salvar(@RequestBody Paciente paciente) {
        Paciente novo = pacienteService.salvar(paciente);
        return ResponseEntity.ok().body(novo);
    }
}
