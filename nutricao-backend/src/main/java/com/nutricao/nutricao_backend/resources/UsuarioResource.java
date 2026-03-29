package com.nutricao.nutricao_backend.resources;
import com.nutricao.nutricao_backend.dto.LoginDTO;

import com.nutricao.nutricao_backend.entidades.Usuario;
import com.nutricao.nutricao_backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "usuario")

public class UsuarioResource {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAllUsuarios(){
        List<Usuario> listaUsuarios = usuarioService.findAllUsuario();
        return ResponseEntity.ok().body(listaUsuarios);
    }
    @PostMapping
    public Usuario salvar(@RequestBody Usuario usuario) {
        return usuarioService.salvar(usuario);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO login) {

        boolean autenticado = usuarioService.autenticar(
                login.getEmail(),
                login.getSenha()
        );

        if (autenticado) {
            return ResponseEntity.ok("Login realizado com sucesso");
        } else {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }
    }
}
