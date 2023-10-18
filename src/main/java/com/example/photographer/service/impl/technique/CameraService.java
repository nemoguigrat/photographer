package com.example.photographer.service.impl.technique;

import com.example.photographer.domain.Camera;
import com.example.photographer.domain.Memory;
import com.example.photographer.domain.TechniqueInfo;
import com.example.photographer.repository.ManufacturerRepository;
import com.example.photographer.repository.ModelRepository;
import com.example.photographer.repository.technique.CameraRepository;
import com.example.photographer.service.dto.technique.request.CameraRequest;
import com.example.photographer.service.dto.technique.request.TechniqueRequest;
import com.example.photographer.service.impl.AbstractTechniqueService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CameraService extends AbstractTechniqueService<Camera> {
    public CameraService(CameraRepository repository,
                         ManufacturerRepository manufacturerRepository,
                         ModelRepository modelRepository) {
        super(repository, manufacturerRepository, modelRepository);
    }

    @Override
    public void updateTechniqueInfo(TechniqueInfo techniqueInfo, TechniqueRequest techniqueRequest) {
        if (techniqueRequest.getCameras() == null) {
            return;
        }

        Set<Camera> domainCollection = techniqueInfo.getCameras();
        Set<Camera> updateSet = new HashSet<>();

        for (CameraRequest cameraRequest : techniqueRequest.getCameras()) {
            Camera toUpdate = techniqueInfo.getCameras().stream().filter(b -> b.getId().equals(cameraRequest.getId()))
                    .findFirst().orElseGet(() -> {
                        Camera camera = new Camera();
                        camera.setTechniqueInfo(techniqueInfo);
                        return camera;
                    });

            applyModelAndManufacturer(toUpdate, cameraRequest);
            toUpdate.setCrop(cameraRequest.getCrop());
            updateSet.add(toUpdate);
        }

        domainCollection.clear();
        domainCollection.addAll(updateSet);
    }
}
