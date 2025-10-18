package com.keltech.societario.controller;

import com.keltech.societario.dto.SocietarioDTO;
import com.keltech.societario.dto.SocioDTO;
import com.keltech.societario.service.SociosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/socios")
public class SocioController {
    
    @Autowired
    private SociosService sociosService;

    @GetMapping("/")
    public ResponseEntity<?> getSocios(
            @RequestParam(value = "participacaoMin", defaultValue = "0") Integer participacaoMin) {
        try {
            List<SocietarioDTO> socios = sociosService.getSociosComParticipacaoMinima(participacaoMin);
            return ResponseEntity.ok(socios);
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                    .body(Map.of("erro", "A requisição para a API demorou demais e foi encerrada."));
        } catch (HttpClientErrorException.TooManyRequests e) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(Map.of("erro", "A API pública de CNPJ retornou muitas requisições. Tente novamente mais tarde."));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro ao listar sócios: " + e.getMessage()));
        }
    }

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<?> listarSociosPorCnpj(@PathVariable String cnpj) {
        try {
            List<SocioDTO> socios = sociosService.getDetalhesSocios(cnpj);
            return ResponseEntity.ok(socios);
        } catch (HttpClientErrorException.TooManyRequests e) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(Map.of("erro", "A API pública de CNPJ retornou muitas requisições. Tente novamente mais tarde."));
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                    .body(Map.of("erro", "A requisição para a API demorou demais e foi encerrada."));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro ao buscar sócios pelo CNPJ: " + e.getMessage()));
        }
    }

}