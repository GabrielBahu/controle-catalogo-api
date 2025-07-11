package com.br.controle_catalogo_api.application.service;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.request.CadastraProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.response.CategoriaResponse;
import com.br.controle_catalogo_api.adapters.input.response.ProdutoResponse;
import com.br.controle_catalogo_api.application.exception.CategoriaNotFoundException;
import com.br.controle_catalogo_api.application.exception.ProdutoExistenteException;
import com.br.controle_catalogo_api.application.exception.ProdutoNotFoundException;
import com.br.controle_catalogo_api.domain.dto.MensagemDTO;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;
import com.br.controle_catalogo_api.domain.model.ProdutoEntity;
import com.br.controle_catalogo_api.domain.port.CategoriaServicePort;
import com.br.controle_catalogo_api.domain.port.ProdutoRepositoryPort;
import com.br.controle_catalogo_api.domain.port.out.AwsSnsServicePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceImplTest {


    @Mock
    private ProdutoRepositoryPort produtoRepositoryPort;

    @Mock
    private CategoriaServicePort categoriaServicePort;

    @Mock
    private AwsSnsServicePort awsSnsServicePort;

    @InjectMocks
    private ProdutoServiceImpl produtoServiceImpl;

    @Test
    @DisplayName("Insere produto com sucesso quando categoria existe e ID não está em uso")
    void insertProductSuccessfullyWhenCategoryExistsAndIdNotInUse() {
        CadastraProdutoRequest request = new CadastraProdutoRequest();
        request.setProdutoId("1");
        request.setTitulo("Produto Teste");
        request.setDescricao("Descrição Teste");
        request.setPreco(new BigDecimal("100.00"));
        request.setCategoriaId("1");

        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setCategoriaId("1");
        categoria.setTitulo("Categoria Teste");

        ProdutoEntity entity = new ProdutoEntity();
        entity.setProdutoId("1");
        entity.setTitulo("Produto Teste");
        entity.setDescricao("Descrição Teste");
        entity.setPreco(new BigDecimal("100.00"));
        entity.setCategoria(categoria);

        ProdutoResponse expectedResponse = new ProdutoResponse();
        expectedResponse.setProdutoId("1");
        expectedResponse.setTitulo("Produto Teste");
        expectedResponse.setDescricao("Descrição Teste");
        expectedResponse.setPreco(new BigDecimal("100.00"));

        when(categoriaServicePort.getById("1")).thenReturn(categoria);
        when(produtoRepositoryPort.existsByProdutoId("1")).thenReturn(false);
        when(produtoRepositoryPort.insert(any(ProdutoEntity.class))).thenReturn(expectedResponse);
        doNothing().when(awsSnsServicePort).publicarMensagem(any(MensagemDTO.class));

        ProdutoResponse result = produtoServiceImpl.insert(request);

        assertEquals(expectedResponse, result);
        verify(categoriaServicePort).getById("1");
        verify(produtoRepositoryPort).existsByProdutoId("1");
        verify(produtoRepositoryPort).insert(any(ProdutoEntity.class));
        verify(awsSnsServicePort).publicarMensagem(any(MensagemDTO.class));
    }

    @Test
    @DisplayName("Lança exceção ao inserir produto com ID já existente")
    void throwExceptionWhenInsertingProductWithExistingId() {
        CadastraProdutoRequest request = new CadastraProdutoRequest();
        request.setProdutoId("1");
        request.setCategoriaId("1");

        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setCategoriaId("1");

        when(categoriaServicePort.getById("1")).thenReturn(categoria);
        when(produtoRepositoryPort.existsByProdutoId("1")).thenReturn(true);

        assertThrows(ProdutoExistenteException.class, () -> produtoServiceImpl.insert(request));
        verify(categoriaServicePort).getById("1");
        verify(produtoRepositoryPort).existsByProdutoId("1");
        verify(produtoRepositoryPort, never()).insert(any(ProdutoEntity.class));
        verify(awsSnsServicePort, never()).publicarMensagem(any(MensagemDTO.class));
    }

    @Test
    @DisplayName("Lança exceção ao inserir produto com categoria inexistente")
    void throwExceptionWhenInsertingProductWithNonExistingCategory() {
        CadastraProdutoRequest request = new CadastraProdutoRequest();
        request.setProdutoId("1");
        request.setCategoriaId("999");

        when(categoriaServicePort.getById("999")).thenThrow(new CategoriaNotFoundException("Categoria não encontrada"));

        assertThrows(CategoriaNotFoundException.class, () -> produtoServiceImpl.insert(request));
        verify(categoriaServicePort).getById("999");
        verify(produtoRepositoryPort, never()).existsByProdutoId(anyString());
        verify(produtoRepositoryPort, never()).insert(any(ProdutoEntity.class));
        verify(awsSnsServicePort, never()).publicarMensagem(any(MensagemDTO.class));
    }

    @Test
    @DisplayName("Busca todos os produtos com sucesso")
    void getAllProductsSuccessfully() {
        ProdutoResponse response1 = new ProdutoResponse();
        response1.setProdutoId("1");
        response1.setTitulo("Produto 1");
        response1.setDescricao("Descrição 1");

        ProdutoResponse response2 = new ProdutoResponse();
        response2.setProdutoId("2");
        response2.setTitulo("Produto 2");
        response2.setDescricao("Descrição 2");

        List<ProdutoResponse> expectedResponses = List.of(response1, response2);

        when(produtoRepositoryPort.getAll()).thenReturn(expectedResponses);

        List<ProdutoResponse> result = produtoServiceImpl.getAll();

        assertEquals(expectedResponses, result);
        verify(produtoRepositoryPort).getAll();
    }

    @Test
    @DisplayName("Atualiza produto com sucesso quando existe e categoria é válida")
    void updateProductSuccessfullyWhenExistsAndCategoryValid() {
        AtualizaProdutoRequest request = new AtualizaProdutoRequest();
        request.setTitulo("Produto Atualizado");
        request.setDescricao("Descrição Atualizada");
        request.setPreco(new BigDecimal("150.00"));
        request.setCategoriaId("2");

        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setCategoriaId("2");
        categoria.setTitulo("Categoria Nova");

        ProdutoResponse expectedResponse = new ProdutoResponse();
        expectedResponse.setProdutoId("1");
        expectedResponse.setTitulo("Produto Atualizado");
        expectedResponse.setDescricao("Descrição Atualizada");
        expectedResponse.setPreco(new BigDecimal("150.00"));

        when(produtoRepositoryPort.existsByProdutoId("1")).thenReturn(true);
        when(categoriaServicePort.getById("2")).thenReturn(categoria);
        when(produtoRepositoryPort.update(eq("1"), any(AtualizaProdutoRequest.class), any(CategoriaEntity.class))).thenReturn(expectedResponse);
        doNothing().when(awsSnsServicePort).publicarMensagem(any(MensagemDTO.class));

        ProdutoResponse result = produtoServiceImpl.update("1", request);

        assertEquals(expectedResponse, result);
        verify(produtoRepositoryPort).existsByProdutoId("1");
        verify(categoriaServicePort).getById("2");
        verify(produtoRepositoryPort).update(eq("1"), any(AtualizaProdutoRequest.class), any(CategoriaEntity.class));
        verify(awsSnsServicePort).publicarMensagem(any(MensagemDTO.class));
    }

    @Test
    @DisplayName("Atualiza produto mantendo categoria atual quando categoria não é informada")
    void updateProductKeepingCurrentCategoryWhenCategoryNotProvided() {
        AtualizaProdutoRequest request = new AtualizaProdutoRequest();
        request.setTitulo("Produto Atualizado");
        request.setDescricao("Descrição Atualizada");
        request.setPreco(new BigDecimal("99.99"));
        request.setCategoriaId("");

        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setCategoriaId("1");

        ProdutoResponse expectedResponse = new ProdutoResponse();
        expectedResponse.setProdutoId("1");
        expectedResponse.setTitulo("Produto Atualizado");

        when(produtoRepositoryPort.existsByProdutoId("1")).thenReturn(true);
        when(categoriaServicePort.getById("")).thenReturn(categoria);
        doReturn(expectedResponse).when(produtoRepositoryPort).update(eq("1"), eq(request), eq(categoria));

        ProdutoResponse result = produtoServiceImpl.update("1", request);

        assertEquals(expectedResponse, result);
        verify(produtoRepositoryPort).existsByProdutoId("1");
        verify(categoriaServicePort).getById("");
        verify(produtoRepositoryPort).update(eq("1"), eq(request), eq(categoria));
    }

    @Test
    @DisplayName("Lança exceção ao atualizar produto inexistente")
    void throwExceptionWhenUpdatingNonExistentProduct() {
        AtualizaProdutoRequest request = new AtualizaProdutoRequest();
        request.setTitulo("Produto Atualizado");

        when(produtoRepositoryPort.existsByProdutoId("999")).thenReturn(false);

        assertThrows(ProdutoNotFoundException.class, () -> produtoServiceImpl.update("999", request));
        verify(produtoRepositoryPort).existsByProdutoId("999");
        verify(produtoRepositoryPort, never()).update(anyString(), any(AtualizaProdutoRequest.class), any(CategoriaEntity.class));
        verify(awsSnsServicePort, never()).publicarMensagem(any(MensagemDTO.class));
    }

    @Test
    @DisplayName("Exclui produto com sucesso quando existe")
    void deleteProductSuccessfullyWhenExists() {
        when(produtoRepositoryPort.existsByProdutoId("1")).thenReturn(true);
        doNothing().when(produtoRepositoryPort).delete("1");

        produtoServiceImpl.delete("1");

        verify(produtoRepositoryPort).existsByProdutoId("1");
        verify(produtoRepositoryPort).delete("1");
    }

    @Test
    @DisplayName("Lança exceção ao excluir produto inexistente")
    void throwExceptionWhenDeletingNonExistentProduct() {
        when(produtoRepositoryPort.existsByProdutoId("999")).thenReturn(false);

        assertThrows(ProdutoNotFoundException.class, () -> produtoServiceImpl.delete("999"));
        verify(produtoRepositoryPort).existsByProdutoId("999");
        verify(produtoRepositoryPort, never()).delete(anyString());
    }

}
