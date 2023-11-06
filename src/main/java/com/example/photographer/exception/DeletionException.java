package com.example.photographer.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeletionException extends RuntimeException {

    public DeletionException(String message) {
        super(message);
    }
}
