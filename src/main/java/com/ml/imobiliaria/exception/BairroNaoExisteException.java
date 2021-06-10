package com.ml.imobiliaria.exception;

public class BairroNaoExisteException extends RuntimeException {
    private String message;
    public BairroNaoExisteException(String message) {
        super(message);
    }
}
