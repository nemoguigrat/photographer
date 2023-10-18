package com.example.photographer.repository.technique;

import com.example.photographer.domain.Camera;
import com.example.photographer.domain.Flash;
import com.example.photographer.repository.BaseTechniqueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashRepository extends BaseTechniqueRepository<Flash> {
}
