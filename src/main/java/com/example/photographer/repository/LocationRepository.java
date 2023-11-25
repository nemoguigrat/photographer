package com.example.photographer.repository;

import com.example.photographer.domain.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(value = "select distinct p from Location p",
            countQuery = "select count(p.id) from Location p")
    Page<Location> findLocationWithFilter(Pageable pageable);

    List<Location> findByEvent_Id(Long eventId);
}
