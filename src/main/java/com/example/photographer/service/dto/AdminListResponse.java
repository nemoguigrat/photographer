package com.example.photographer.service.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Value
public class AdminListResponse<T> {

    List<T> list;

    Long count;

    public static <T> AdminListResponse<T> of(Page<T> page) {
        return new AdminListResponse<>(page.getContent(), page.getTotalElements());
    }
}
