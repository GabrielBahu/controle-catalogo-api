package com.br.controle_catalogo_api.application.service;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.request.CadastraCategoriaRequest;
import com.br.controle_catalogo_api.adapters.input.response.CategoriaResponse;
import com.br.controle_catalogo_api.application.exception.*;
import com.br.controle_catalogo_api.application.mapper.CategoriaMapper;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;
import com.br.controle_catalogo_api.domain.port.CategoriaRepositoryPort;
import com.br.controle_catalogo_api.domain.port.CategoriaServicePort;
import com.br.controle_catalogo_api.utils.LogConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaServicePort {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaServiceImpl.class);
    private final CategoriaRepositoryPort categoriaRepositoryPort;

    public CategoriaServiceImpl(CategoriaRepositoryPort categoriaRepositoryPort) {
        this.categoriaRepositoryPort = categoriaRepositoryPort;
    }

    @Override
    @Transactional
    public CategoriaResponse insert(CadastraCategoriaRequest categoriaRequest) {
        try {
            logger.info(LogConstantUtils.LOG_INICIANDO_CATEGORIA, "insercao", categoriaRequest.getCategoriaId());

            if (categoriaRepositoryPort.existsByCategoriaId(categoriaRequest.getCategoriaId())) {
                logger.error(LogConstantUtils.LOG_CATEGORIA_EXISTENTE, categoriaRequest.getCategoriaId());
                throw new CategoriaExistenteException("Tentativa de insercao de categoria com ID ja existente: " + categoriaRequest.getCategoriaId());
            }

            CategoriaEntity categoriaEntity = CategoriaMapper.toEntity(categoriaRequest);
            logger.debug(LogConstantUtils.LOG_ENTIDADE_MAPEADA_CATEGORIA, categoriaEntity);

            CategoriaResponse response = categoriaRepositoryPort.insert(categoriaEntity);
            logger.info(LogConstantUtils.LOG_SUCESSO_CATEGORIA, "inserida", categoriaEntity.getCategoriaId());

            return response;
        } catch (CategoriaExistenteException e) {
            logger.error("Erro ao inserir categoria: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error(LogConstantUtils.LOG_ERRO_CATEGORIA, "inserir", e.getMessage(), e);
            throw new CadastraCategoriaException("Erro ao inserir categoria: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CategoriaResponse> getAll() {
        try {
            logger.info(LogConstantUtils.LOG_BUSCA_TODAS_CATEGORIAS);

            List<CategoriaResponse> categorias = categoriaRepositoryPort.getAll();

            logger.info(LogConstantUtils.LOG_BUSCA_CONCLUIDA_CATEGORIA, categorias.size());
            return categorias;

        } catch (Exception e) {
            logger.error(LogConstantUtils.LOG_ERRO_CATEGORIA, "buscar", e.getMessage(), e);
            throw new ConsultaCategoriaException("Erro ao buscar categorias: " + e.getMessage(), e);
        }
    }

    @Override
    public CategoriaEntity getById(String id) {
        try {
            logger.info(LogConstantUtils.LOG_INICIANDO_CATEGORIA, "consulta", id);

            CategoriaEntity categoriaEntity = categoriaRepositoryPort.getById(id);

            if (categoriaEntity == null) {
                logger.error(LogConstantUtils.LOG_NAO_ENCONTRADA_CATEGORIA, id);
                throw new CategoriaNotFoundException("Categoria nao encontrada para ID: " + id);
            }

            logger.info(LogConstantUtils.LOG_SUCESSO_CATEGORIA, "encontrada", id);
            return categoriaEntity;

        } catch (CategoriaNotFoundException e) {
            logger.error(LogConstantUtils.LOG_NAO_ENCONTRADA_CATEGORIA, id);
            throw e;
        } catch (Exception e) {
            logger.error(LogConstantUtils.LOG_ERRO_CATEGORIA, "buscar ", e.getMessage(), e);
            throw new ConsultaCategoriaException("Erro ao buscar categoria por ID: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public CategoriaResponse update(String id, AtualizaCategoriaRequest categoriaRequest) {
        try {
            logger.info(LogConstantUtils.LOG_INICIANDO_CATEGORIA, "atualizacao", id);

            CategoriaResponse response = categoriaRepositoryPort.update(id, categoriaRequest);

            logger.info(LogConstantUtils.LOG_SUCESSO_CATEGORIA, "atualizada", id);
            return response;
        } catch (CategoriaNotFoundException e) {
            logger.error(LogConstantUtils.LOG_NAO_ENCONTRADA_CATEGORIA, id);
            throw e;
        } catch (Exception e) {
            logger.error(LogConstantUtils.LOG_ERRO_CATEGORIA, "atualizar", e.getMessage(), e);
            throw new AtualizaCategoriaException("Erro ao atualizar categoria: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        try {
            logger.info(LogConstantUtils.LOG_INICIANDO_CATEGORIA, "exclusao", id);

            if (!categoriaRepositoryPort.existsByCategoriaId(id)) {
                logger.error(LogConstantUtils.LOG_NAO_ENCONTRADA_CATEGORIA, id);
                throw new CategoriaNotFoundException("Categoria nao encontrada para o id: " + id);
            }

            categoriaRepositoryPort.delete(id);

            logger.info(LogConstantUtils.LOG_SUCESSO_CATEGORIA, "excluida", id);
        } catch (CategoriaNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error(LogConstantUtils.LOG_ERRO_CATEGORIA, "excluir", e.getMessage(), e);
            throw new DeleteCategoriaException("Erro ao excluir categoria: " + e.getMessage(), e);
        }
    }
}
