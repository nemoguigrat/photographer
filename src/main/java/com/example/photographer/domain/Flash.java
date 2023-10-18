package com.example.photographer.domain;

import com.example.photographer.service.dto.technique.response.FlashDto;
import com.example.photographer.support.domain.AbstractTechnique;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class Flash extends AbstractTechnique {

    @Override
    public FlashDto buildDto() {
        return FlashDto.builder()
                .id(this.getId())
                .model(this.getModel() != null ? this.getModel().buildDto() : null)
                .manufacturer(this.getManufacturer() != null ? this.getManufacturer().buildDto() : null)
                .build();
    }
}
