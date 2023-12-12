package com.example.photographer.repository;

import com.example.photographer.domain.PhotographerZoneInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotographerZonePriorityRepository extends JpaRepository<PhotographerZoneInfo, Long>, JpaSpecificationExecutor<PhotographerZoneInfo> {

    @Query("select distinct pzi from PhotographerZoneInfo pzi where pzi.photographerSchedule.photographer.id = :pId and pzi.zone.event.id = :eventId")
    List<PhotographerZoneInfo> findByPhotographerIdAndEventId(Long pId, Long eventId);
}
