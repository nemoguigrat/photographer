package com.example.photographer.repository;

import com.example.photographer.domain.Activity;
import com.example.photographer.domain.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {

    @Modifying
    @Query("delete from Activity a where a.id in :ids")
    void deleteByList(List<Long> ids);

    @Query(value = "select distinct p from Activity p where p.event.id = :eventId",
            countQuery = "select count(p.id) from Activity p where p.event.id = :eventId")
    Page<Activity> findByEventId(Long eventId, Pageable pageable);

    @Query("select distinct e.id from Activity a left join a.event e where a.lastUpdateTime >= :delay")
    List<Long> findLastModified(LocalDateTime delay);

    @Query(value = "select distinct a from Activity a left join fetch a.zone z left join fetch a.scheduleParts p " +
            "where a.startTime <= :time and a.endTime >= :time and (:zone is null or z = :zone)",
            countQuery = "select count(a.id) from Activity a where a.startTime <= :time and a.endTime >= :time and (:zone is null or a.zone = :zone)")
    Page<Activity> findFreeActivity(LocalDateTime time, Zone zone, Pageable pageable);
}
