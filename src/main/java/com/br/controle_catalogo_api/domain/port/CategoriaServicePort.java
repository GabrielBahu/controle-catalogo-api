package com.br.controle_catalogo_api.domain.port;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.request.CadastraCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.response.CategoriaResponse;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;

import java.util.List;

public interface CategoriaServicePort {

    CategoriaResponse insert(CadastraCategoriaRequest categoriaRequest);

    List<CategoriaResponse> getAll();

    CategoriaEntity getById(String id);

    CategoriaResponse update(String id, AtualizaCategoriaRequest categoriaRequest);

    void delete(String id);
}
