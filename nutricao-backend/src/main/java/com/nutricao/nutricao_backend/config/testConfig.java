package com.nutricao.nutricao_backend.config;

import com.nutricao.nutricao_backend.entidades.Perfil;
import com.nutricao.nutricao_backend.entidades.Usuario;
import com.nutricao.nutricao_backend.repositories.PerfilRepositorie;
import com.nutricao.nutricao_backend.repositories.UsuarioRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class testConfig implements CommandLineRunner {

    @Autowired
    private UsuarioRepositorie usuarioRepositorie;

    @Autowired
    private PerfilRepositorie perfilRepositorie;

    @Override
    public void run(String... args) throws Exception {

        // 1. Criar perfis
        Perfil p1 = new Perfil(null, "NUTRICIONISTA");
        Perfil p2 = new Perfil(null, "SECRETARIA");

        perfilRepositorie.saveAll(Arrays.asList(p1, p2));

        // 2. Buscar perfil (agora já salvo)
        Perfil nutricionista = perfilRepositorie.findById(p1.getId()).get();

        // 3. Criar usuário (SEM perfil no construtor)
        Usuario u1 = new Usuario(
                null,
                "Patricia",
                "teste@email.com",
                "43996101563",
                "patriciareghine",
                "12345",
                "nutri",
                "assinatura"
        );

        // 4. Setar o perfil (ESSENCIAL)
        u1.setPerfil(nutricionista);

        // 5. Salvar
        usuarioRepositorie.save(u1);
    }
}