package com.keltech.societario.utils;

import org.springframework.stereotype.Component;

@Component
public class UrlMapa {
    
    public String gerarUrlMapa(String cep) {
        String cepFormatado = cep.replaceAll("\\D", "");
        return "https://www.google.com/maps/embed/v1/place?key=AIzaSyBFw0Qbyq9zTFTd-tUY6dZWTgaQzuU17R8&q=" + cepFormatado + ",Brazil";
    }

}
