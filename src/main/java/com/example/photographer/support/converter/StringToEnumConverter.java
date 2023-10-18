package com.example.photographer.support.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
public class StringToEnumConverter<T extends Enum<T>> implements Converter<String, T> {

    private final Class<T> type;

    @Override
    public T convert(@NotNull String source) {
        return T.valueOf(type, source.toUpperCase());
    }

}

