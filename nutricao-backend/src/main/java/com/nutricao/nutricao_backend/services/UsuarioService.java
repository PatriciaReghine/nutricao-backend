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

    // 🔹 LISTAR USUÁRIOS
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

    // 🔹 BUSCAR POR ID
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

    // 🔹 SALVAR USUÁRIO
    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setLogin(dto.getLogin());
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

            // 🔥 REGRA DE NEGÓCIO
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

    // 🔹 LOGIN
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

    // 🔹 ATUALIZAR POR ID (COM REGRA DE ADMIN)
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto, String token) {

        Long idLogado = JwtService.getIdFromToken(token);

        Usuario usuarioLogado = usuarioRepositorie.findById(idLogado)
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado"));

        Usuario usuario = usuarioRepositorie.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // 🔒 REGRA: só admin pode alterar perfil
        if (dto.getPerfilId() != null) {

            if (usuarioLogado.getPerfil() == null ||
                    !usuarioLogado.getPerfil().getNomePerfil().equalsIgnoreCase("ADMINISTRADOR")) {

                throw new IllegalStateException("Apenas ADMIN pode alterar perfil");
            }

            Perfil perfil = perfilRepositorie.findById(dto.getPerfilId())
                    .orElseThrow(() -> new IllegalStateException("Perfil não encontrado"));

            usuario.setPerfil(perfil);
        }

        // 🔹 atualização parcial
        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getTelefone() != null) usuario.setTelefone(dto.getTelefone());
        if (dto.getLogin() != null) usuario.setLogin(dto.getLogin());

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

    // 🔹 ATUALIZAR USUÁRIO LOGADO (/me)
    public UsuarioResponseDTO atualizarMe(String token, UsuarioUpdateMeDTO dto) {

        Long id = JwtService.getIdFromToken(token);

        Usuario usuario = usuarioRepositorie.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getTelefone() != null) usuario.setTelefone(dto.getTelefone());

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

    // 🔹 BLOQUEAR / DESBLOQUEAR
    public void alterarStatus(Long id, Boolean ativo, String token) {

        // 🔹 pega usuário logado
        Long idLogado = JwtService.getIdFromToken(token);

        Usuario usuarioLogado = usuarioRepositorie.findById(idLogado)
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado"));

        // 🔒 valida admin
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

    // 🔹 LISTAR NUTRICIONISTAS
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

    // 🔹 BUSCAR EMAIL
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepositorie.findByEmail(email);
    }

    // 🔹 CONVERSOR PADRÃO (evita repetição)
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

        // 🔍 FILTRO DE BUSCA
        if (busca != null && !busca.isEmpty()) {
            lista = lista.stream()
                    .filter(u -> u.getNome().toLowerCase().contains(busca.toLowerCase())
                            || u.getEmail().toLowerCase().contains(busca.toLowerCase()))
                    .toList();
        }

        // 🔍 FILTRO DE PERFIL
        if (perfil != null && !perfil.isEmpty()) {
            lista = lista.stream()
                    .filter(u -> u.getPerfil() != null &&
                            u.getPerfil().getNomePerfil().equalsIgnoreCase(perfil))
                    .toList();
        }

        // 🔍 FILTRO DE STATUS
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

        if (limit != null) {
            lista = lista.stream().limit(limit).toList();
        }

        return lista.stream()
                .map(this::mapToDTO)
                .toList();
    }


}