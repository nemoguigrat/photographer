package com.example.photographer.support.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ErrorWrapperEntry {

    @Schema(description = "Список ошибок", requiredMode = Schema.RequiredMode.REQUIRED)
    @Singular
    List<Error> errors;

    public static ErrorWrapperEntry of(@NonNull String code, @NonNull String message) {

        return ErrorWrapperEntry.builder()
                .error(Error.builder()
                        .code(code)
                        .message(message)
                        .build())
                .build();
    }
}

