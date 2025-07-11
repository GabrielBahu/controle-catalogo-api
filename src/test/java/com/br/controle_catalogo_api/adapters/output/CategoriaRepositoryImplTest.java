package com.br.controle_catalogo_api.adapters.output;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.response.CategoriaResponse;
import com.br.controle_catalogo_api.application.exception.CategoriaNotFoundException;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaRepositoryImplTest {

    @Mock
    private CategoriaSpringRepository categoriaSpringRepository;

    @InjectMocks
    private CategoriaRepositoryImpl categoriaRepositoryImpl;

    @Test
    @DisplayName("Deve verificar a existência de uma categoria pelo ID com sucesso")
    void verifyCategoryExistenceByIdSuccessfully() {
        when(categoriaSpringRepository.existsByCategoriaId("1")).thenReturn(true);

        boolean exists = categoriaRepositoryImpl.existsByCategoriaId("1");

        assertTrue(exists);
        verify(categoriaSpringRepository, times(1)).existsByCategoriaId("1");
    }

    @Test
    @DisplayName("Deve inserir uma nova categoria com sucesso")
    void insertNewCategorySuccessfully() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoriaId("1");
        categoriaEntity.setDescricao("Categoria Teste");
        categoriaEntity.setTitulo("Categoria Teste");

        CategoriaEntity savedEntity = new CategoriaEntity();
        savedEntity.setCategoriaId("1");
        savedEntity.setDescricao("Categoria Teste");
        savedEntity.setTitulo("Categoria Teste");

        CategoriaResponse response = new CategoriaResponse();
        response.setCategoriaId("1");
        response.setTitulo("Categoria Teste");
        response.setDescricao("Categoria Teste");

        when(categoriaSpringRepository.save(categoriaEntity)).thenReturn(savedEntity);

        CategoriaResponse result = categoriaRepositoryImpl.insert(categoriaEntity);

        assertEquals(response.getCategoriaId(), result.getCategoriaId());
        assertEquals(response.getDescricao(), result.getTitulo());
        verify(categoriaSpringRepository, times(1)).save(categoriaEntity);
    }

    @Test
    @DisplayName("Deve retornar todas as categorias com sucesso")
    void getAllCategoriesSuccessfully() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoriaId("1");
        categoriaEntity.setDescricao("Categoria Teste");
        categoriaEntity.setTitulo("Categoria Teste");

        List<CategoriaEntity> categoriaEntities = Collections.singletonList(categoriaEntity);

        when(categoriaSpringRepository.findAll()).thenReturn(categoriaEntities);

        List<CategoriaResponse> result = categoriaRepositoryImpl.getAll();

        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getCategoriaId());
        assertEquals("Categoria Teste", result.get(0).getTitulo());
        verify(categoriaSpringRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar uma categoria pelo ID com sucesso")
    void getCategoryByIdSuccessfully() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoriaId("1");
        categoriaEntity.setDescricao("Categoria Teste");
        categoriaEntity.setTitulo("Categoria Teste");

        when(categoriaSpringRepository.findByCategoriaId("1")).thenReturn(Optional.of(categoriaEntity));

        CategoriaEntity result = categoriaRepositoryImpl.getById("1");

        assertEquals(categoriaEntity, result);
        verify(categoriaSpringRepository, times(1)).findByCategoriaId("1");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar categoria inexistente pelo ID")
    void throwExceptionWhenCategoryNotFoundById() {
        when(categoriaSpringRepository.findByCategoriaId("999")).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> categoriaRepositoryImpl.getById("999"));
        verify(categoriaSpringRepository, times(1)).findByCategoriaId("999");
    }

    @Test
    @DisplayName("Deve atualizar uma categoria com sucesso")
    void updateCategorySuccessfully() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoriaId("1");
        categoriaEntity.setDescricao("Categoria Teste");
        categoriaEntity.setTitulo("Categoria Teste");

        AtualizaCategoriaRequest categoriaRequest = new AtualizaCategoriaRequest();
        categoriaRequest.setTitulo("Categoria Atualizada");
        categoriaRequest.setDescricao("Categoria Atualizada");

        CategoriaEntity updatedEntity = new CategoriaEntity();
        updatedEntity.setCategoriaId("1");
        updatedEntity.setDescricao("Categoria Atualizada");
        updatedEntity.setTitulo("Categoria Atualizada");

        when(categoriaSpringRepository.findByCategoriaId("1")).thenReturn(Optional.of(categoriaEntity));
        when(categoriaSpringRepository.save(any(CategoriaEntity.class))).thenReturn(updatedEntity);

        CategoriaResponse result = categoriaRepositoryImpl.update("1", categoriaRequest);

        assertEquals("1", result.getCategoriaId());
        assertEquals("Categoria Atualizada", result.getTitulo());
        assertEquals("Categoria Atualizada", result.getDescricao());
        verify(categoriaSpringRepository, times(1)).findByCategoriaId("1");
        verify(categoriaSpringRepository, times(1)).save(any(CategoriaEntity.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar categoria inexistente")
    void throwExceptionWhenUpdatingNonExistentCategory() {
        AtualizaCategoriaRequest categoriaRequest = new AtualizaCategoriaRequest();
        categoriaRequest.setDescricao("Categoria Atualizada");
        categoriaRequest.setTitulo("Categoria Atualizada");

        when(categoriaSpringRepository.findByCategoriaId("999")).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> categoriaRepositoryImpl.update("999", categoriaRequest));
        verify(categoriaSpringRepository, times(1)).findByCategoriaId("999");
    }

    @Test
    @DisplayName("Deve deletar uma categoria com sucesso")
    void deleteCategorySuccessfully() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setCategoriaId("1");
        categoriaEntity.setDescricao("Categoria Teste");
        categoriaEntity.setTitulo("Categoria Teste");

        when(categoriaSpringRepository.findByCategoriaId("1")).thenReturn(Optional.of(categoriaEntity));
        doNothing().when(categoriaSpringRepository).delete(categoriaEntity);

        categoriaRepositoryImpl.delete("1");

        verify(categoriaSpringRepository, times(1)).findByCategoriaId("1");
        verify(categoriaSpringRepository, times(1)).delete(categoriaEntity);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar categoria inexistente")
    void throwExceptionWhenDeletingNonExistentCategory() {
        when(categoriaSpringRepository.findByCategoriaId("999")).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> categoriaRepositoryImpl.delete("999"));
        verify(categoriaSpringRepository, times(1)).findByCategoriaId("999");
    }
}
