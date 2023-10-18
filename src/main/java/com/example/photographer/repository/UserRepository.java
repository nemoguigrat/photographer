package com.example.photographer.repository;

import com.example.photographer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
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

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
