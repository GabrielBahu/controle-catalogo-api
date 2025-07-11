package com.br.controle_catalogo_api.application.exception;

public class ProdutoExistenteException extends RuntimeException {
    public ProdutoExistenteException(String message, ProdutoExistenteException e) {
        super(message, e);
    }

    public ProdutoExistenteException(String message) {
        super(message);
    }
}
