package com.br.controle_catalogo_api.adapters.input.response;

import com.br.controle_catalogo_api.adapters.input.dto.CategoriaDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProdutoResponse {

    private String produtoId;
    private String titulo;
    private String descricao;
    private BigDecimal preco;
    private CategoriaDTO categoria;
}
