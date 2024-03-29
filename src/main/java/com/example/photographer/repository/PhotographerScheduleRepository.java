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

    @Query("select distinct p from PhotographerSchedule p left join fetch p.zone z where p.id = :id")
    Optional<PhotographerSchedule> findByIdAndFetchZone(Long id);

    @Query(value = "select distinct p from PhotographerSchedule p left join fetch p.event where p.photographer.id = :id",
        countQuery = "select count(p) from PhotographerSchedule p where p.photographer.id = :id")
    Page<PhotographerSchedule> findByPhotographerId(Long id, Pageable pageable);

    @Query("select count(p) > 0 from PhotographerSchedule p where p.photographer.id = :id and p.event.id = :eventId")
    boolean existsByPhotographerAndEvent(Long id, Long eventId);

    @Query(value = "select distinct s from PhotographerSchedule s left join fetch s.photographer p left join fetch s.event e left join fetch s.zone z " +
            "where (:eventId is null or e.id = :eventId) and (:photographerId is null or p.id = :photographerId)",
    countQuery = "select count(s) from PhotographerSchedule s " +
            "where (:eventId is null or s.event.id = :eventId) and (:photographerId is null or s.photographer.id = :photographerId)")
    Page<PhotographerSchedule> findAllWithFilter(Long eventId, Long photographerId, Pageable pageable);
}
