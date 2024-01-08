package com.example.photographer.event.listener;

import com.example.photographer.event.EventPublishEvent;
import com.example.photographer.event.ScheduleChangedEvent;
import com.example.photographer.event.UserBlockedEvent;
import com.example.photographer.service.push.PhotographerNotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhotographerEventListener {

    PhotographerNotificationService notificationService;

    @Async("taskExecutor")
    @TransactionalEventListener
    public void handleEvent(ScheduleChangedEvent changedEvent) {
        notificationService.sendScheduleChangedPush(changedEvent);
    }

    @Async("taskExecutor")
    @TransactionalEventListener
    public void handleEvent(EventPublishEvent publishEvent) {
        notificationService.sendEventPublishedPush(publishEvent);
    }

    @Async("taskExecutor")
    @TransactionalEventListener
    public void handleEvent(UserBlockedEvent blockedEvent) {
        notificationService.sendUserBlockedPush(blockedEvent);
    }
}
