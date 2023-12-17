package com.example.photographer.repository;

import com.example.photographer.domain.PhotographerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotographerScheduleRepository extends JpaRepository<PhotographerSchedule, Long> {

    @Query("select distinct p from PhotographerSchedule p where p.photographer.id = :id and p.event.id = :eventId")
    Optional<PhotographerSchedule> findByPhotographerId(Long id, Long eventId);

    @Query("select count(p) > 0 from PhotographerSchedule p where p.photographer.id = :id and p.event.id = :eventId")
    boolean existsByPhotographerAndEvent(Long id, Long eventId);
}
