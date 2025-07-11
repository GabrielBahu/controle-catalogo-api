package com.br.controle_catalogo_api.application.service;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.request.CadastraCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.response.CategoriaResponse;
import com.br.controle_catalogo_api.application.exception.CadastraCategoriaException;
import com.br.controle_catalogo_api.application.exception.CategoriaExistenteException;
import com.br.controle_catalogo_api.application.exception.CategoriaNotFoundException;
import com.br.controle_catalogo_api.application.exception.ConsultaCategoriaException;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;
import com.br.controle_catalogo_api.domain.port.CategoriaRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceImplTest {

    @Mock
    private CategoriaRepositoryPort categoriaRepositoryPort;

    @InjectMocks
    private CategoriaServiceImpl categoriaServiceImpl;

    @Test
    @DisplayName("Insere categoria com sucesso quando ID não existe")
    void insertCategorySuccessfullyWhenIdDoesNotExist() {
        CadastraCategoriaRequest request = new CadastraCategoriaRequest();
        request.setCategoriaId("1");
        request.setTitulo("Categoria Teste");
        request.setDescricao("Descrição Teste");

        CategoriaEntity entity = new CategoriaEntity();
        entity.setCategoriaId("1");
        entity.setTitulo("Categoria Teste");
        entity.setDescricao("Descrição Teste");

        CategoriaResponse expectedResponse = new CategoriaResponse();
        expectedResponse.setCategoriaId("1");
        expectedResponse.setTitulo("Categoria Teste");
        expectedResponse.setDescricao("Descrição Teste");

        when(categoriaRepositoryPort.existsByCategoriaId("1")).thenReturn(false);
        when(categoriaRepositoryPort.insert(any(CategoriaEntity.class))).thenReturn(expectedResponse);

        CategoriaResponse result = categoriaServiceImpl.insert(request);

        assertEquals(expectedResponse, result);
        verify(categoriaRepositoryPort).existsByCategoriaId("1");
        verify(categoriaRepositoryPort).insert(any(CategoriaEntity.class));
    }

    @Test
    @DisplayName("Lança exceção ao tentar inserir categoria com ID existente")
    void throwExceptionWhenInsertingCategoryWithExistingId() {
        CadastraCategoriaRequest request = new CadastraCategoriaRequest();
        request.setCategoriaId("1");

        when(categoriaRepositoryPort.existsByCategoriaId("1")).thenReturn(true);

        assertThrows(CategoriaExistenteException.class, () -> categoriaServiceImpl.insert(request));
        verify(categoriaRepositoryPort).existsByCategoriaId("1");
        verify(categoriaRepositoryPort, never()).insert(any());
    }

    @Test
    @DisplayName("Lança exceção genérica ao inserir categoria")
    void throwGenericExceptionWhenInsertingCategory() {
        CadastraCategoriaRequest request = new CadastraCategoriaRequest();
        request.setCategoriaId("1");
        request.setTitulo("Título Teste");
        request.setDescricao("Descrição Teste");

        when(categoriaRepositoryPort.existsByCategoriaId("1")).thenReturn(false);
        when(categoriaRepositoryPort.insert(any(CategoriaEntity.class))).thenThrow(new RuntimeException("Erro ao inserir"));

        assertThrows(CadastraCategoriaException.class, () -> categoriaServiceImpl.insert(request));
        verify(categoriaRepositoryPort).existsByCategoriaId("1");
        verify(categoriaRepositoryPort).insert(any(CategoriaEntity.class));
    }

    @Test
    @DisplayName("Busca todas as categorias com sucesso")
    void getAllCategoriesSuccessfully() {
        CategoriaResponse response1 = new CategoriaResponse();
        response1.setCategoriaId("1");
        response1.setTitulo("Categoria 1");
        response1.setDescricao("Descrição 1");

        CategoriaResponse response2 = new CategoriaResponse();
        response2.setCategoriaId("2");
        response2.setTitulo("Categoria 2");
        response2.setDescricao("Descrição 2");

        List<CategoriaResponse> expectedResponses = List.of(response1, response2);

        when(categoriaRepositoryPort.getAll()).thenReturn(expectedResponses);

        List<CategoriaResponse> result = categoriaServiceImpl.getAll();

        assertEquals(expectedResponses, result);
        verify(categoriaRepositoryPort).getAll();
    }

    @Test
    @DisplayName("Lança exceção ao buscar todas as categorias")
    void throwExceptionWhenGettingAllCategories() {
        when(categoriaRepositoryPort.getAll()).thenThrow(new RuntimeException("Erro ao buscar"));

        assertThrows(ConsultaCategoriaException.class, () -> categoriaServiceImpl.getAll());
        verify(categoriaRepositoryPort).getAll();
    }

    @Test
    @DisplayName("Busca categoria por ID com sucesso")
    void getCategoryByIdSuccessfully() {
        CategoriaEntity expectedEntity = new CategoriaEntity();
        expectedEntity.setCategoriaId("1");
        expectedEntity.setTitulo("Categoria Teste");
        expectedEntity.setDescricao("Descrição Teste");

        when(categoriaRepositoryPort.getById("1")).thenReturn(expectedEntity);

        CategoriaEntity result = categoriaServiceImpl.getById("1");

        assertEquals(expectedEntity, result);
        verify(categoriaRepositoryPort).getById("1");
    }

    @Test
    @DisplayName("Lança exceção ao buscar categoria por ID inexistente")
    void throwExceptionWhenGettingCategoryByNonexistentId() {
        when(categoriaRepositoryPort.getById("999")).thenReturn(null);

        assertThrows(CategoriaNotFoundException.class, () -> categoriaServiceImpl.getById("999"));
        verify(categoriaRepositoryPort).getById("999");
    }

    @Test
    @DisplayName("Atualiza categoria com sucesso")
    void updateCategorySuccessfully() {
        AtualizaCategoriaRequest request = new AtualizaCategoriaRequest();
        request.setTitulo("Categoria Atualizada");
        request.setDescricao("Descrição Atualizada");

        CategoriaResponse expectedResponse = new CategoriaResponse();
        expectedResponse.setCategoriaId("1");
        expectedResponse.setTitulo("Categoria Atualizada");
        expectedResponse.setDescricao("Descrição Atualizada");

        when(categoriaRepositoryPort.update("1", request)).thenReturn(expectedResponse);

        CategoriaResponse result = categoriaServiceImpl.update("1", request);

        assertEquals(expectedResponse, result);
        verify(categoriaRepositoryPort).update("1", request);
    }

    @Test
    @DisplayName("Lança exceção ao atualizar categoria inexistente")
    void throwExceptionWhenUpdatingNonexistentCategory() {
        AtualizaCategoriaRequest request = new AtualizaCategoriaRequest();

        when(categoriaRepositoryPort.update("999", request))
                .thenThrow(new CategoriaNotFoundException("Categoria não encontrada"));

        assertThrows(CategoriaNotFoundException.class, () -> categoriaServiceImpl.update("999", request));
        verify(categoriaRepositoryPort).update("999", request);
    }

    @Test
    @DisplayName("Exclui categoria com sucesso")
    void deleteCategorySuccessfully() {
        when(categoriaRepositoryPort.existsByCategoriaId("1")).thenReturn(true);
        doNothing().when(categoriaRepositoryPort).delete("1");

        categoriaServiceImpl.delete("1");

        verify(categoriaRepositoryPort).existsByCategoriaId("1");
        verify(categoriaRepositoryPort).delete("1");
    }

    @Test
    @DisplayName("Lança exceção ao excluir categoria inexistente")
    void throwExceptionWhenDeletingNonexistentCategory() {
        when(categoriaRepositoryPort.existsByCategoriaId("999")).thenReturn(false);

        assertThrows(CategoriaNotFoundException.class, () -> categoriaServiceImpl.delete("999"));
        verify(categoriaRepositoryPort).existsByCategoriaId("999");
        verify(categoriaRepositoryPort, never()).delete(anyString());
    }
}
