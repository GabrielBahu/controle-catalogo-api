package com.br.controle_catalogo_api.adapters.input.controller;

import com.br.controle_catalogo_api.adapters.input.ProdutoController;
import com.br.controle_catalogo_api.adapters.input.request.AtualizaProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.request.CadastraProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.response.ProdutoResponse;
import com.br.controle_catalogo_api.domain.port.ProdutoServicePort;
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
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceControllerTest {

    private ProdutoServicePort produtoServicePort;
    private ProdutoController produtoController;

    @BeforeEach
    void setup() {
        produtoServicePort = Mockito.mock(ProdutoServicePort.class);
        produtoController = new ProdutoController(produtoServicePort);
    }

    @Test
    @DisplayName("Deve inserir um novo produto com sucesso")
    void insertProductSuccessfully() {
        CadastraProdutoRequest request = new CadastraProdutoRequest();
        request.setProdutoId("1");
        request.setTitulo("Título do Produto Teste");
        request.setDescricao("Descrição do Produto Teste");

        ProdutoResponse response = new ProdutoResponse();
        response.setProdutoId("1");
        response.setTitulo("Título do Produto Teste");
        response.setDescricao("Descrição do Produto Teste");

        when(produtoServicePort.insert(request)).thenReturn(response);

        ResponseEntity<ProdutoResponse> result = produtoController.insert(request);

        assertEquals(ResponseEntity.ok().body(response), result);
        verify(produtoServicePort, times(1)).insert(request);
    }

    @Test
    @DisplayName("Deve retornar todos os produtos com sucesso")
    void getAllProductsSuccessfully() {
        ProdutoResponse response = new ProdutoResponse();
        response.setProdutoId("1");
        response.setTitulo("Título do Produto Teste");
        response.setDescricao("Descrição do Produto Teste");
        List<ProdutoResponse> responseList = Collections.singletonList(response);

        when(produtoServicePort.getAll()).thenReturn(responseList);

        ResponseEntity<List<ProdutoResponse>> result = produtoController.getAll();

        assertEquals(ResponseEntity.ok().body(responseList), result);
        verify(produtoServicePort, times(1)).getAll();
    }

    @Test
    @DisplayName("Deve atualizar um produto com sucesso")
    void updateProductSuccessfully() {
        AtualizaProdutoRequest request = new AtualizaProdutoRequest();
        request.setTitulo("Título do Produto Atualizado");
        request.setDescricao("Descrição do Produto Atualizado");

        ProdutoResponse response = new ProdutoResponse();
        response.setProdutoId("1");
        response.setTitulo("Título do Produto Atualizado");
        response.setDescricao("Descrição do Produto Atualizado");

        when(produtoServicePort.update("1", request)).thenReturn(response);

        ResponseEntity<ProdutoResponse> result = produtoController.update("1", request);

        assertEquals(ResponseEntity.ok().body(response), result);
        verify(produtoServicePort, times(1)).update("1", request);
    }

    @Test
    @DisplayName("Deve deletar um produto com sucesso")
    void deleteProductSuccessfully() {
        doNothing().when(produtoServicePort).delete("1");

        ResponseEntity<Void> result = produtoController.delete("1");

        assertEquals(ResponseEntity.noContent().build(), result);
        verify(produtoServicePort, times(1)).delete("1");
    }

    @Test
    @DisplayName("Deve lidar com lista vazia de produtos")
    void handleEmptyProductList() {
        when(produtoServicePort.getAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<ProdutoResponse>> result = produtoController.getAll();

        assertEquals(ResponseEntity.ok().body(Collections.emptyList()), result);
        verify(produtoServicePort, times(1)).getAll();
    }

    @Test
    @DisplayName("Deve lidar com atualização de produto inexistente")
    void handleNonExistentProductUpdate() {
        AtualizaProdutoRequest request = new AtualizaProdutoRequest();
        request.setTitulo("Título do Produto Inexistente");
        request.setDescricao("Descrição do Produto Inexistente");

        when(produtoServicePort.update("999", request)).thenThrow(new RuntimeException("Produto não encontrado"));

        try {
            produtoController.update("999", request);
        } catch (RuntimeException e) {
            assertEquals("Produto não encontrado", e.getMessage());
        }

        verify(produtoServicePort, times(1)).update("999", request);
    }
}
