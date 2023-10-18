package com.example.photographer.repository.technique;

import com.example.photographer.domain.Camera;
import com.example.photographer.domain.Lens;
import com.example.photographer.repository.BaseTechniqueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LensRepository extends BaseTechniqueRepository<Lens> {
}
