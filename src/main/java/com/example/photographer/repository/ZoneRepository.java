package com.example.photographer.repository;

import com.example.photographer.domain.Event;
import com.example.photographer.domain.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {

    @Query(value = "select distinct z from Zone z",
            countQuery = "select count(z.id) from Zone z")
    Page<Zone> findZoneWithFilter(Pageable pageable);

    List<Zone> findByEvent_Id(Long eventId);

    @Query("select count(z.id) > 0 from Zone z where z.locations.size > 0 or z.photographerZones.size > 0 or z.activities.size > 0")
    boolean existsLinkedData(Long id);
}
