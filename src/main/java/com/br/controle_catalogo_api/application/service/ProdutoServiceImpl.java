package com.br.controle_catalogo_api.application.service;

import com.br.controle_catalogo_api.adapters.input.request.AtualizaProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.request.CadastraProdutoRequest;
import com.br.controle_catalogo_api.adapters.input.response.ProdutoResponse;
import com.br.controle_catalogo_api.application.exception.*;
import com.br.controle_catalogo_api.application.mapper.ProdutoMapper;
import com.br.controle_catalogo_api.domain.dto.MensagemDTO;
import com.br.controle_catalogo_api.domain.model.CategoriaEntity;
import com.br.controle_catalogo_api.domain.model.ProdutoEntity;
import com.br.controle_catalogo_api.domain.port.CategoriaServicePort;
import com.br.controle_catalogo_api.domain.port.ProdutoRepositoryPort;
import com.br.controle_catalogo_api.domain.port.ProdutoServicePort;
import com.br.controle_catalogo_api.domain.port.out.AwsSnsServicePort;
import com.br.controle_catalogo_api.utils.LogConstantUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoServicePort {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoServiceImpl.class);
    private final ProdutoRepositoryPort produtoRepositoryPort;
    private final CategoriaServicePort categoriaServicePort;
    private final AwsSnsServicePort awsSnsServicePort;

    public ProdutoServiceImpl(ProdutoRepositoryPort produtoRepositoryPort, CategoriaServicePort categoriaServicePort, AwsSnsServicePort awsSnsServicePort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
        this.categoriaServicePort = categoriaServicePort;
        this.awsSnsServicePort = awsSnsServicePort;
    }


    @Override
    public ProdutoResponse insert(CadastraProdutoRequest produtoRequest) {
        try {
            LOGGER.info(LogConstantUtils.LOG_INICIANDO_PRODUTO, "insercao", produtoRequest.getProdutoId());

            CategoriaEntity categoriaEntity = categoriaServicePort.getById(produtoRequest.getCategoriaId());

            if (produtoRepositoryPort.existsByProdutoId(produtoRequest.getProdutoId())) {
                LOGGER.error(LogConstantUtils.LOG_PRODUTO_EXISTENTE, produtoRequest.getProdutoId());
                throw new ProdutoExistenteException("Tentativa de insercao de produto com ID ja existente: " + produtoRequest.getProdutoId());
            }

            ProdutoEntity produtoEntity = ProdutoMapper.toEntity(produtoRequest, categoriaEntity);
            LOGGER.debug(LogConstantUtils.LOG_ENTIDADE_MAPEADA_PRODUTO, produtoEntity);

            ProdutoResponse response = produtoRepositoryPort.insert(produtoEntity);
            LOGGER.info(LogConstantUtils.LOG_SUCESSO_PRODUTO, "inserido", produtoEntity.getProdutoId());

            awsSnsServicePort.publicarMensagem(new MensagemDTO(response.getProdutoId()));

            return response;
        } catch (CategoriaNotFoundException | ProdutoExistenteException e) {
            LOGGER.error(LogConstantUtils.LOG_ERRO_PRODUTO, "inserir", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error(LogConstantUtils.LOG_ERRO_PRODUTO, "inserir", e.getMessage(), e);
            throw new CadastraProdutoException("Erro ao inserir produto: " + e.getMessage(), e);
        }
    }


    @Override
    public List<ProdutoResponse> getAll() {
        try {
            LOGGER.info(LogConstantUtils.LOG_BUSCA_TODOS_PRODUTOS);

            List<ProdutoResponse> produtoResponses = produtoRepositoryPort.getAll();

            LOGGER.info(LogConstantUtils.LOG_BUSCA_CONCLUIDA_PRODUTO, produtoResponses.size());
            return produtoResponses;

        } catch (Exception e) {
            LOGGER.error(LogConstantUtils.LOG_ERRO_PRODUTO, "buscar todos", e.getMessage(), e);
            throw new RuntimeException("Erro ao buscar produtos: " + e.getMessage(), e);
        }
    }

    @Override
    public ProdutoResponse update(String id, AtualizaProdutoRequest produtoRequest) {
        try {
            LOGGER.info(LogConstantUtils.LOG_INICIANDO_PRODUTO, "atualizacao", id);

            if (!produtoRepositoryPort.existsByProdutoId(id)) {
                LOGGER.error(LogConstantUtils.LOG_NAO_ENCONTRADO_PRODUTO, id);
                throw new ProdutoNotFoundException("Produto não encontrado para o id: " + id);
            }

            CategoriaEntity categoriaEntity = null;
            if (produtoRequest.getCategoriaId() != null) {
                categoriaEntity = categoriaServicePort.getById(produtoRequest.getCategoriaId());
            } else {
                ProdutoEntity produtoAtual = produtoRepositoryPort.getById(id);
                categoriaEntity = produtoAtual.getCategoria();
            }

            ProdutoResponse response = produtoRepositoryPort.update(id, produtoRequest, categoriaEntity);

            LOGGER.info(LogConstantUtils.LOG_SUCESSO_PRODUTO, "atualizado", response.getProdutoId());

            awsSnsServicePort.publicarMensagem(new MensagemDTO(response.getProdutoId()));

            return response;
        } catch (ProdutoNotFoundException e) {
            LOGGER.error(LogConstantUtils.LOG_NAO_ENCONTRADO_PRODUTO, id);
            throw e;
        } catch (Exception e) {
            LOGGER.error(LogConstantUtils.LOG_ERRO_PRODUTO, "atualizar", e.getMessage(), e);
            throw new AtualizaProdutoException("Erro ao atualizar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String id) {
        try {
            LOGGER.info(LogConstantUtils.LOG_INICIANDO_PRODUTO, "exclusao", id);

            if (!produtoRepositoryPort.existsByProdutoId(id)) {
                LOGGER.error(LogConstantUtils.LOG_NAO_ENCONTRADO_PRODUTO, id);
                throw new ProdutoNotFoundException("Produto não encontrado para o id: " + id);
            }

            produtoRepositoryPort.delete(id);
            LOGGER.info(LogConstantUtils.LOG_SUCESSO_PRODUTO, "deletado", id);
        } catch (ProdutoNotFoundException e) {
            LOGGER.error(LogConstantUtils.LOG_NAO_ENCONTRADO_PRODUTO, id);
            throw e;
        } catch (Exception e) {
            LOGGER.error(LogConstantUtils.LOG_ERRO_PRODUTO, "deletar", e.getMessage(), e);
            throw new DeletaProdutoException("Erro ao deletar produto: " + e.getMessage(), e);
        }
    }
}
