package com.example.photographer.api.advice;

import com.example.photographer.exception.NotSupportedMediaTypeException;
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
public class MediaAdvice {

    @ExceptionHandler({
            NotSupportedMediaTypeException.class})
    public ResponseEntity<ApiError> notSupported() {
        String message = "Не поддерживаемый формат изображения";

        return status(BAD_REQUEST)
                .body(ApiError.of("NOT_SUPPORTED", message));
    }
}
