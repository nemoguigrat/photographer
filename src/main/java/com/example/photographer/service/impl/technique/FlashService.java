package com.example.photographer.service.impl.technique;

import com.example.photographer.domain.Flash;
import com.example.photographer.domain.TechniqueInfo;
import com.example.photographer.repository.ManufacturerRepository;
import com.example.photographer.repository.ModelRepository;
import com.example.photographer.repository.technique.FlashRepository;
import com.example.photographer.service.dto.technique.AbstractTechniqueRequest;
import com.example.photographer.service.dto.technique.request.BatteryRequest;
import com.example.photographer.service.dto.technique.request.FlashRequest;
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
public class FlashService extends AbstractTechniqueService<Flash> {
    public FlashService(FlashRepository repository,
                        ManufacturerRepository manufacturerRepository,
                        ModelRepository modelRepository) {
        super(repository, manufacturerRepository, modelRepository);
    }

    @Override
    @Transactional
    public void updateTechniqueInfo(TechniqueInfo techniqueInfo, TechniqueRequest techniqueRequest) {
        List<FlashRequest> requests = techniqueRequest.getTechnique().stream()
                .filter(t -> t.getType() == TechniqueType.FLASH)
                .map(t -> (FlashRequest) t)
                .collect(Collectors.toList());

        Set<Flash> updateSet = new HashSet<>();
        Set<Flash> domainCollection = techniqueInfo.getFlashes();

        for (FlashRequest request : requests) {
            Flash toUpdate = domainCollection.stream().filter(b -> b.getId().equals(request.getId()))
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

    @Override
    protected void applyFromRequest(Flash domain, AbstractTechniqueRequest request) {}
}
