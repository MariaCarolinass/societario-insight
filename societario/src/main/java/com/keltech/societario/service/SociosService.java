package com.keltech.societario.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keltech.societario.dto.SocietarioDTO;
import com.keltech.societario.dto.SocioDTO;
import com.keltech.societario.utils.UrlMapa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class SociosService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UrlMapa urlMapa;

    @Value("${api.keltech.url:https://keltech-test.wiremockapi.cloud/json}")
    private String kelTechApiUrl;

    @Value("${api.receita.url:https://publica.cnpj.ws/cnpj}")
    private String receitaApiUrl;

    public List<SocietarioDTO> getSociosComParticipacaoMinima(Double participacaoMin) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    kelTechApiUrl,
                    HttpMethod.GET,
                    null,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String json = response.getBody();
                JsonNode root = objectMapper.readTree(json);

                JsonNode empresaData = root.path("mix").path("empresa").path("data");
                String cep = empresaData.path("cep").asText(null);

                List<String> cnaesSecundarios = new ArrayList<>();
                JsonNode cnaesArray = empresaData.path("cnaesSecundarios");
                if (cnaesArray.isArray()) {
                    for (JsonNode c : cnaesArray) {
                        cnaesSecundarios.add(c.asText());
                    }
                }

                JsonNode quadroArray = root.path("mix")
                        .path("quadroSocietario")
                        .path("data")
                        .path("quadroSocietario");

                if (!quadroArray.isArray()) return Collections.emptyList();

                List<SocietarioDTO> socios = new ArrayList<>();
                for (JsonNode node : quadroArray) {
                    Integer participacao = 0;
                    if (node.has("participacao")) {
                        JsonNode pNode = node.get("participacao");
                        if (pNode.isNumber()) participacao = pNode.asInt();
                        else participacao = Integer.parseInt(pNode.asText().replace("%", "").trim());
                    }

                    String nome = node.path("nome").asText(null);
                    nome = new String(nome.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                    SocietarioDTO s = SocietarioDTO.builder()
                            .nome(nome)
                            .cnpj(node.path("cnpjEmpresa").asText(null))
                            .participacao(participacao)
                            .cep(cep)
                            .urlMapa(urlMapa.gerarUrlMapa(cep))
                            .cnaesSecundarios(cnaesSecundarios)
                            .build();

                    socios.add(s);
                }

                if (participacaoMin != null && participacaoMin > 0.0) {
                    socios = socios.stream()
                            .filter(s -> s.getParticipacao() >= participacaoMin)
                            .toList();
                }

                return socios;
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar API de sócios: " + e.getMessage(), e);
        }

        return new ArrayList<>();
    }

    public List<SocioDTO> getDetalhesSocios(String cnpj) {
        List<SocioDTO> sociosDetalhados = new ArrayList<>();

        try {
            String cnpjSomenteDigitos = cnpj.replaceAll("\\D", "");
            String url = receitaApiUrl + "/" + cnpjSomenteDigitos;

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode sociosNode = root.path("socios");

                if (sociosNode.isArray()) {
                    for (JsonNode socioNode : sociosNode) {
                        SocioDTO dto = new SocioDTO();
                        dto.setCnpj(cnpjSomenteDigitos);
                        String nome = socioNode.path("nome").asText(null);
                        nome = new String(nome.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                        dto.setNome(nome);
                        sociosDetalhados.add(dto);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar sócios na API: " + e.getMessage(), e);
        }

        return sociosDetalhados;
    }

}
