package com.br.controle_catalogo_api.application.exception;

public class CategoriaExistenteException extends RuntimeException {
    public CategoriaExistenteException(String message) {
        super(message);
    }
}
