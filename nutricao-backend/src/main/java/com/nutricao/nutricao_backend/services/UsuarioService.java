package com.nutricao.nutricao_backend.services;

import com.nutricao.nutricao_backend.dto.nutricionista.NutricionistaResponseDTO;
import com.nutricao.nutricao_backend.dto.usuario.UsuarioRequestDTO;
import com.nutricao.nutricao_backend.dto.usuario.UsuarioResponseDTO;
import com.nutricao.nutricao_backend.dto.usuario.UsuarioUpdateMeDTO;
import com.nutricao.nutricao_backend.entidades.Perfil;
import com.nutricao.nutricao_backend.entidades.Usuario;
import com.nutricao.nutricao_backend.repositories.PerfilRepositorie;
import com.nutricao.nutricao_backend.repositories.UsuarioRepositorie;
import com.nutricao.nutricao_backend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorie usuarioRepositorie;

    @Autowired
    private PerfilRepositorie perfilRepositorie;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void validarNutricionista(
            UsuarioRequestDTO dto,
            Perfil perfil
    ) {

        if (!"NUTRICIONISTA".equalsIgnoreCase(
                perfil.getNomePerfil()
        )) {
            return;
        }

        if (dto.getCrn() == null ||
                dto.getCrn().isBlank()) {

            throw new RuntimeException(
                    "CRN é obrigatório para nutricionista"
            );
        }

        if (dto.getEspecialidade() == null ||
                dto.getEspecialidade().isBlank()) {

            throw new RuntimeException(
                    "Especialidade é obrigatória para nutricionista"
            );
        }
    }

    // Listar Usuários
    public List<UsuarioResponseDTO> findAllUsuario() {
        return usuarioRepositorie.findAll().stream().map(usuario ->
                new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getTelefone(),
                        usuario.getCrn(),
                        usuario.getEspecialidade(),
                        usuario.getPerfil() != null ? usuario.getPerfil().getNomePerfil(): null,
                        usuario.getAtivo()
                )
        ).toList();
    }

    // Buscar Por id
    public UsuarioResponseDTO buscarPeloId(Long id) {

        Usuario usuario = usuarioRepositorie.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getCrn(),
                usuario.getEspecialidade(),
                usuario.getPerfil() != null ? usuario.getPerfil().getNomePerfil() : null,
                usuario.getAtivo()
        );
    }

    // Salvar Usuário
    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {

        // Email Duplicado
        if (usuarioRepositorie.existsByEmail(dto.getEmail())) {

            throw new RuntimeException(
                    "Já existe um usuário cadastrado com este e-mail."
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setSenhaHash(
                passwordEncoder.encode(
                        dto.getSenha()
                )
        );
        if (dto.getPerfilId() != null) {

            Perfil perfil = perfilRepositorie.findById(dto.getPerfilId())
                    .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

            validarNutricionista(dto, perfil);

            usuario.setPerfil(perfil);

            if (usuario.getPerfil() != null &&
                    "NUTRICIONISTA".equalsIgnoreCase(usuario.getPerfil().getNomePerfil())) {

                if (dto.getCrn() != null) {
                    usuario.setCrn(dto.getCrn());
                }
                if (dto.getEspecialidade() != null) {
                    usuario.setEspecialidade(dto.getEspecialidade());
                }

            } else {
                usuario.setCrn(null);
                usuario.setEspecialidade(null);
            }
        }

        usuario = usuarioRepositorie.save(usuario);

        return mapToDTO(usuario);
    }

    // Login
    public String autenticar(String email, String senha) {

        Optional<Usuario> usuarioOpt = usuarioRepositorie.findByEmail(email);

        if (usuarioOpt.isPresent()) {

            Usuario usuario = usuarioOpt.get();

            if (usuario.getSenhaHash() != null &&
                    passwordEncoder.matches(
                            senha,
                            usuario.getSenhaHash()
                    )) {

                if (!usuario.getAtivo()) {
                    throw new IllegalStateException("Usuário bloqueado");
                }

                return JwtService.gerarToken(usuario);
            }
        }

        return null;
    }

    // Atualizar com ID ( ADMIN)
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto, String token) {

        Long idLogado = JwtService.getIdFromToken(token);

        Usuario usuarioLogado = usuarioRepositorie.findById(idLogado)
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado"));

        Usuario usuario = usuarioRepositorie.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        if (dto.getPerfilId() != null) {

            if (usuarioLogado.getPerfil() == null ||
                    !usuarioLogado.getPerfil().getNomePerfil().equalsIgnoreCase("ADMINISTRADOR")) {

                throw new IllegalStateException("Apenas ADMIN pode alterar perfil");
            }

            Perfil perfil = perfilRepositorie.findById(dto.getPerfilId())
                    .orElseThrow(() -> new IllegalStateException("Perfil não encontrado"));

            usuario.setPerfil(perfil);

            if (!"NUTRICIONISTA".equalsIgnoreCase(
                    perfil.getNomePerfil()
            )) {

                usuario.setCrn(null);
                usuario.setEspecialidade(null);
            }
        }

        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getNome() != null) {
            usuario.setNome(dto.getNome());
        }

        if (dto.getEmail() != null) {

            if (!dto.getEmail().equalsIgnoreCase(usuario.getEmail())
                    && usuarioRepositorie.existsByEmail(dto.getEmail())) {

                throw new RuntimeException(
                        "Já existe um usuário cadastrado com este e-mail."
                );
            }

            usuario.setEmail(dto.getEmail());
        }        if (dto.getTelefone() != null) usuario.setTelefone(dto.getTelefone());

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {

            usuario.setSenhaHash(
                    passwordEncoder.encode(
                            dto.getSenha()
                    )
            );
        }
        if (dto.getCrn() != null) usuario.setCrn(dto.getCrn());
        if (dto.getEspecialidade() != null) usuario.setEspecialidade(dto.getEspecialidade());

        usuario = usuarioRepositorie.save(usuario);

        return mapToDTO(usuario);
    }

    // Atualizar Logado (me)
    public UsuarioResponseDTO atualizarMe(String token, UsuarioUpdateMeDTO dto) {

        Long id = JwtService.getIdFromToken(token);

        Usuario usuario = usuarioRepositorie.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getNome() != null) {
            usuario.setNome(dto.getNome());
        }

        if (dto.getEmail() != null) {

            if (!dto.getEmail().equalsIgnoreCase(usuario.getEmail())
                    && usuarioRepositorie.existsByEmail(dto.getEmail())) {

                throw new RuntimeException(
                        "Já existe um usuário cadastrado com este e-mail."
                );
            }

            usuario.setEmail(dto.getEmail());
        }        if (dto.getTelefone() != null) usuario.setTelefone(dto.getTelefone());

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {

            usuario.setSenhaHash(
                    passwordEncoder.encode(
                            dto.getSenha()
                    )
            );
        }

        if (dto.getCrn() != null) usuario.setCrn(dto.getCrn());
        if (dto.getEspecialidade() != null) usuario.setEspecialidade(dto.getEspecialidade());

        usuario = usuarioRepositorie.save(usuario);

        return mapToDTO(usuario);
    }

    //Bloquear / Desbloquear
    public void alterarStatus(Long id, Boolean ativo, String token) {

        Long idLogado = JwtService.getIdFromToken(token);

        Usuario usuarioLogado = usuarioRepositorie.findById(idLogado)
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado"));


        if (usuarioLogado.getPerfil() == null ||
                !usuarioLogado.getPerfil().getNomePerfil().equalsIgnoreCase("ADMINISTRADOR")) {

            throw new IllegalStateException("Apenas ADMIN pode alterar status");
        }

        // 🔎 busca usuário alvo
        Usuario usuario = usuarioRepositorie.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setAtivo(ativo);

        usuarioRepositorie.save(usuario);
    }

    // Listar Nutricionistas
    public List<NutricionistaResponseDTO> listarNutricionistas() {

        return usuarioRepositorie
                .findByPerfil_NomePerfilAndAtivoTrueOrderByNomeAsc("NUTRICIONISTA")
                .stream()
                .map(usuario ->
                        new NutricionistaResponseDTO(
                                usuario.getId(),
                                usuario.getNome(),
                                usuario.getCrn()
                        )
                ).toList();
    }

    // Buscar Email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepositorie.findByEmail(email);
    }

    private UsuarioResponseDTO mapToDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getCrn(),
                usuario.getEspecialidade(),
                usuario.getPerfil() != null ? usuario.getPerfil().getNomePerfil() : null,
                usuario.getAtivo()
        );
    }

    public List<UsuarioResponseDTO> listarComFiltro(String busca, String perfil, Boolean ativo) {

        List<Usuario> lista = usuarioRepositorie.findAllByOrderByIdDesc();

        // Filtro Busca
        if (busca != null && !busca.isEmpty()) {
            lista = lista.stream()
                    .filter(u -> u.getNome().toLowerCase().contains(busca.toLowerCase())
                            || u.getEmail().toLowerCase().contains(busca.toLowerCase()))
                    .toList();
        }

        // Filtro Perfil
        if (perfil != null && !perfil.isEmpty()) {
            lista = lista.stream()
                    .filter(u -> u.getPerfil() != null &&
                            u.getPerfil().getNomePerfil().equalsIgnoreCase(perfil))
                    .toList();
        }

        // Filtro Status
        if (ativo != null) {
            lista = lista.stream()
                    .filter(u -> u.getAtivo() != null && u.getAtivo().equals(ativo))
                    .toList();
        }

        return lista.stream()
                .map(this::mapToDTO)
                .toList();
    }
    public List<UsuarioResponseDTO> listarUltimos(Integer limit) {

        List<Usuario> lista = usuarioRepositorie.findAllByOrderByIdDesc();

        System.out.println("=== ORDEM DO BANCO ===");

        lista.forEach(u ->
                System.out.println(
                        u.getId() + " - " + u.getNome()
                )
        );


        if (limit != null) {
            lista = lista.stream().limit(limit).toList();
        }

        return lista.stream()
                .map(this::mapToDTO)
                .toList();
    }


}