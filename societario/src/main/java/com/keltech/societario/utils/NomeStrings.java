package com.keltech.societario.utils;

import org.springframework.stereotype.Component;

@Component
public class NomeStrings {
    
    public String normalizarNome(String nome) {
        if (nome == null) return "";
        return nome.trim()
                .replaceAll("\\s+", " ")
                .toUpperCase();
    }

}
