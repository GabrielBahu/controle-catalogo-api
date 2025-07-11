package com.br.controle_catalogo_api.adapters.input.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CadastraProdutoRequest {
    @NotNull
    private String produtoId;
    @NotNull
    private String titulo;
    @NotNull
    private String descricao;
    @NotNull
    private BigDecimal preco;
    @NotNull
    private String categoriaId;
}
