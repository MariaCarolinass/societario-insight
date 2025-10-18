package com.keltech.societario.utils;

import org.springframework.stereotype.Component;

@Component
public class ConverterStrings {
    
    public String normalizarNome(String nome) {
        if (nome == null) return "";
        return nome.trim()
                .replaceAll("\\s+", " ")
                .toUpperCase();
    }

    public String formatarCnae(String cnae) {
        if (cnae == null || cnae.isEmpty()) return "";
        
        String[] partes = cnae.split(" ", 2);
        String codigo = partes[0];
        String descricao = partes.length > 1 ? partes[1] : "";
        
        if (codigo.length() != 7) return cnae;
        
        String parte1 = codigo.substring(0, 4);
        String parte2 = codigo.substring(4, 5);
        String parte3 = codigo.substring(5);
        
        return parte1 + "-" + parte2 + "/" + parte3 + " " + descricao;
    }

}
