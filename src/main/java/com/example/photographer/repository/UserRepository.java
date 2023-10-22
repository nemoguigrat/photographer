package com.example.photographer.repository;

import com.example.photographer.domain.Photographer;
import com.example.photographer.domain.User;
import com.example.photographer.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    default User findUserById(Long id) {
        return findById(id).orElseThrow(() -> new UsernameNotFoundException("msg"));
    }

    default User findUserByEmail(String email) {
        return findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("msg"));
    }

    default User findByIdWithPhotographer(Long id) {
        return findByIdAndFetchPhotographer(id).orElseThrow(() -> new NotFoundException(id));
    }

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select distinct u from User u left join fetch u.photographer where u.id = :id")
    Optional<User> findByIdAndFetchPhotographer(Long id);
}
