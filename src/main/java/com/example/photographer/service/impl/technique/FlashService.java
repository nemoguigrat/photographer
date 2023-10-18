package com.example.photographer.service.impl.technique;

import com.example.photographer.domain.Battery;
import com.example.photographer.domain.Flash;
import com.example.photographer.domain.Memory;
import com.example.photographer.domain.TechniqueInfo;
import com.example.photographer.repository.ManufacturerRepository;
import com.example.photographer.repository.ModelRepository;
import com.example.photographer.repository.technique.FlashRepository;
import com.example.photographer.service.dto.technique.request.BatteryRequest;
import com.example.photographer.service.dto.technique.request.FlashRequest;
import com.example.photographer.service.dto.technique.request.TechniqueRequest;
import com.example.photographer.service.impl.AbstractTechniqueService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FlashService extends AbstractTechniqueService<Flash> {
    public FlashService(FlashRepository repository,
                        ManufacturerRepository manufacturerRepository,
                        ModelRepository modelRepository) {
        super(repository, manufacturerRepository, modelRepository);
    }

    @Override
    public void updateTechniqueInfo(TechniqueInfo techniqueInfo, TechniqueRequest techniqueRequest) {
        if (techniqueRequest.getFlashes() == null) {
            return;
        }

        Set<Flash> updateSet = new HashSet<>();
        Set<Flash> domainCollection = techniqueInfo.getFlashes();

        for (FlashRequest request : techniqueRequest.getFlashes()) {
            Flash toUpdate = techniqueInfo.getFlashes().stream().filter(b -> b.getId().equals(request.getId()))
                    .findFirst().orElseGet(() -> {
                        Flash battery = new Flash();
                        battery.setTechniqueInfo(techniqueInfo);
                        return battery;
                    });

            applyModelAndManufacturer(toUpdate, request);
            updateSet.add(toUpdate);
        }

        domainCollection.clear();
        domainCollection.addAll(updateSet);
    }
}
