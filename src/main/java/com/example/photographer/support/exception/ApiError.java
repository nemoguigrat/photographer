package com.example.photographer.support.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiError {

    @Schema(description = "Код ошибки", requiredMode = Schema.RequiredMode.REQUIRED)
    String code;

    @Schema(description = "Сообщение о ошибке", requiredMode = Schema.RequiredMode.REQUIRED)
    String message;

    @Schema(description = "Поле на котором возникла ошибка")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String field;

    public static ApiError of(String code, String message) {
        return ApiError.builder()
                .message(message)
                .code(code)
                .build();
    }
}

