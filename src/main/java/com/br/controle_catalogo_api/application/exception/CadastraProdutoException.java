package com.br.controle_catalogo_api.application.exception;

public class CadastraProdutoException extends RuntimeException {
    public CadastraProdutoException(String message, Exception e) {
        super(message, e);
    }
}
