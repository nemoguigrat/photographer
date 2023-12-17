package com.example.photographer.event.scheduler;

import com.example.photographer.service.push.PhotographerNotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class PhotographerScheduler {

    PhotographerNotificationService photographerNotificationService;

    @Scheduled(cron = "${photographers.jobs.notify}")
    @SchedulerLock(name = "PhotographerNotifyJob_notifyPhotographers",
            lockAtLeastFor = "${photographers.jobs.lockAt:PT1M}", lockAtMostFor = "${photographers.jobs.lockMost:PT2M}")
    public void notifyPhotographers() {
        photographerNotificationService.sendEventNotification();
    }
}
