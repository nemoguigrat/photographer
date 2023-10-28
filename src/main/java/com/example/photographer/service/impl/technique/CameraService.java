package com.example.photographer.service.impl.technique;

import com.example.photographer.domain.Camera;
import com.example.photographer.domain.TechniqueInfo;
import com.example.photographer.repository.ManufacturerRepository;
import com.example.photographer.repository.ModelRepository;
import com.example.photographer.repository.technique.CameraRepository;
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
public class CameraService extends AbstractTechniqueService<Camera> {
    public CameraService(CameraRepository repository,
                         ManufacturerRepository manufacturerRepository,
                         ModelRepository modelRepository) {
        super(repository, manufacturerRepository, modelRepository);
    }

    @Override
    @Transactional
    public void updateTechniqueInfo(TechniqueInfo techniqueInfo, TechniqueRequest techniqueRequest) {
        List<CameraRequest> requests = techniqueRequest.getTechnique().stream()
                .filter(t -> t.getType() == TechniqueType.CAMERA)
                .map(t -> (CameraRequest) t)
                .collect(Collectors.toList());

        Set<Camera> domainCollection = techniqueInfo.getCameras();
        Set<Camera> updateSet = new HashSet<>();

        for (CameraRequest cameraRequest : requests) {
            Camera toUpdate = domainCollection.stream().filter(b -> b.getId().equals(cameraRequest.getId()))
                    .findFirst().orElseGet(() -> {
                        Camera camera = new Camera();
                        camera.setTechniqueInfo(techniqueInfo);
                        return camera;
                    });

            applyModelAndManufacturer(toUpdate, cameraRequest);
            applyFromRequest(toUpdate, cameraRequest);

            updateSet.add(toUpdate);
        }

        domainCollection.clear();
        domainCollection.addAll(updateSet);
    }

    @Override
    protected void applyFromRequest(Camera domain, AbstractTechniqueRequest request) {
        if (request instanceof CameraRequest) {
            CameraRequest cameraRequest = (CameraRequest) request;
            domain.setCrop(cameraRequest.getCrop());
        }
    }
}
