package com.br.controle_catalogo_api.domain.port;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.request.CadastraProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.response.ProdutoResponse;

import java.util.List;

public interface ProdutoServicePort {
    
    ProdutoResponse insert(CadastraProdutoRequest produtoRequest);

    List<ProdutoResponse> getAll();

    ProdutoResponse update(String id, AtualizaProdutoRequest produtoRequest);

    void delete(String id);
}
