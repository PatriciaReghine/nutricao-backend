//package com.nutricao.nutricao_backend.config;
//
//import com.nutricao.nutricao_backend.entidades.Perfil;
//import com.nutricao.nutricao_backend.entidades.Usuario;
//import com.nutricao.nutricao_backend.repositories.PerfilRepositorie;
//import com.nutricao.nutricao_backend.repositories.UsuarioRepositorie;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import java.util.Arrays;
//
//@Configuration
//@Profile("test")
//public class testConfig implements CommandLineRunner {
//
//    @Autowired
//    private UsuarioRepositorie usuarioRepositorie;
//
//    @Autowired
//    private PerfilRepositorie perfilRepositorie;
//
//    @Override
//
//    public void run(String... args) {
//
//      //  if (usuarioRepositorie.count() == 0) {
//
//            Perfil p1 = new Perfil(null, "NUTRICIONISTA");
//            Perfil p2 = new Perfil(null, "SECRETARIA");
//
//            perfilRepositorie.saveAll(Arrays.asList(p1, p2));
//
//            Usuario u1 = new Usuario(
//                    null,
//                    "Patricia",
//                    "pat@email.com",
//                    "43996101563",
//                    "patricia456",
//                    "654321",
//                    "12369",
//                    "nutri"
//            );
//
//            Usuario u2 = new Usuario(
//                    null,
//                    "Clara",
//                    "clara@email.com",
//                    "43996101564",
//                    "clara123",
//                    "654321",
//                    "12369",
//                    "nutri"
//                    );
//        Usuario u3 = new Usuario(
//                null,
//                "Clara",
//                "clara3@email.com",
//                "43996101565",
//                "clara12",
//                "654331",
//                "12368",
//                "nutri"
//        );
//            Usuario u4 = new Usuario(
//                    null,
//                    "Clara",
//                    "clara5@email.com",
//                    "43996101575",
//                    "clara1",
//                    "654331",
//                    "12368",
//                    "nutri"
//            );
//            Usuario u5 = new Usuario(
//                    null,
//                    "Patricia",
//                    "pat5@email.com",
//                    "43996109575",
//                    "pat3",
//                    "674331",
//                    "123698",
//                    "nutri"
//            );
//            Usuario u6 = new Usuario(
//                    null,
//                    "Patricia",
//                    "pat7@email.com",
//                    "43996108575",
//                    "pat7",
//                    "674332",
//                    "123698",
//                    "nutri"
//            );
//
//            u1.setPerfil(p1);
//            u2.setPerfil(p1);
//            u3.setPerfil(p1);
//            u4.setPerfil(p1);
//            u5.setPerfil(p1);
//            u6.setPerfil(p1);
//
//
//
//            usuarioRepositorie.saveAll(Arrays.asList(u1, u2,u3,u4,u5,u6));
//        }
//
//    //}
//}