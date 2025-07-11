package com.br.controle_catalogo_api.utils;

public class LogConstantUtils {

    // CATEGORIA
    // Mensagens gerais
    public static final String LOG_INICIANDO_CATEGORIA = "Iniciando {} de categoria com ID: {}";
    public static final String LOG_SUCESSO_CATEGORIA = "Categoria {} com sucesso. ID: {}";
    public static final String LOG_ERRO_CATEGORIA = "Erro inesperado ao {} categoria: {}";
    public static final String LOG_NAO_ENCONTRADA_CATEGORIA = "Categoria nao encontrada para ID: {}";

    // Mensagens específicas para busca
    public static final String LOG_BUSCA_TODAS_CATEGORIAS = "Iniciando busca de todas as categorias";
    public static final String LOG_BUSCA_CONCLUIDA_CATEGORIA = "Busca de categorias concluida com sucesso. Total encontrado: {}";
    public static final String LOG_VERIFICANDO_EXISTENCIA_CATEGORIA = "Verificando existencia da categoria com ID: {}";
    public static final String LOG_BUSCA_ID_CATEGORIA = "Iniciando busca de categoria por ID: {}";

    // Mensagens específicas para insercao
    public static final String LOG_CATEGORIA_EXISTENTE = "Tentativa de insercao de categoria com ID ja existente: {}";
    public static final String LOG_ENTIDADE_MAPEADA_CATEGORIA = "Entidade de categoria mapeada: {}";

    // PRODUTO
    // Mensagens gerais
    public static final String LOG_INICIANDO_PRODUTO = "Iniciando {} de produto com ID: {}";
    public static final String LOG_SUCESSO_PRODUTO = "Produto {} com sucesso. ID: {}";
    public static final String LOG_ERRO_PRODUTO = "Erro inesperado ao {} produto: {}";
    public static final String LOG_NAO_ENCONTRADO_PRODUTO = "Produto não encontrado para o ID: {}";

    // Mensagens específicas para busca
    public static final String LOG_BUSCA_TODOS_PRODUTOS = "Iniciando busca de todos os produtos";
    public static final String LOG_BUSCA_CONCLUIDA_PRODUTO = "Busca de produtos concluida com sucesso. Total encontrado: {}";
    public static final String LOG_VERIFICANDO_EXISTENCIA_PRODUTO = "Verificando existencia do produto com ID: {}";
    public static final String LOG_BUSCA_ID_PRODUTO = "Iniciando busca de produto por ID: {}";

    // Mensagens específicas para insercao
    public static final String LOG_PRODUTO_EXISTENTE = "Tentativa de insercao de produto com ID ja existente: {}";
    public static final String LOG_ENTIDADE_MAPEADA_PRODUTO = "Entidade de produto mapeada: {}";

    private LogConstantUtils() {
    }
}