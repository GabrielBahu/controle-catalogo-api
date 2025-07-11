package com.br.controle_catalogo_api.application.mapper;

import com.br.controle_catalogo_api.adapters.input.dto.CategoriaDTO;
import com.br.controle_catalogo_api.adapters.input.request.AtualizaCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.request.CadastraCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.response.CategoriaResponse;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;

public class CategoriaMapper {

    public static CategoriaEntity toEntity(CadastraCategoriaRequest categoriaRequest) {
        if (categoriaRequest == null) {
            return null;
        }
        CategoriaEntity entity = new CategoriaEntity();
        entity.setTitulo(categoriaRequest.getTitulo());
        entity.setDescricao(categoriaRequest.getDescricao());
        entity.setCategoriaId(categoriaRequest.getCategoriaId());
        return entity;
    }

    public static CategoriaResponse toResponse(CategoriaEntity categoriaEntity) {
        if (categoriaEntity == null) {
            return null;
        }
        CategoriaResponse response = new CategoriaResponse();
        response.setTitulo(categoriaEntity.getTitulo());
        response.setDescricao(categoriaEntity.getDescricao());
        response.setCategoriaId(categoriaEntity.getCategoriaId());
        return response;
    }

    public static CategoriaEntity updateEntity(CategoriaEntity categoriaEntity, AtualizaCategoriaRequest categoriaRequest) {
        if (categoriaEntity == null || categoriaRequest == null) {
            return null;
        }
        categoriaEntity.setCategoriaId(categoriaEntity.getCategoriaId());
        categoriaEntity.setDescricao(categoriaRequest.getDescricao());
        categoriaEntity.setTitulo(categoriaRequest.getTitulo());
        return categoriaEntity;
    }

    public static CategoriaDTO toDTO(CategoriaEntity categoriaEntity) {
        if (categoriaEntity == null) {
            return null;
        }
        return new CategoriaDTO(
                categoriaEntity.getCategoriaId(),
                categoriaEntity.getTitulo(),
                categoriaEntity.getDescricao());
    }
}