package com.example.photographer.repository;

import com.example.photographer.domain.Event;
import com.example.photographer.domain.Location;
import com.example.photographer.domain.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long>, JpaSpecificationExecutor<Zone> {

    @Query(value = "select distinct z from Zone z",
            countQuery = "select count(z.id) from Zone z")
    Page<Zone> findZoneWithFilter(Pageable pageable);

    @Query(value = "select distinct p from Zone p where p.event.id = :eventId",
            countQuery = "select count(p.id) from Zone p where p.event.id = :eventId")
    Page<Zone> findByEventId(Long eventId, Pageable pageable);

    @Query("select z from Zone z left join fetch z.event where z.id = :zoneId")
    Optional<Zone> findAndFetchEvent(Long zoneId);

    @Query("select count(z.id) > 0 from Zone z where z.locations.size > 0 or z.photographerZones.size > 0 or z.activities.size > 0")
    boolean existsLinkedData(Long id);
}
