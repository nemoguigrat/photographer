package com.example.photographer.repository;

import com.example.photographer.domain.PhotographerSchedule;
import com.example.photographer.domain.PhotographerSchedulePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SchedulePartRepository extends JpaRepository<PhotographerSchedulePart, Long>, JpaSpecificationExecutor<PhotographerSchedulePart> {

    @Query("select distinct p from PhotographerSchedulePart p left join fetch p.photographerSchedule s left join fetch s.photographer where " +
            "(p.notifiedTime is null and p.startTime >= :now and p.startTime <= :firstNotifiedTime) " +
            "or (p.notifiedTime is not null and p.notifiedTime <= :firstNotifiedTime and " +
            "p.notifiedTime <= p.startTime and p.startTime >= :now and p.startTime <= :secondNotifiedTime) ")
    List<PhotographerSchedulePart> findUpcoming(LocalDateTime now, LocalDateTime firstNotifiedTime, LocalDateTime secondNotifiedTime);

    @Query("select distinct p from PhotographerSchedulePart p left join fetch p.activity a where p.photographerSchedule = :schedule")
    List<PhotographerSchedulePart> findParts(PhotographerSchedule schedule);
}
