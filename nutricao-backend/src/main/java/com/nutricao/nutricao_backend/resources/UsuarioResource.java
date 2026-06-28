package com.nutricao.nutricao_backend.resources;

import com.nutricao.nutricao_backend.dto.login.LoginDTO;
import com.nutricao.nutricao_backend.dto.login.LoginResponseDTO;
import com.nutricao.nutricao_backend.dto.usuario.UsuarioRequestDTO;
import com.nutricao.nutricao_backend.dto.usuario.UsuarioResponseDTO;
import com.nutricao.nutricao_backend.dto.usuario.UsuarioUpdateMeDTO;
import com.nutricao.nutricao_backend.entidades.Usuario;
import com.nutricao.nutricao_backend.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "usuarios")
public class UsuarioResource {

    @Autowired
    UsuarioService usuarioService;

    // 🔹 GET - LISTAR (COM FILTRO + LIMIT)

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) String perfil,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer limit
    ) {

        List<UsuarioResponseDTO> lista =
                usuarioService.listarComFiltro(busca, perfil, ativo);

        //  aplica ordenação + limit SEM quebrar filtro
        if (limit != null) {
            lista = lista.stream().limit(limit).toList();
        }

        return ResponseEntity.ok(lista);
    }

    // 🔹 GET - BUSCAR POR ID

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id){

        UsuarioResponseDTO usuario = usuarioService.buscarPeloId(id);

        return ResponseEntity.ok(usuario);
    }

    // 🔹 POST - SALVAR
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> salvar(@RequestBody @Valid UsuarioRequestDTO dto){

        UsuarioResponseDTO usuario = usuarioService.salvar(dto);

        return ResponseEntity.ok(usuario);
    }

    // 🔹 LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO login) {

        String token = usuarioService.autenticar(
                login.getEmail(),
                login.getSenha()
        );

        if (token != null) {

            Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(login.getEmail());

            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(401).body("Usuário não encontrado");
            }

            Usuario usuario = usuarioOpt.get();

            UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getTelefone(),
                    usuario.getCrn(),
                    usuario.getEspecialidade(),
                    usuario.getPerfil() != null ? usuario.getPerfil().getNomePerfil() : null,
                    usuario.getAtivo()
            );

            LoginResponseDTO response = new LoginResponseDTO(
                    token,
                    usuarioDTO
            );

            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }
    }

    // 🔹 PUT - ATUALIZAR USUÁRIO
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
             @RequestBody UsuarioRequestDTO dto,
            @RequestHeader("Authorization") String token) {

        token = token.replace("Bearer ", "");

        UsuarioResponseDTO usuario = usuarioService.atualizar(id, dto, token);

        return ResponseEntity.ok(usuario);
    }

    // 🔹 BLOQUEAR / DESBLOQUEAR

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}/status")
    public ResponseEntity<?> alterarStatus(
            @PathVariable Long id,
            @Valid @RequestParam Boolean ativo,
            @RequestHeader("Authorization") String token) {

        token = token.replace("Bearer ", "");

        usuarioService.alterarStatus(id, ativo, token);

        return ResponseEntity.ok("Status atualizado");
    }

    // 🔹 ATUALIZAR USUÁRIO LOGADO
    @PutMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> atualizarMe(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody UsuarioUpdateMeDTO dto) {

        System.out.println("===== ATUALIZAR PERFIL =====");
        System.out.println("Nome: " + dto.getNome());
        System.out.println("Email: " + dto.getEmail());
        System.out.println("Telefone: " + dto.getTelefone());
        System.out.println("Senha: " + dto.getSenha());
        System.out.println("CRN: " + dto.getCrn());
        System.out.println("Especialidade: " + dto.getEspecialidade());

        token = token.replace("Bearer ", "");

        UsuarioResponseDTO usuario =
                usuarioService.atualizarMe(token, dto);

        return ResponseEntity.ok(usuario);
    }
}