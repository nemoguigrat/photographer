package com.example.photographer.api.advice;

import com.example.photographer.exception.NotFoundException;
import com.example.photographer.exception.UserAlreadyExists;
import com.example.photographer.support.exception.ErrorWrapperEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RegistrationAdvice {

    @ExceptionHandler({
            UserAlreadyExists.class
    })
    public ResponseEntity<ErrorWrapperEntry> registerFailed() {

        return status(CONFLICT)
                .body(ErrorWrapperEntry.of("REGISTER_FAILED", CONFLICT.getReasonPhrase()));
    }
}
