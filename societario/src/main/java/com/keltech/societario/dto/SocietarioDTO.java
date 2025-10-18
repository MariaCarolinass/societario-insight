package com.keltech.societario.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SocietarioDTO {

    @JsonProperty("nome")
    private String nome;
    
    @JsonProperty("participacao")
    private Integer participacao;
    
    @JsonProperty("cnpj")
    private String cnpj;

    @JsonProperty("cep")
    private String cep;

    @JsonProperty("urlMapa")
    private String urlMapa;

    @JsonProperty("cnaesSecundarios")
    private List<String> cnaesSecundarios;

}
