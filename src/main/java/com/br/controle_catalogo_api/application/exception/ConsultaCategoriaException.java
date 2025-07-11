package com.br.controle_catalogo_api.application.exception;

public class ConsultaCategoriaException extends RuntimeException {
    public ConsultaCategoriaException(String message, Exception e) {
        super(message, e);
    }
}
