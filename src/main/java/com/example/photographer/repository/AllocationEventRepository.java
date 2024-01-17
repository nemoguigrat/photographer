package com.example.photographer.repository;

import com.example.photographer.domain.AllocationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocationEventRepository extends JpaRepository<AllocationEvent, Long> {

    List<AllocationEvent> findByEmployee_Id(Long id);

    @Modifying
    @Query("delete from AllocationEvent a where a.id in :ids")
    void deleteByIds(List<Long> ids);
}
