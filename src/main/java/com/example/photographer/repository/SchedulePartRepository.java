package com.example.photographer.repository;

import com.example.photographer.domain.PhotographerSchedulePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulePartRepository extends JpaRepository<PhotographerSchedulePart, Long>, JpaSpecificationExecutor<PhotographerSchedulePart> {
}
