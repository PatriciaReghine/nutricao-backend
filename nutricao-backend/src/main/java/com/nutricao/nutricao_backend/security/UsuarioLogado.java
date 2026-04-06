package com.nutricao.nutricao_backend.security;

public class UsuarioLogado {

    private static ThreadLocal<String> email = new ThreadLocal<>();
    private static ThreadLocal<String> perfil = new ThreadLocal<>();

    public static void set(String emailUser, String perfilUser) {
        email.set(emailUser);
        perfil.set(perfilUser);
    }

    public static String getEmail() {
        return email.get();
    }

    public static String getPerfil() {
        return perfil.get();
    }

    public static void clear() {
        email.remove();
        perfil.remove();
    }
}