package com.br.controle_catalogo_api.adapters.output;

import com.br.controle_catalogo_api.domain.model.CategoriaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaSpringRepository extends MongoRepository<CategoriaEntity, String> {

    Optional<CategoriaEntity> findByCategoriaId(String categoriaId);

    boolean existsByCategoriaId(String categoriaId);
}
