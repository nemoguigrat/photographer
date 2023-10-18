package com.example.photographer.service;

import com.example.photographer.repository.BaseTechniqueRepository;
import com.example.photographer.support.TechniqueType;
import com.example.photographer.support.domain.AbstractTechnique;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TechniqueResolverService {

    Map<TechniqueType, TechniqueService<? extends AbstractTechnique>> services;

    public TechniqueResolverService(Map<TechniqueType, TechniqueService<? extends AbstractTechnique>> services) {
        if (services == null) {
            services = new HashMap<>();
        }
        this.services = services;
    }

    public TechniqueService<? extends AbstractTechnique> getServiceByType(TechniqueType type) {
        return services.get(type);
    }

    public Collection<TechniqueService<? extends AbstractTechnique>> getServices() {
        return services.values();
    }
}
