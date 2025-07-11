package com.br.controle_catalogo_api.adapters.output;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.response.CategoriaResponse;
import com.br.controle_catalogo_api.application.exception.CategoriaNotFoundException;
import com.br.controle_catalogo_api.application.mapper.CategoriaMapper;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;
import com.br.controle_catalogo_api.domain.port.CategoriaRepositoryPort;
import com.br.controle_catalogo_api.utils.LogConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoriaRepositoryImpl implements CategoriaRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaRepositoryImpl.class);
    private final CategoriaSpringRepository categoriaSpringRepository;

    public CategoriaRepositoryImpl(CategoriaSpringRepository categoriaSpringRepository) {
        this.categoriaSpringRepository = categoriaSpringRepository;
    }

    @Override
    public boolean existsByCategoriaId(String categoriaId) {
        logger.debug(LogConstantUtils.LOG_VERIFICANDO_EXISTENCIA_CATEGORIA, categoriaId);
        return categoriaSpringRepository.existsByCategoriaId(categoriaId);
    }

    @Override
    public CategoriaResponse insert(CategoriaEntity categoriaEntity) {
        logger.debug(LogConstantUtils.LOG_ENTIDADE_MAPEADA_CATEGORIA, categoriaEntity);
        CategoriaEntity savedEntity = categoriaSpringRepository.save(categoriaEntity);
        logger.debug(LogConstantUtils.LOG_SUCESSO_CATEGORIA, "persistida", categoriaEntity.getCategoriaId());
        return CategoriaMapper.toResponse(savedEntity);
    }

    @Override
    public List<CategoriaResponse> getAll() {
        logger.debug(LogConstantUtils.LOG_BUSCA_TODAS_CATEGORIAS);

        List<CategoriaEntity> categoriaEntities = categoriaSpringRepository.findAll();

        List<CategoriaResponse> categoriaResponses = categoriaEntities.stream()
                .map(CategoriaMapper::toResponse)
                .toList();

        logger.debug(LogConstantUtils.LOG_BUSCA_CONCLUIDA_CATEGORIA, categoriaResponses.size());

        return categoriaResponses;
    }

    @Override
    public CategoriaEntity getById(String id) {
        logger.debug(LogConstantUtils.LOG_BUSCA_ID_CATEGORIA, id);

        return categoriaSpringRepository.findByCategoriaId(id)
                .orElseThrow(() -> new CategoriaNotFoundException(LogConstantUtils.LOG_NAO_ENCONTRADA_CATEGORIA + id));
    }

    @Override
    public CategoriaResponse update(String id, AtualizaCategoriaRequest categoriaRequest) {
        logger.debug(LogConstantUtils.LOG_INICIANDO_CATEGORIA, "atualização", id);

        CategoriaEntity categoriaEntity = categoriaSpringRepository.findByCategoriaId(id)
                .orElseThrow(() -> new CategoriaNotFoundException(LogConstantUtils.LOG_NAO_ENCONTRADA_CATEGORIA + id));

        categoriaEntity = CategoriaMapper.updateEntity(categoriaEntity, categoriaRequest);
        CategoriaEntity savedEntity = categoriaSpringRepository.save(categoriaEntity);

        logger.debug(LogConstantUtils.LOG_SUCESSO_CATEGORIA, "atualizada", id);

        return CategoriaMapper.toResponse(savedEntity);
    }

    @Override
    public void delete(String id) {
        logger.debug(LogConstantUtils.LOG_INICIANDO_CATEGORIA, "exclusão", id);

        CategoriaEntity categoriaEntity = categoriaSpringRepository.findByCategoriaId(id)
                .orElseThrow(() -> new CategoriaNotFoundException(LogConstantUtils.LOG_NAO_ENCONTRADA_CATEGORIA + id));

        categoriaSpringRepository.delete(categoriaEntity);

        logger.debug(LogConstantUtils.LOG_SUCESSO_CATEGORIA, "excluída", id);
    }
}
