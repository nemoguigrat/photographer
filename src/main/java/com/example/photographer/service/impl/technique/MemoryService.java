package com.example.photographer.service.impl.technique;

import com.example.photographer.domain.Memory;
import com.example.photographer.domain.TechniqueInfo;
import com.example.photographer.repository.ManufacturerRepository;
import com.example.photographer.repository.ModelRepository;
import com.example.photographer.repository.technique.MemoryRepository;
import com.example.photographer.service.dto.technique.AbstractTechniqueRequest;
import com.example.photographer.service.dto.technique.request.BatteryRequest;
import com.example.photographer.service.dto.technique.request.MemoryRequest;
import com.example.photographer.service.dto.technique.request.TechniqueRequest;
import com.example.photographer.service.impl.AbstractTechniqueService;
import com.example.photographer.support.TechniqueType;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MemoryService extends AbstractTechniqueService<Memory> {
    public MemoryService(MemoryRepository repository,
                         ManufacturerRepository manufacturerRepository,
                         ModelRepository modelRepository) {
        super(repository, manufacturerRepository, modelRepository);
    }

    @Override
    @Transactional
    public void updateTechniqueInfo(TechniqueInfo techniqueInfo, TechniqueRequest techniqueRequest) {
        List<MemoryRequest> requests = techniqueRequest.getTechnique().stream()
                .filter(t -> t.getType() == TechniqueType.MEMORY)
                .map(t -> (MemoryRequest) t)
                .collect(Collectors.toList());

        Set<Memory> domainCollection = techniqueInfo.getMemories();
        Set<Memory> updateSet = new HashSet<>();

        for (MemoryRequest request : requests) {
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

    @Override
    protected void applyFromRequest(Memory domain, AbstractTechniqueRequest request) {

    }
}
