package com.example.photographer.repository;

import com.example.photographer.domain.PhotographerSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotographerScheduleRepository extends JpaRepository<PhotographerSchedule, Long>, JpaSpecificationExecutor<PhotographerSchedule> {

    @Query("select distinct p from PhotographerSchedule p where p.photographer.id = :id and p.event.id = :eventId")
    Optional<PhotographerSchedule> findByPhotographerId(Long id, Long eventId);

    @Query(value = "select distinct p from PhotographerSchedule p left join fetch p.event where p.photographer.id = :id",
        countQuery = "select count(p) from PhotographerSchedule p where p.photographer.id = :id")
    Page<PhotographerSchedule> findByPhotographerId(Long id, Pageable pageable);

    @Query("select count(p) > 0 from PhotographerSchedule p where p.photographer.id = :id and p.event.id = :eventId")
    boolean existsByPhotographerAndEvent(Long id, Long eventId);
}
