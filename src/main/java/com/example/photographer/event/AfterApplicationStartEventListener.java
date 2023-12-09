package com.example.photographer.event;

import com.example.photographer.config.properties.EmployeeProperties;
import com.example.photographer.domain.Employee;
import com.example.photographer.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AfterApplicationStartEventListener implements ApplicationListener<ApplicationReadyEvent> {

    EmployeeRepository employeeRepository;
    PasswordEncoder passwordEncoder;
    EmployeeProperties employeeProperties;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (employeeRepository.count() > 0 || !employeeProperties.defaultEmployeeEnabled()) {
            return;
        }

        employeeRepository.save(Employee.builder()
                .email(employeeProperties.getDefaultEmail())
                .password(passwordEncoder.encode(employeeProperties.getDefaultPassword()))
                .registrationDate(LocalDate.now())
                .blocked(false)
                .build());
    }
}
