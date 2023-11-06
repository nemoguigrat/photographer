package com.example.photographer.api.advice;

import com.example.photographer.exception.FileEmptyException;
import com.example.photographer.exception.UserAlreadyExists;
import com.example.photographer.support.exception.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FileEmptyAdvice {

    @ExceptionHandler({
            FileEmptyException.class
    })
    public ResponseEntity<ApiError> handleFileEmpty(UserAlreadyExists ex) {
        String message = ex.getMessage() == null ? "Файл не может быть пустым или остутствовать" : ex.getMessage();

        return status(BAD_REQUEST)
                .body(ApiError.of("FILE_EMPTY", message));
    }
}
