package com.example.photographer.repository;

import com.example.photographer.domain.Photographer;
import com.example.photographer.exception.NotFoundException;
import com.example.photographer.service.dto.photographer.request.AdminPhotographerFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Long> {

    default Photographer findByIdWithUser(Long id) {
        return findByIdAndFetchUser(id).orElseThrow(() -> new NotFoundException(id));
    }

    Optional<Photographer> findByUser_Id(Long userId);

    @Query(value = "select distinct p from Photographer p left join fetch p.user",
            countQuery = "select count(p.id) from Photographer p")
    Page<Photographer> findPhotographersWithFilter(Pageable pageable);

    @Query("select distinct p from Photographer p left join fetch p.user where p.id = :id")
    Optional<Photographer> findByIdAndFetchUser(Long id);
}
