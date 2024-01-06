package com.example.photographer.repository;

import com.example.photographer.domain.Photographer;
import com.example.photographer.domain.TechniqueInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TechniqueInfoRepository extends JpaRepository<TechniqueInfo, Long> {

    @Query("select distinct t from TechniqueInfo t " +
            "left join fetch t.cameras " +
            "left join fetch t.flashes " +
            "left join fetch t.lenses " +
            "left join fetch t.memories " +
            "left join fetch t.batteries " +
            "left join fetch t.additionalTechniques where t.id = :id")
    TechniqueInfo findAndFetchTechnique(Long id);

    @Query("select distinct t from TechniqueInfo t " +
            "left join fetch t.cameras " +
            "left join fetch t.flashes " +
            "left join fetch t.lenses " +
            "left join fetch t.memories " +
            "left join fetch t.batteries " +
            "left join fetch t.additionalTechniques where t.photographer = :photographer")
    TechniqueInfo findAndFetchTechniqueByPhotographer(@Param("photographer") Photographer photographer);
}
