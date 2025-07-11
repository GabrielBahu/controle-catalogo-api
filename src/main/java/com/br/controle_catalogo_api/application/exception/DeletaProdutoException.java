package com.br.controle_catalogo_api.application.exception;

public class DeletaProdutoException extends RuntimeException {
    public DeletaProdutoException(String message, Exception e) {
        super(message, e);
    }
}
