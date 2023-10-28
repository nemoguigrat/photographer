package com.example.photographer.domain;

import com.example.photographer.service.dto.technique.response.CameraDto;
import com.example.photographer.support.TechniqueType;
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
public class Camera extends AbstractTechnique implements Evaluated {

    @Column
    String crop;

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
    public CameraDto buildDto() {
        return CameraDto.builder()
                .id(this.getId())
                .model(this.getModel() != null ? this.getModel().buildDto() : null)
                .manufacturer(this.getManufacturer() != null ? this.getManufacturer().buildDto() : null)
                .type(TechniqueType.CAMERA)
                .crop(this.getCrop())
                .rating(this.getRating())
                .build();
    }
}
