package com.example.photographer.repository;

import com.example.photographer.support.domain.AbstractTechnique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;

@NoRepositoryBean
public interface BaseTechniqueRepository<T extends AbstractTechnique> extends JpaRepository<T, Long> {

    @Query("select t from #{#entityName} t ")
    Collection<T> findTechniqueWithModelAndManufacturer();
}
