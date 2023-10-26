package com.example.photographer.api.advice;

import com.example.photographer.exception.UserAlreadyExists;
import com.example.photographer.support.exception.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RegistrationAdvice {

    @ExceptionHandler({
            UserAlreadyExists.class
    })
    public ResponseEntity<ApiError> registerFailed(UserAlreadyExists ex) {
        String message = ex.getMessage() == null ? "Ошибка при создании нового пользователя" : ex.getMessage();

        return status(CONFLICT)
                .body(ApiError.of("REGISTER_FAILED", message));
    }
}
