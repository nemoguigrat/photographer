package com.example.photographer.api.advice;

import com.example.photographer.support.exception.ApiError;
import com.example.photographer.support.exception.ErrorList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@Slf4j
public class DefaultAdvice {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> defaultHandler(Throwable e) {

        log.warn("Something wrong", e);

        return status(INTERNAL_SERVER_ERROR)
                .body(ApiError.of("INTERNAL_SERVER_ERROR", INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }
}
