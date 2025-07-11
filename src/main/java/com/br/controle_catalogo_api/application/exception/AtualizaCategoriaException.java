package com.br.controle_catalogo_api.application.exception;

public class AtualizaCategoriaException extends RuntimeException {
    public AtualizaCategoriaException(String message, Exception e) {
        super(message, e);
    }
}
