package com.example.photographer.support.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Error {

    @Schema(description = "Код ошибки", requiredMode = Schema.RequiredMode.REQUIRED)
    String code;

    @Schema(description = "Сообщение о ошибке", requiredMode = Schema.RequiredMode.REQUIRED)
    String message;

    @Schema(description = "Поле на котором возникла ошибка")
    String field;
}

