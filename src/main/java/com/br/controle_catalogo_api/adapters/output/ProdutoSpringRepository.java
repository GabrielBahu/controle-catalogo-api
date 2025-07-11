package com.br.controle_catalogo_api.adapters.output;

import com.br.controle_catalogo_api.domain.model.ProdutoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoSpringRepository extends MongoRepository<ProdutoEntity, String> {
    boolean existsByProdutoId(String produtoId);

    Optional<ProdutoEntity> findByProdutoId(String id);
}
