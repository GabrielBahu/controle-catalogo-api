package com.br.controle_catalogo_api.adapters.output;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.response.ProdutoResponse;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;
import com.br.controle_catalogo_api.domain.model.ProdutoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoRepositoryImplTest {

    @Mock
    private ProdutoSpringRepository produtoSpringRepository;

    @InjectMocks
    private ProdutoRepositoryImpl produtoRepositoryImpl;

    @Test
    @DisplayName("Deve verificar a existência de um produto pelo ID com sucesso")
    void verifyProductExistenceByIdSuccessfully() {
        when(produtoSpringRepository.existsByProdutoId("1")).thenReturn(true);

        boolean exists = produtoRepositoryImpl.existsByProdutoId("1");

        assertTrue(exists);
        verify(produtoSpringRepository, times(1)).existsByProdutoId("1");
    }

    @Test
    @DisplayName("Deve inserir um novo produto com sucesso")
    void insertNewProductSuccessfully() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoriaId("1");
        categoriaEntity.setTitulo("Categoria Teste");
        categoriaEntity.setDescricao("Descrição Categoria");

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setProdutoId("1");
        produtoEntity.setTitulo("Produto Teste");
        produtoEntity.setDescricao("Descrição Teste");
        produtoEntity.setCategoria(categoriaEntity);

        ProdutoEntity savedEntity = new ProdutoEntity();
        savedEntity.setProdutoId("1");
        savedEntity.setTitulo("Produto Teste");
        savedEntity.setDescricao("Descrição Teste");
        savedEntity.setCategoria(categoriaEntity);

        when(produtoSpringRepository.save(produtoEntity)).thenReturn(savedEntity);

        ProdutoResponse result = produtoRepositoryImpl.insert(produtoEntity);

        assertEquals("1", result.getProdutoId());
        assertEquals("Produto Teste", result.getTitulo());
        assertEquals("Descrição Teste", result.getDescricao());
        verify(produtoSpringRepository, times(1)).save(produtoEntity);
    }

    @Test
    @DisplayName("Deve retornar todos os produtos com sucesso")
    void getAllProductsSuccessfully() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoriaId("1");
        categoriaEntity.setTitulo("Categoria Teste");
        categoriaEntity.setDescricao("Descrição Categoria");

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setProdutoId("1");
        produtoEntity.setTitulo("Produto Teste");
        produtoEntity.setDescricao("Descrição Teste");
        produtoEntity.setCategoria(categoriaEntity);

        List<ProdutoEntity> produtoEntities = Collections.singletonList(produtoEntity);

        when(produtoSpringRepository.findAll()).thenReturn(produtoEntities);

        List<ProdutoResponse> result = produtoRepositoryImpl.getAll();

        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getProdutoId());
        assertEquals("Produto Teste", result.get(0).getTitulo());
        assertEquals("Descrição Teste", result.get(0).getDescricao());
        verify(produtoSpringRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar um produto pelo ID com sucesso")
    void getProductByIdSuccessfully() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoriaId("1");
        categoriaEntity.setTitulo("Categoria Teste");
        categoriaEntity.setDescricao("Descrição Categoria");

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setProdutoId("1");
        produtoEntity.setTitulo("Produto Teste");
        produtoEntity.setDescricao("Descrição Teste");
        produtoEntity.setCategoria(categoriaEntity);

        when(produtoSpringRepository.findByProdutoId("1")).thenReturn(Optional.of(produtoEntity));

        ProdutoEntity result = produtoRepositoryImpl.getById("1");

        assertEquals(produtoEntity, result);
        verify(produtoSpringRepository, times(1)).findByProdutoId("1");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar produto inexistente pelo ID")
    void throwExceptionWhenProductNotFoundById() {
        when(produtoSpringRepository.findByProdutoId("999")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> produtoRepositoryImpl.getById("999"));
        verify(produtoSpringRepository, times(1)).findByProdutoId("999");
    }

    @Test
    @DisplayName("Deve atualizar um produto com sucesso")
    void updateProductSuccessfully() {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setProdutoId("1");
        produtoEntity.setTitulo("Produto Teste");
        produtoEntity.setDescricao("Descrição Teste");

        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoriaId("1");
        categoriaEntity.setTitulo("Categoria Teste");
        categoriaEntity.setDescricao("Descrição Categoria");

        AtualizaProdutoRequest produtoRequest = new AtualizaProdutoRequest();
        produtoRequest.setTitulo("Produto Atualizado");
        produtoRequest.setDescricao("Descrição Atualizada");
        produtoRequest.setPreco(new BigDecimal("99.99"));
        produtoRequest.setCategoriaId("1");

        ProdutoEntity updatedEntity = new ProdutoEntity();
        updatedEntity.setProdutoId("1");
        updatedEntity.setTitulo("Produto Atualizado");
        updatedEntity.setDescricao("Descrição Atualizada");
        updatedEntity.setCategoria(categoriaEntity);

        when(produtoSpringRepository.findByProdutoId("1")).thenReturn(Optional.of(produtoEntity));
        when(produtoSpringRepository.save(any(ProdutoEntity.class))).thenReturn(updatedEntity);

        ProdutoResponse result = produtoRepositoryImpl.update("1", produtoRequest, categoriaEntity);

        assertEquals("1", result.getProdutoId());
        assertEquals("Produto Atualizado", result.getTitulo());
        assertEquals("Descrição Atualizada", result.getDescricao());
        verify(produtoSpringRepository, times(1)).findByProdutoId("1");
        verify(produtoSpringRepository, times(1)).save(any(ProdutoEntity.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar produto inexistente")
    void throwExceptionWhenUpdatingNonExistentProduct() {
        AtualizaProdutoRequest produtoRequest = new AtualizaProdutoRequest();
        produtoRequest.setTitulo("Produto Atualizado");
        produtoRequest.setDescricao("Descrição Atualizada");

        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoriaId("1");
        categoriaEntity.setTitulo("Categoria Teste");
        categoriaEntity.setDescricao("Descrição Categoria");

        when(produtoSpringRepository.findByProdutoId("999")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> produtoRepositoryImpl.update("999", produtoRequest, categoriaEntity));
        verify(produtoSpringRepository, times(1)).findByProdutoId("999");
    }

    @Test
    @DisplayName("Deve deletar um produto com sucesso")
    void deleteProductSuccessfully() {
        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setProdutoId("1");
        produtoEntity.setTitulo("Produto Teste");
        produtoEntity.setDescricao("Descrição Teste");

        when(produtoSpringRepository.findByProdutoId("1")).thenReturn(Optional.of(produtoEntity));
        doNothing().when(produtoSpringRepository).delete(produtoEntity);

        produtoRepositoryImpl.delete("1");

        verify(produtoSpringRepository, times(1)).findByProdutoId("1");
        verify(produtoSpringRepository, times(1)).delete(produtoEntity);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar produto inexistente")
    void throwExceptionWhenDeletingNonExistentProduct() {
        when(produtoSpringRepository.findByProdutoId("999")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> produtoRepositoryImpl.delete("999"));
        verify(produtoSpringRepository, times(1)).findByProdutoId("999");
    }
}
