package com.example.photographer.config.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties("photographers")
public class PhotographerProperties {

    String defaultImage;

    List<String> mediaTypes = List.of("image/jpeg");
}
