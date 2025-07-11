package com.br.controle_catalogo_api.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "produto")
@Data
@NoArgsConstructor
public class ProdutoEntity {

    @Id
    private String produtoId;
    private String titulo;
    private String descricao;
    private BigDecimal preco;
    private CategoriaEntity categoria;
}
