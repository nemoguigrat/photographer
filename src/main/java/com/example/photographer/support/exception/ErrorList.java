package com.example.photographer.support.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ErrorList<T extends ApiError> {

    @Schema(description = "Список ошибок", requiredMode = Schema.RequiredMode.REQUIRED)
    @Singular
    List<T> errors;
}

