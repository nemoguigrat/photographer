package com.example.photographer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableJdbcHttpSession(
        maxInactiveIntervalInSeconds=7200
)
public class SpringSessionConfig extends AbstractHttpSessionApplicationInitializer {
}
