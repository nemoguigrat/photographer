package com.example.photographer.domain;

import com.example.photographer.service.dto.technique.response.MemoryDto;
import com.example.photographer.support.domain.AbstractTechnique;
import com.example.photographer.support.domain.Evaluated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
public class Memory extends AbstractTechnique implements Evaluated {

    @Column(nullable = false)
    int size;

    @Column
    Integer rating;

    @Override
    public Integer getRating() {
        return rating;
    }

    @Override
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public MemoryDto buildDto() {
        return MemoryDto.builder()
                .id(this.getId())
                .model(this.getModel() != null ? this.getModel().buildDto() : null)
                .manufacturer(this.getManufacturer() != null ? this.getManufacturer().buildDto() : null)
                .size(this.getSize())
                .rating(this.getRating())
                .build();
    }
}
