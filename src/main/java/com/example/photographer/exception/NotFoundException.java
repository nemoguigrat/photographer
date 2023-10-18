package com.example.photographer.exception;

public class NotFoundException extends RuntimeException {

    private static final String TEMPLATE = "Can not identify object by id: %s";

    public NotFoundException(final Object id) {
        super(String.format(TEMPLATE, id));
    }

}