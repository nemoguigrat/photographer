package com.example.photographer.config.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties("photographers.distribution")
public class DistributionServiceProperties {

    String url;
}
