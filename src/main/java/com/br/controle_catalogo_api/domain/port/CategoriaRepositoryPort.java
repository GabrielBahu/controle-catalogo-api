package com.br.controle_catalogo_api.domain.port;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.response.CategoriaResponse;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;

import java.util.List;

public interface CategoriaRepositoryPort {

    CategoriaResponse insert(CategoriaEntity categoriaEntity);

    List<CategoriaResponse> getAll();

    CategoriaResponse update(String id, AtualizaCategoriaRequest categoriaRequest);

    boolean existsByCategoriaId(String categoriaId);

    void delete(String id);

    CategoriaEntity getById(String id);
}
