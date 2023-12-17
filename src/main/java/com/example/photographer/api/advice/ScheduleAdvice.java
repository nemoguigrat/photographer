package com.example.photographer.api.advice;

import com.example.photographer.exception.ActivityConflictException;
import com.example.photographer.exception.ScheduleAlreadyExists;
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
public class ScheduleAdvice {

    @ExceptionHandler({
            ActivityConflictException.class
    })
    public ResponseEntity<ApiError> handleActivityConflict(ActivityConflictException ex) {
        String message = ex.getMessage() == null ? "Конфликт во времени между событиями" : ex.getMessage();

        return status(BAD_REQUEST)
                .body(ApiError.of("ACTIVITY_CONFLICT", message));
    }

    @ExceptionHandler({
            ScheduleAlreadyExists.class
    })
    public ResponseEntity<ApiError> handleSchedule(ScheduleAlreadyExists ex) {
        String message = ex.getMessage() == null ? "Заявка ожидает подтверждения" : ex.getMessage();

        return status(BAD_REQUEST)
                .body(ApiError.of("SCHEDULE_EXISTS", message));
    }
}
