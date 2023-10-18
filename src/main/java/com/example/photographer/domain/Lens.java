package com.example.photographer.domain;

import com.example.photographer.service.dto.technique.response.LensDto;
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
public class Lens extends AbstractTechnique implements Evaluated {

    @ManyToOne
    @JoinColumn(name = "camera_id")
    Camera camera;

    @Column
    Integer focus;

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
    public LensDto buildDto() {
        return LensDto.builder()
                .id(this.getId())
                .model(this.getModel() != null ? this.getModel().buildDto() : null)
                .manufacturer(this.getManufacturer() != null ? this.getManufacturer().buildDto() : null)
                .camera(this.getCamera() != null ? this.getCamera().buildDto() : null)
                .focus(this.getFocus())
                .rating(this.getRating())
                .build();
    }
}
