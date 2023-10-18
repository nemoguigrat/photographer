package com.example.photographer.repository;

import com.example.photographer.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    default Employee findEmployeeById(Long id) {
        return findById(id).orElseThrow(() -> new UsernameNotFoundException("msg"));
    }

    default Employee findEmployeeByEmail(String email) {
        return findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("msg"));
    }

    Optional<Employee> findByEmail(String email);

}
