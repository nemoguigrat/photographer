package com.example.photographer.repository;

import com.example.photographer.support.domain.AbstractTechnique;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;

@NoRepositoryBean
public interface BaseTechniqueRepository<T extends AbstractTechnique> extends JpaRepository<T, Long> {

    @Query(value = "select t from #{#entityName} t where (:techniqueInfoId is null or t.techniqueInfo.id = :techniqueInfoId)",
            countQuery = "select count(t) from #{#entityName} t where (:techniqueInfoId is null or t.techniqueInfo.id = :techniqueInfoId)")
    Page<T> findTechniqueWithModelAndManufacturer(Long techniqueInfoId, Pageable pageable);
}
