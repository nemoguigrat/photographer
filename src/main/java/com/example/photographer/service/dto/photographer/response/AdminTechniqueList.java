package com.example.photographer.service.dto.photographer.response;

import com.example.photographer.service.dto.technique.AbstractTechniqueDto;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Value
public class AdminTechniqueList {

    List<AbstractTechniqueDto> technique;

    Long count;

    public static AdminTechniqueList of(Page<AbstractTechniqueDto> technique) {
        return new AdminTechniqueList(technique.getContent(), technique.getTotalElements());
    }
}
