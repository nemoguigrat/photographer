package com.example.photographer.config.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties("photographers.employee")
public class EmployeeProperties {

    String defaultPassword;

    String defaultEmail;

    public boolean defaultEmployeeEnabled() {
        if (defaultEmail == null || defaultPassword == null) {
            return false;
        }

        return true;
    }
}
