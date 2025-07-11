package com.br.controle_catalogo_api.application.mapper;

import com.br.controle_catalogo_api.adapters.input.dto.CategoriaDTO;
import com.br.controle_catalogo_api.adapters.input.request.AtualizaProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.request.CadastraProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.response.ProdutoResponse;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;
import com.br.controle_catalogo_api.domain.model.ProdutoEntity;

import java.math.BigDecimal;

public class ProdutoMapper {

    public static ProdutoEntity toEntity(CadastraProdutoRequest produtoRequest, CategoriaEntity categoriaEntity) {
        if (produtoRequest == null || categoriaEntity == null) {
            return null;
        }

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setProdutoId(produtoRequest.getProdutoId());
        produtoEntity.setTitulo(produtoRequest.getTitulo());
        produtoEntity.setDescricao(produtoRequest.getDescricao());
        produtoEntity.setPreco(produtoRequest.getPreco());
        produtoEntity.setCategoria(categoriaEntity);

        return produtoEntity;
    }

    public static ProdutoResponse toResponse(ProdutoEntity produtoEntity, CategoriaDTO categoriaDTO) {
        if (produtoEntity == null) {
            return null;
        }

        ProdutoResponse response = new ProdutoResponse();
        response.setProdutoId(produtoEntity.getProdutoId());
        response.setTitulo(produtoEntity.getTitulo());
        response.setDescricao(produtoEntity.getDescricao());
        response.setPreco(produtoEntity.getPreco());
        response.setCategoria(categoriaDTO);

        return response;
    }

    public static ProdutoEntity updateEntity(ProdutoEntity produtoEntity, AtualizaProdutoRequest produtoRequest, CategoriaEntity categoriaEntity) {
        if (produtoEntity == null || produtoRequest == null || categoriaEntity == null) {
            return null;
        }

        String titulo = produtoRequest.getTitulo();
        String descricao = produtoRequest.getDescricao();
        BigDecimal preco = produtoRequest.getPreco();

        produtoEntity.setProdutoId(produtoEntity.getProdutoId());

        if (titulo != null && !titulo.isEmpty()) {
            produtoEntity.setTitulo(titulo);
        }

        if (descricao != null && !descricao.isEmpty()) {
            produtoEntity.setDescricao(descricao);
        }

        if (preco != null && preco.compareTo(BigDecimal.ZERO) > 0) {
            produtoEntity.setPreco(preco);
        }

        produtoEntity.setCategoria(categoriaEntity);

        return produtoEntity;
    }
}
