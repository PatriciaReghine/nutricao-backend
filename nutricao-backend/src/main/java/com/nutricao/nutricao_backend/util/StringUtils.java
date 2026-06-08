package com.nutricao.nutricao_backend.util;

import java.text.Normalizer;

public class StringUtils {
    public static String normalizar(String texto) {
        if (texto == null) return null;

        return Normalizer
                .normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toUpperCase()
                .trim();
    }
}
