package com.br.controle_catalogo_api.adapters.input.controller;

import com.br.controle_catalogo_api.adapters.input.CategoriaController;
import com.br.controle_catalogo_api.adapters.input.request.AtualizaCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.request.CadastraCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.response.CategoriaResponse;
import com.br.controle_catalogo_api.domain.port.CategoriaServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaControllerTest {


    private CategoriaServicePort categoriaServicePort;
    private CategoriaController categoriaController;

    @BeforeEach
    void setup() {
        categoriaServicePort = Mockito.mock(CategoriaServicePort.class);
        categoriaController = new CategoriaController(categoriaServicePort);
    }

    @Test
    @DisplayName("Deve inserir uma nova categoria com sucesso")
    void insertCategorySuccessfully() {
        CadastraCategoriaRequest request = new CadastraCategoriaRequest();
        request.setDescricao("Descrição da Categoria Teste");
        request.setTitulo("Título da Categoria Teste");
        request.setCategoriaId("1");

        CategoriaResponse response = new CategoriaResponse();
        response.setDescricao("Descrição da Categoria Teste");
        response.setTitulo("Título da Categoria Teste");
        response.setCategoriaId("1");

        when(categoriaServicePort.insert(request)).thenReturn(response);

        ResponseEntity<CategoriaResponse> result = categoriaController.insert(request);

        assertEquals(ResponseEntity.ok(response), result);
        verify(categoriaServicePort, times(1)).insert(request);
    }

    @Test
    @DisplayName("Deve retornar todas as categorias com sucesso")
    void getAllCategoriesSuccessfully() {
        CategoriaResponse response = new CategoriaResponse();
        response.setDescricao("Descrição da Categoria Teste");
        response.setTitulo("Título da Categoria Teste");
        response.setCategoriaId("1");
        List<CategoriaResponse> responseList = Collections.singletonList(response);

        when(categoriaServicePort.getAll()).thenReturn(responseList);

        ResponseEntity<List<CategoriaResponse>> result = categoriaController.getAll();

        assertEquals(ResponseEntity.ok(responseList), result);
        verify(categoriaServicePort, times(1)).getAll();
    }

    @Test
    @DisplayName("Deve atualizar uma categoria com sucesso")
    void updateCategorySuccessfully() {
        AtualizaCategoriaRequest request = new AtualizaCategoriaRequest();
        request.setDescricao("Descrição da Categoria Teste Atualizada");
        request.setTitulo("Título da Categoria Teste Atualizado");

        CategoriaResponse response = new CategoriaResponse();
        response.setDescricao("Descrição da Categoria Teste");
        response.setTitulo("Título da Categoria Teste");
        response.setCategoriaId("1");

        when(categoriaServicePort.update("1", request)).thenReturn(response);

        ResponseEntity<CategoriaResponse> result = categoriaController.update("1", request);

        assertEquals(ResponseEntity.ok(response), result);
        verify(categoriaServicePort, times(1)).update("1", request);
    }

    @Test
    @DisplayName("Deve deletar uma categoria com sucesso")
    void deleteCategorySuccessfully() {
        doNothing().when(categoriaServicePort).delete("1");

        ResponseEntity<Void> result = categoriaController.delete("1");

        assertEquals(ResponseEntity.noContent().build(), result);
        verify(categoriaServicePort, times(1)).delete("1");
    }

    @Test
    @DisplayName("Deve lidar com lista vazia de categorias")
    void handleEmptyCategoryList() {
        when(categoriaServicePort.getAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<CategoriaResponse>> result = categoriaController.getAll();

        assertEquals(ResponseEntity.ok(Collections.emptyList()), result);
        verify(categoriaServicePort, times(1)).getAll();
    }

    @Test
    @DisplayName("Deve lidar com atualização de categoria inexistente")
    void handleNonExistentCategoryUpdate() {
        AtualizaCategoriaRequest request = new AtualizaCategoriaRequest();
        request.setDescricao("Descrição da Categoria Inexistente");
        request.setTitulo("Título da Categoria Inexistente");

        when(categoriaServicePort.update("999", request)).thenThrow(new RuntimeException("Categoria não encontrada"));

        try {
            categoriaController.update("999", request);
        } catch (RuntimeException e) {
            assertEquals("Categoria não encontrada", e.getMessage());
        }

        verify(categoriaServicePort, times(1)).update("999", request);
    }
}
