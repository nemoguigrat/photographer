package com.example.photographer.api.advice;

import com.example.photographer.support.exception.ApiError;
import com.example.photographer.support.exception.ErrorList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@Slf4j
public class DefaultAdvice {

    //dev only
    @Value("${default.advice.debug:false}")
    private Boolean debugMessage;

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return status(METHOD_NOT_ALLOWED)
                .body(ApiError.of("METHOD_NOT_ALLOWED", ex.getMessage()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> defaultHandler(Throwable e) {

        log.warn("Something wrong", e);

        String message = debugMessage ? e.getMessage() : INTERNAL_SERVER_ERROR.getReasonPhrase();
        return status(INTERNAL_SERVER_ERROR)
                .body(ApiError.of("INTERNAL_SERVER_ERROR", message));
    }
}
