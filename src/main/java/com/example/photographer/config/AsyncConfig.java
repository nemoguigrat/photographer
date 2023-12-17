package com.example.photographer.config;

import com.example.photographer.config.properties.AsyncProperties;
import lombok.extern.log4j.Log4j2;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
@EnableAsync
@EnableSchedulerLock(defaultLockAtMostFor = "1m")
@Log4j2
@EnableConfigurationProperties(AsyncProperties.class)
public class AsyncConfig {

    @Bean
    public TaskExecutor taskExecutor(AsyncProperties properties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        log.info("Handlers pool size: " + properties.getPoolSize());
        executor.setCorePoolSize(properties.getPoolSize());
        return executor;
    }

    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(dataSource);
    }
}
