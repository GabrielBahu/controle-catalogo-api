package com.br.controle_catalogo_api.application.exception;

public class AtualizaProdutoException extends RuntimeException {
    public AtualizaProdutoException(String message, Exception e) {
        super(message, e);
    }
}
