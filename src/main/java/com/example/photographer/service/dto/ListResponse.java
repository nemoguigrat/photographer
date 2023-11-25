package com.example.photographer.service.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Value
public class ListResponse<T> {

    List<T> list;

    Long count;

    public static <T> ListResponse<T> of(Page<T> page) {
        return new ListResponse<>(page.getContent(), page.getTotalElements());
    }
}
