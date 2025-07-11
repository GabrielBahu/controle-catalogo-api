package com.br.controle_catalogo_api.adapters.input.response;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaResponse {

    private String categoriaId;
    private String titulo;
    private String descricao;
}
