package com.example.photographer.repository;

import com.example.photographer.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select distinct p from Event p",
            countQuery = "select count(p.id) from Event p")
    Page<Event> findEventWithFilter(Pageable pageable);
}
