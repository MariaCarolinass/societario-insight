package com.keltech.societario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SocioDTO {
    
    @JsonProperty("nome")
    private String nome;
    
    @JsonProperty("cnpj")
    private String cnpj;

}
