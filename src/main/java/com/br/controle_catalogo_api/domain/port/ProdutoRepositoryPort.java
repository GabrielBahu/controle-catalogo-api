package com.br.controle_catalogo_api.domain.port;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.response.ProdutoResponse;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;
import com.br.controle_catalogo_api.domain.model.ProdutoEntity;

import java.util.List;

public interface ProdutoRepositoryPort {
    boolean existsByProdutoId(String produtoId);

    ProdutoResponse insert(ProdutoEntity produtoEntity);

    List<ProdutoResponse> getAll();

    ProdutoEntity getById(String id);

    ProdutoResponse update(String id, AtualizaProdutoRequest produtoRequest, CategoriaEntity categoriaEntity);

    void delete(String id);
}
