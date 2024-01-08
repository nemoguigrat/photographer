package com.example.photographer.event.scheduler;

import com.example.photographer.config.properties.PhotographerProperties;
import com.example.photographer.event.ScheduleChangedEvent;
import com.example.photographer.service.AdminEventService;
import com.example.photographer.service.push.PhotographerNotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class PhotographerScheduler {

    PhotographerNotificationService photographerNotificationService;
    AdminEventService adminEventService;
    PhotographerProperties photographerProperties;

    @Scheduled(cron = "${photographers.jobs.notify.before-event:-}")
    @SchedulerLock(name = "PhotographerNotifyJob_notifyPhotographers",
            lockAtLeastFor = "${photographers.jobs.lockAt:PT1M}", lockAtMostFor = "${photographers.jobs.lockMost:PT2M}")
    public void notifyPhotographers() {
        photographerNotificationService.sendEventNotification();
    }

    @Scheduled(cron = "${photographers.jobs.notify.event-changes:-}")
    @SchedulerLock(name = "PhotographerNotifyJob_notifyAboutEventChanges",
            lockAtLeastFor = "${photographers.jobs.lockAt:PT1M}", lockAtMostFor = "${photographers.jobs.lockMost:PT2M}")
    public void notifyAboutEventChanges() {
        List<Long> events = adminEventService.findLastChangedEvents(photographerProperties.getDelaySeconds());

        events.stream().map(ScheduleChangedEvent::of).forEach(
                photographerNotificationService::sendScheduleChangedPush
        );
    }
}
