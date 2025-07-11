package com.br.controle_catalogo_api.application.mapper;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaCategoriaRequest;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class CategoriaMapperTest {

    @Test
    @DisplayName("Manter valores originais quando campos são preenchidos com strings não vazias")
    void keepOriginalValuesWhenFieldsAreNotEmpty() {
        CategoriaEntity entity = new CategoriaEntity();
        entity.setTitulo("Categoria Original");
        entity.setDescricao("Descrição Original");
        entity.setCategoriaId("1");

        AtualizaCategoriaRequest request = new AtualizaCategoriaRequest();
        request.setTitulo("Categoria Original");
        request.setDescricao("Descrição Original");

        CategoriaEntity updatedEntity = CategoriaMapper.updateEntity(entity, request);

        assertEquals("Categoria Original", updatedEntity.getTitulo());
        assertEquals("Descrição Original", updatedEntity.getDescricao());
        assertEquals("1", updatedEntity.getCategoriaId());
    }

    @Test
    @DisplayName("Atualizar apenas o título mantendo a descrição original")
    void updateOnlyTitleKeepingOriginalDescription() {
        CategoriaEntity entity = new CategoriaEntity();
        entity.setTitulo("Categoria Original");
        entity.setDescricao("Descrição Original");
        entity.setCategoriaId("1");

        AtualizaCategoriaRequest request = new AtualizaCategoriaRequest();
        request.setTitulo("Categoria Atualizada");
        request.setDescricao("Descrição Original");

        CategoriaEntity updatedEntity = CategoriaMapper.updateEntity(entity, request);

        assertEquals("Categoria Atualizada", updatedEntity.getTitulo());
        assertEquals("Descrição Original", updatedEntity.getDescricao());
        assertEquals("1", updatedEntity.getCategoriaId());
    }

    @Test
    @DisplayName("Atualizar apenas a descrição mantendo o título original")
    void updateOnlyDescriptionKeepingOriginalTitle() {
        CategoriaEntity entity = new CategoriaEntity();
        entity.setTitulo("Categoria Original");
        entity.setDescricao("Descrição Original");
        entity.setCategoriaId("1");

        AtualizaCategoriaRequest request = new AtualizaCategoriaRequest();
        request.setTitulo("Categoria Original");
        request.setDescricao("Descrição Atualizada");

        CategoriaEntity updatedEntity = CategoriaMapper.updateEntity(entity, request);

        assertEquals("Categoria Original", updatedEntity.getTitulo());
        assertEquals("Descrição Atualizada", updatedEntity.getDescricao());
        assertEquals("1", updatedEntity.getCategoriaId());
    }

    @Test
    @DisplayName("Não atualizar quando entity é null mas request é válido")
    void doNotUpdateWhenEntityIsNullButRequestIsValid() {
        AtualizaCategoriaRequest request = new AtualizaCategoriaRequest();
        request.setTitulo("Categoria Teste");
        request.setDescricao("Descrição Teste");

        CategoriaEntity updatedEntity = CategoriaMapper.updateEntity(null, request);

        assertNull(updatedEntity);
    }
}
