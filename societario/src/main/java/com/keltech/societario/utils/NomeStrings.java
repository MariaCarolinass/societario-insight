package com.keltech.societario.utils;

import org.springframework.stereotype.Component;

@Component
public class NomeStrings {
    
    public String normalizarNome(String nome) {
        if (nome == null) return "";
        String semAcento = java.text.Normalizer.normalize(nome, java.text.Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        return semAcento.trim().replaceAll("\\s+", " ").toUpperCase();
    }

}
