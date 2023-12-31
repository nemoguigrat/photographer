package com.example.photographer.service.impl.technique;

import com.example.photographer.domain.Battery;
import com.example.photographer.domain.Camera;
import com.example.photographer.domain.Memory;
import com.example.photographer.domain.TechniqueInfo;
import com.example.photographer.repository.ManufacturerRepository;
import com.example.photographer.repository.ModelRepository;
import com.example.photographer.repository.technique.BatteryRepository;
import com.example.photographer.service.dto.technique.AbstractTechniqueRequest;
import com.example.photographer.service.dto.technique.request.BatteryRequest;
import com.example.photographer.service.dto.technique.request.CameraRequest;
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
public class BatteryService extends AbstractTechniqueService<Battery> {
    public BatteryService(BatteryRepository repository,
                          ManufacturerRepository manufacturerRepository,
                          ModelRepository modelRepository) {
        super(repository, manufacturerRepository, modelRepository);
    }

    @Override
    @Transactional
    public void updateTechniqueInfo(TechniqueInfo techniqueInfo, TechniqueRequest techniqueRequest) {
        List<BatteryRequest> requests = techniqueRequest.getTechnique().stream()
                .filter(t -> t.getType() == TechniqueType.BATTERY)
                .map(t -> (BatteryRequest) t)
                .collect(Collectors.toList());

        Set<Battery> updateSet = new HashSet<>();
        Set<Battery> domainCollection = techniqueInfo.getBatteries();

        for (BatteryRequest batteryRequest : requests) {
            Battery toUpdate = domainCollection.stream().filter(b -> b.getId().equals(batteryRequest.getId()))
                    .findFirst().orElseGet(() -> {
                        Battery battery = new Battery();
                        battery.setTechniqueInfo(techniqueInfo);
                        return battery;
                    });

            applyModelAndManufacturer(toUpdate, batteryRequest);
            updateSet.add(toUpdate);
        }

        domainCollection.clear();
        domainCollection.addAll(updateSet);
    }

    @Override
    protected void applyFromRequest(Battery domain, AbstractTechniqueRequest request) { }
}
