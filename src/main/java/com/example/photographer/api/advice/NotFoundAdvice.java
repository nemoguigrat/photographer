package com.example.photographer.api.advice;

import com.example.photographer.exception.NotFoundException;
import com.example.photographer.support.exception.ErrorWrapperEntry;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class NotFoundAdvice {

    @ExceptionHandler({
            NotFoundException.class,
            EntityNotFoundException.class})
    public ResponseEntity<ErrorWrapperEntry> notFound() {

        return status(UNPROCESSABLE_ENTITY)
                .body(ErrorWrapperEntry.of("NOT_FOUND", NOT_FOUND.getReasonPhrase()));
    }

    @ExceptionHandler({
            UsernameNotFoundException.class,
    })
    public ResponseEntity<ErrorWrapperEntry> userNotFound() {

        return status(NOT_FOUND)
                .body(ErrorWrapperEntry.of("NOT_FOUND", NOT_FOUND.getReasonPhrase()));
    }
}

