package com.example.photographer.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserAlreadyExists extends RuntimeException {

    public UserAlreadyExists(String m) {
        super(m);
    }
}
