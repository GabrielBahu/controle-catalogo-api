package com.br.controle_catalogo_api.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categoria")
@TypeAlias("CategoriaEntity")
@Data
@NoArgsConstructor
public class CategoriaEntity {
    @Id
    private String categoriaId;
    private String titulo;
    private String descricao;
}
