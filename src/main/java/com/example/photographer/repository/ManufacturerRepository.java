package com.example.photographer.repository;

import com.example.photographer.domain.Manufacturer;
import com.example.photographer.support.TechniqueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    List<Manufacturer> findAllByType(TechniqueType type);
}
