package com.br.controle_catalogo_api.adapters.output;

import com.br.controle_catalogo_api.adapters.input.dto.CategoriaDTO;
import com.br.controle_catalogo_api.adapters.input.request.AtualizaProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.response.ProdutoResponse;
import com.br.controle_catalogo_api.application.mapper.CategoriaMapper;
import com.br.controle_catalogo_api.application.mapper.ProdutoMapper;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;
import com.br.controle_catalogo_api.domain.model.ProdutoEntity;
import com.br.controle_catalogo_api.domain.port.ProdutoRepositoryPort;
import com.br.controle_catalogo_api.utils.LogConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoRepositoryImpl.class);
    private final ProdutoSpringRepository produtoSpringRepository;

    public ProdutoRepositoryImpl(ProdutoSpringRepository produtoSpringRepository) {
        this.produtoSpringRepository = produtoSpringRepository;
    }

    @Override
    public boolean existsByProdutoId(String produtoId) {
        logger.debug(LogConstantUtils.LOG_VERIFICANDO_EXISTENCIA_PRODUTO, produtoId);
        return produtoSpringRepository.existsByProdutoId(produtoId);
    }

    @Override
    public ProdutoResponse insert(ProdutoEntity produtoEntity) {
        logger.debug(LogConstantUtils.LOG_ENTIDADE_MAPEADA_PRODUTO, produtoEntity);

        ProdutoEntity savedResponse = produtoSpringRepository.save(produtoEntity);
        logger.debug(LogConstantUtils.LOG_SUCESSO_PRODUTO, "persistido", savedResponse.getProdutoId());
        CategoriaDTO categoriaDTO = CategoriaMapper.toDTO(produtoEntity.getCategoria());

        return ProdutoMapper.toResponse(savedResponse, categoriaDTO);
    }

    @Override
    public List<ProdutoResponse> getAll() {
        logger.debug(LogConstantUtils.LOG_BUSCA_TODOS_PRODUTOS);

        List<ProdutoEntity> produtoEntities = produtoSpringRepository.findAll();

        List<ProdutoResponse> produtoResponses = produtoEntities.stream()
                .map(produtoEntity -> ProdutoMapper.toResponse(
                        produtoEntity,
                        CategoriaMapper.toDTO(produtoEntity.getCategoria())))
                .toList();

        logger.debug(LogConstantUtils.LOG_BUSCA_CONCLUIDA_PRODUTO, produtoResponses.size());

        return produtoResponses;
    }

    @Override
    public ProdutoEntity getById(String id) {
        logger.debug(LogConstantUtils.LOG_BUSCA_ID_PRODUTO, id);

        return produtoSpringRepository.findByProdutoId(id)
                .orElseThrow(() -> new RuntimeException(LogConstantUtils.LOG_NAO_ENCONTRADO_PRODUTO + id));
    }

    @Override
    public ProdutoResponse update(String id, AtualizaProdutoRequest produtoRequest, CategoriaEntity categoriaEntity) {
        logger.debug(LogConstantUtils.LOG_INICIANDO_PRODUTO, "atualização", id);

        ProdutoEntity produtoEntity = produtoSpringRepository.findByProdutoId(id)
                .orElseThrow(() -> new RuntimeException(LogConstantUtils.LOG_NAO_ENCONTRADO_PRODUTO + id));

        produtoEntity = ProdutoMapper.updateEntity(produtoEntity, produtoRequest, categoriaEntity);
        ProdutoEntity savedResponse = produtoSpringRepository.save(produtoEntity);

        logger.debug(LogConstantUtils.LOG_SUCESSO_PRODUTO, "atualizado", id);

        return ProdutoMapper.toResponse(savedResponse, CategoriaMapper.toDTO(categoriaEntity));
    }

    @Override
    public void delete(String id) {
        logger.debug(LogConstantUtils.LOG_INICIANDO_PRODUTO, "exclusão", id);

        ProdutoEntity produtoEntity = produtoSpringRepository.findByProdutoId(id)
                .orElseThrow(() -> new RuntimeException(LogConstantUtils.LOG_NAO_ENCONTRADO_PRODUTO + id));

        produtoSpringRepository.delete(produtoEntity);
        logger.debug(LogConstantUtils.LOG_SUCESSO_PRODUTO, "excluído", id);
    }
}
