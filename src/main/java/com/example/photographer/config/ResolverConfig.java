package com.example.photographer.config;

import com.example.photographer.service.TechniqueResolverService;
import com.example.photographer.service.TechniqueService;
import com.example.photographer.support.TechniqueType;
import com.example.photographer.support.domain.AbstractTechnique;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
public class ResolverConfig {

    @Bean
    public TechniqueResolverService techniqueResolverService(List<TechniqueService<? extends AbstractTechnique>> services) {
        TechniqueType[] repositoryForClasses = TechniqueType.values();

        Map<TechniqueType, TechniqueService<? extends AbstractTechnique>> resolverMap = new HashMap<>();

        for (TechniqueType type : repositoryForClasses) {
            if (type.getTechniqueDomainClass() == null) {
                continue;
            }

            ResolvableType resolvableType = ResolvableType.forClassWithGenerics(TechniqueService.class, type.getTechniqueDomainClass());

            Optional<TechniqueService<? extends AbstractTechnique>> service = services.stream()
                    .filter(resolvableType::isInstance)
                    .findFirst();

            service.ifPresent(o ->
                    resolverMap.put(type, service.get())
            );
        }

        return new TechniqueResolverService(resolverMap);
    }
}
