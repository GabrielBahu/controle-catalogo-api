package com.br.controle_catalogo_api.application.mapper;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaProdutoRequest;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;
import com.br.controle_catalogo_api.domain.model.ProdutoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProdutoMapperTest {

    @DisplayName("Mantém valores originais quando AtualizaProdutoRequest possui campos vazios")
    @Test
    void keepOriginalValuesWhenRequestFieldsAreEmpty() {
        ProdutoEntity entity = new ProdutoEntity();
        entity.setProdutoId("1");
        entity.setTitulo("Produto Original");
        entity.setDescricao("Descrição Original");
        entity.setPreco(BigDecimal.valueOf(50.0));

        AtualizaProdutoRequest request = new AtualizaProdutoRequest();
        request.setTitulo("");
        request.setDescricao("");
        request.setPreco(BigDecimal.ZERO);

        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoriaId("1");
        categoriaEntity.setTitulo("Categoria Atualizada");
        categoriaEntity.setDescricao("Descrição Atualizada");

        ProdutoEntity updatedEntity = ProdutoMapper.updateEntity(entity, request, categoriaEntity);

        assertEquals("Produto Original", updatedEntity.getTitulo());
        assertEquals("Descrição Original", updatedEntity.getDescricao());
        assertEquals(BigDecimal.valueOf(50.0), updatedEntity.getPreco());
        assertEquals(categoriaEntity, updatedEntity.getCategoria());
    }

    @DisplayName("Atualiza apenas título mantendo outros valores originais")
    @Test
    void updateOnlyTitleKeepingOtherOriginalValues() {
        ProdutoEntity entity = new ProdutoEntity();
        entity.setProdutoId("1");
        entity.setTitulo("Produto Original");
        entity.setDescricao("Descrição Original");
        entity.setPreco(BigDecimal.valueOf(50.0));

        AtualizaProdutoRequest request = new AtualizaProdutoRequest();
        request.setTitulo("Produto Atualizado");
        request.setDescricao("");
        request.setPreco(BigDecimal.ZERO);

        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoriaId("1");

        ProdutoEntity updatedEntity = ProdutoMapper.updateEntity(entity, request, categoriaEntity);

        assertEquals("Produto Atualizado", updatedEntity.getTitulo());
        assertEquals("Descrição Original", updatedEntity.getDescricao());
        assertEquals(BigDecimal.valueOf(50.0), updatedEntity.getPreco());
        assertEquals(categoriaEntity, updatedEntity.getCategoria());
    }

    @DisplayName("Atualiza apenas descrição mantendo outros valores originais")
    @Test
    void updateOnlyDescriptionKeepingOtherOriginalValues() {
        ProdutoEntity entity = new ProdutoEntity();
        entity.setProdutoId("1");
        entity.setTitulo("Produto Original");
        entity.setDescricao("Descrição Original");
        entity.setPreco(BigDecimal.valueOf(50.0));

        AtualizaProdutoRequest request = new AtualizaProdutoRequest();
        request.setTitulo("");
        request.setDescricao("Descrição Atualizada");
        request.setPreco(BigDecimal.ZERO);

        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoriaId("1");

        ProdutoEntity updatedEntity = ProdutoMapper.updateEntity(entity, request, categoriaEntity);

        assertEquals("Produto Original", updatedEntity.getTitulo());
        assertEquals("Descrição Atualizada", updatedEntity.getDescricao());
        assertEquals(BigDecimal.valueOf(50.0), updatedEntity.getPreco());
        assertEquals(categoriaEntity, updatedEntity.getCategoria());
    }

    @DisplayName("Atualiza apenas preço mantendo outros valores originais")
    @Test
    void updateOnlyPriceKeepingOtherOriginalValues() {
        ProdutoEntity entity = new ProdutoEntity();
        entity.setProdutoId("1");
        entity.setTitulo("Produto Original");
        entity.setDescricao("Descrição Original");
        entity.setPreco(BigDecimal.valueOf(50.0));

        AtualizaProdutoRequest request = new AtualizaProdutoRequest();
        request.setTitulo("");
        request.setDescricao("");
        request.setPreco(BigDecimal.valueOf(75.0));

        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoriaId("1");

        ProdutoEntity updatedEntity = ProdutoMapper.updateEntity(entity, request, categoriaEntity);

        assertEquals("Produto Original", updatedEntity.getTitulo());
        assertEquals("Descrição Original", updatedEntity.getDescricao());
        assertEquals(BigDecimal.valueOf(75.0), updatedEntity.getPreco());
        assertEquals(categoriaEntity, updatedEntity.getCategoria());
    }
}
