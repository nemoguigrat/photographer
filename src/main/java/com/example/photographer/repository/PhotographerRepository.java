package com.example.photographer.repository;

import com.example.photographer.domain.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Long> {

    Optional<Photographer> findByEmail(String email);

    Optional<Photographer> findByUser_Id(Long userId);
}
