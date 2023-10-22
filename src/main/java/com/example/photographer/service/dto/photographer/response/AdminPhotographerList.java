package com.example.photographer.service.dto.photographer.response;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Value
public class AdminPhotographerList {

    List<AdminPhotographerResponse> photographers;

    Long count;

    public static AdminPhotographerList of(Page<AdminPhotographerResponse> page) {
        return new AdminPhotographerList(page.getContent(), page.getTotalElements());
    }
}
