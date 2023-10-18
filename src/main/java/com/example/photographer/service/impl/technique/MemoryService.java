package com.example.photographer.service.impl.technique;

import com.example.photographer.domain.Battery;
import com.example.photographer.domain.Lens;
import com.example.photographer.domain.Memory;
import com.example.photographer.domain.TechniqueInfo;
import com.example.photographer.repository.ManufacturerRepository;
import com.example.photographer.repository.ModelRepository;
import com.example.photographer.repository.technique.MemoryRepository;
import com.example.photographer.service.dto.technique.request.BatteryRequest;
import com.example.photographer.service.dto.technique.request.LensRequest;
import com.example.photographer.service.dto.technique.request.MemoryRequest;
import com.example.photographer.service.dto.technique.request.TechniqueRequest;
import com.example.photographer.service.impl.AbstractTechniqueService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MemoryService extends AbstractTechniqueService<Memory> {
    public MemoryService(MemoryRepository repository,
                         ManufacturerRepository manufacturerRepository,
                         ModelRepository modelRepository) {
        super(repository, manufacturerRepository, modelRepository);
    }

    @Override
    public void updateTechniqueInfo(TechniqueInfo techniqueInfo, TechniqueRequest techniqueRequest) {
        if (techniqueRequest.getMemories() == null) {
            return;
        }

        Set<Memory> domainCollection = techniqueInfo.getMemories();
        Set<Memory> updateSet = new HashSet<>();

        for (MemoryRequest request : techniqueRequest.getMemories()) {
            Memory toUpdate = domainCollection.stream().filter(b -> b.getId().equals(request.getId()))
                    .findFirst().orElseGet(() -> {
                        Memory domain = new Memory();
                        domain.setTechniqueInfo(techniqueInfo);
                        return domain;
                    });

            applyModelAndManufacturer(toUpdate, request);
            updateSet.add(toUpdate);
        }

        domainCollection.clear();
        domainCollection.addAll(updateSet);
    }
}
