package com.example.photographer.repository;

import com.example.photographer.domain.Photographer;
import com.example.photographer.support.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Long> {

    default Photographer findPhotographerById(Long id) {
        return findById(id).orElseThrow(() -> new UsernameNotFoundException("msg"));
    }

    default Photographer findPhotographerByEmail(String email) {
        return findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("msg"));
    }

    @Query(value = "select distinct p from Photographer p ",
            countQuery = "select count(p.id) from Photographer p ")
    Page<Photographer> findPhotographersWithFilter(Pageable pageable);

    boolean existsByEmail(String email);

    Optional<Photographer> findByEmail(String email);

    @Query("select distinct p.expoPushToken from Photographer p where p.expoPushToken is not null and p.status = :status")
    List<String> findTokens(UserStatus status);

    @Query("select distinct p.expoPushToken from Photographer p left join p.schedules s where p.expoPushToken is not null and p.status = :status and s.event.id = :eventId")
    List<String> findTokensByEvent(Long eventId, UserStatus status);

    @Modifying
    @Query("update Photographer p set p.expoPushToken = null where p.id = :id")
    void clearTokens(Long id);

    @Modifying
    @Query("update Photographer p set p.expoPushToken = :token where p.id = :id")
    void setToken(Long id, String token);
}
