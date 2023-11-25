package com.example.photographer.repository;

import com.example.photographer.domain.PhotographerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotographerScheduleRepository extends JpaRepository<PhotographerSchedule, Long> {
}
