package com.example.photographer.config;

import com.example.photographer.service.push.ExpoPushService;
import com.example.photographer.service.push.PushService;
import io.github.jav.exposerversdk.PushClient;
import io.github.jav.exposerversdk.PushClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PushConfig {

    @Bean
    public PushService pushService() throws PushClientException {
        PushClient client = new PushClient();
        return new ExpoPushService(client);
    }
}
