package com.br.controle_catalogo_api.application.exception;

public class DeleteCategoriaException extends RuntimeException {
    public DeleteCategoriaException(String message, Exception e) {
        super(message, e);
    }
}
