package com.br.controle_catalogo_api.application.exception;


public class CadastraCategoriaException extends RuntimeException {

    public CadastraCategoriaException(String message) {
        super(message);
    }

    public CadastraCategoriaException(String message, Exception cause) {
        super(message, cause);
    }
}