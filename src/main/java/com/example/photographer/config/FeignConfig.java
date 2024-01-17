package com.example.photographer.config;

import com.example.photographer.config.properties.DistributionServiceProperties;
import com.example.photographer.service.feign.DistributionApi;
import feign.Feign;
import feign.Target;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DistributionServiceProperties.class)
public class FeignConfig {

    @Bean
    public DistributionApi distributionApi(DistributionServiceProperties properties) {
        var builder = Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder());

        if (properties.getUrl() == null) {
            return builder.target(Target.EmptyTarget.create(DistributionApi.class));
        }

        return builder
                .target(DistributionApi.class, properties.getUrl());
    }
}
