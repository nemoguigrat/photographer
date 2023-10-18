package com.example.photographer.support;

import com.example.photographer.domain.*;
import com.example.photographer.support.domain.AbstractTechnique;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum TechniqueType {

    @JsonProperty("camera") CAMERA("camera", Camera.class),

    @JsonProperty("battery") BATTERY("battery", Battery.class),

    @JsonProperty("flash") FLASH("flash", Flash.class),

    @JsonProperty("lens") LENS("lens", Lens.class),

    @JsonProperty("memory") MEMORY("memory", Memory.class);

    private final String id;

    private final Class<? extends AbstractTechnique> domainClazz;

    TechniqueType(String id, Class<? extends AbstractTechnique> domainClazz) {
        this.id = id;
        this.domainClazz = domainClazz;
    }

    public Class<? extends AbstractTechnique> getTechniqueDomainClass() {
        return this.domainClazz;
    }
}
