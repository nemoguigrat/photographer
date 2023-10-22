package com.example.photographer.service.impl.technique;

import com.example.photographer.domain.*;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.repository.ManufacturerRepository;
import com.example.photographer.repository.ModelRepository;
import com.example.photographer.repository.technique.CameraRepository;
import com.example.photographer.repository.technique.LensRepository;
import com.example.photographer.service.dto.technique.AbstractTechniqueRequest;
import com.example.photographer.service.dto.technique.request.LensRequest;
import com.example.photographer.service.dto.technique.request.TechniqueRequest;
import com.example.photographer.service.impl.AbstractTechniqueService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LensService extends AbstractTechniqueService<Lens> {

    CameraRepository cameraRepository;
    public LensService(LensRepository repository,
                       ManufacturerRepository manufacturerRepository,
                       ModelRepository modelRepository,
                       CameraRepository cameraRepository) {
        super(repository, manufacturerRepository, modelRepository);

        this.cameraRepository = cameraRepository;
    }

    @Override
    @Transactional
    public void updateTechniqueInfo(TechniqueInfo techniqueInfo, TechniqueRequest techniqueRequest) {
        if (techniqueRequest.getLenses() == null) {
            return;
        }

        Set<Lens> updateSet = new HashSet<>();
        Set<Lens> domainCollection = techniqueInfo.getLenses();

        for (LensRequest request : techniqueRequest.getLenses()) {
            Lens toUpdate = techniqueInfo.getLenses().stream().filter(b -> b.getId().equals(request.getId()))
                    .findFirst().orElseGet(() -> {
                        Lens domain = new Lens();
                        domain.setTechniqueInfo(techniqueInfo);
                        return domain;
                    });

            applyModelAndManufacturer(toUpdate, request);
            applyFromRequest(toUpdate, request);

            updateSet.add(toUpdate);
        }

        domainCollection.clear();
        domainCollection.addAll(updateSet);
    }

    @Override
    protected void applyFromRequest(Lens domain, AbstractTechniqueRequest request) {
        if (request instanceof LensRequest) {
            LensRequest lensRequest = (LensRequest) request;
            Camera camera = null;
            if (lensRequest.getCameraId() != null) {
                camera = cameraRepository.findById(lensRequest.getCameraId())
                        .orElseThrow(() -> new NotFoundException(lensRequest.getCameraId())); //todo заменить ошибку
            }
            domain.setCamera(camera);
            domain.setFocus(lensRequest.getFocus());
        }
    }
}
