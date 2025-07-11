package com.br.controle_catalogo_api.adapters.input.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
public class AtualizaCategoriaRequest {
    @NotNull
    private String titulo;
    @NotNull
    private String descricao;
}
