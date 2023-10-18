package com.example.photographer.repository;

import com.example.photographer.domain.Model;
import com.example.photographer.support.TechniqueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findAllByType(TechniqueType type);

}
