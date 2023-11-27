package com.example.photographer.repository;

import com.example.photographer.domain.PhotographerFreetime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotographerFreetimeRepository extends JpaRepository<PhotographerFreetime, Long>, JpaSpecificationExecutor<PhotographerFreetime> {

    @Query("select distinct pf from PhotographerFreetime pf where pf.photographerSchedule.photographer.id = :pId and pf.photographerSchedule.event.id = :eventId")
    List<PhotographerFreetime> findByPhotographerIdAndEventId(Long pId, Long eventId);
}
