package config;

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
    @Override
    public void run(String... args) throws Exception {

        Perfil p1 = new Perfil(null, "NUTRICIONISTA");
        Perfil p2 = new Perfil(null, "SECRETARIA");

        perfilRepositorie.saveAll(Arrays.asList(p1, p2));

        Perfil nutricionista = perfilRepositorie.findById(1L).get();

        Usuario u1 = new Usuario(null, "Patricia", "teste@email.com","43996101563","patriciareghine", "12345","nutri", nutricionista.getNome_perfil());

        usuarioRepositorie.saveAll(Arrays.asList(u1));

    }
    @Autowired
    private UsuarioRepositorie usuarioRepositorie;

    @Autowired
    private PerfilRepositorie perfilRepositorie;
}
