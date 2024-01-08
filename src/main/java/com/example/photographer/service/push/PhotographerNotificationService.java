package com.example.photographer.service.push;

import com.example.photographer.domain.Event;
import com.example.photographer.domain.Photographer;
import com.example.photographer.domain.PhotographerSchedulePart;
import com.example.photographer.event.EventPublishEvent;
import com.example.photographer.event.ScheduleChangedEvent;
import com.example.photographer.event.UserBlockedEvent;
import com.example.photographer.repository.EventRepository;
import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.repository.SchedulePartRepository;
import com.example.photographer.support.UserStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PhotographerNotificationService {

    PhotographerRepository photographerRepository;
    SchedulePartRepository schedulePartRepository;
    EventRepository eventRepository;
    PushService pushService;

    static String NEW_EVENT_MESSAGE = "Открыт набор на новое событие %s. Время проведения %s - %s";
    static String SCHEDULE_CHANGES = "Расписание события %s изменилось! Сверьтесь со своим графиком";
    static String BLOCKED_MESSAGE = "Администратором было принято решение о блокировке учетной записи!";
    static String UPCOMING_MESSAGE = "До ближайшей съемки осталось менее %s минут. Проверьте, пожалуйста, в расписании место проведения";

    @Transactional(readOnly = true)
    public void sendEventPublishedPush(EventPublishEvent publishEvent) {
        List<String> tokens = photographerRepository.findTokens(UserStatus.APPROVED);

        MessageContext context = MessageContext.builder()
                .tokens(tokens)
                .title("Появилось новое событие!")
                .message(String.format(NEW_EVENT_MESSAGE, publishEvent.getName(), publishEvent.getStartTime(), publishEvent.getEndTime()))
                .build();

        pushService.send(context);
    }

    @Transactional(readOnly = true)
    public void sendScheduleChangedPush(ScheduleChangedEvent changedEvent) {
        Optional<Event> event = eventRepository.findById(changedEvent.getEventId());

        if (event.isEmpty()) {
            return;
        }
        List<String> tokens = photographerRepository.findTokensByEvent(changedEvent.getEventId(), UserStatus.APPROVED);

        MessageContext context = MessageContext.builder()
                .tokens(tokens)
                .title("Расписание события изменилось")
                .message(String.format(SCHEDULE_CHANGES, event.get().getName()))
                .build();

        pushService.send(context);
    }

    @Transactional(readOnly = true)
    public void sendUserBlockedPush(UserBlockedEvent blockedEvent) {
        Optional<Photographer> user = photographerRepository.findById(blockedEvent.getId());

        if (user.isEmpty() && user.get().getExpoPushToken() != null) {
            return;
        }

        MessageContext context = MessageContext.builder()
                .tokens(List.of(user.get().getExpoPushToken()))
                .title("Учетная запись заблокирована")
                .message(BLOCKED_MESSAGE)
                .build();

        pushService.send(context);
    }

    @Transactional
    public void sendEventNotification() {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime firstNotifyTime = LocalDateTime.now().plusMinutes(15L);
        LocalDateTime secondNotifyTime = LocalDateTime.now().plusMinutes(2L);

        List<PhotographerSchedulePart> upcomings = schedulePartRepository.findUpcoming(now, firstNotifyTime, secondNotifyTime);

        List<String> tokensForFirst = new ArrayList<>();
        List<String> tokensForSecond = new ArrayList<>();

        for (PhotographerSchedulePart part : upcomings) {
            if (part.getStartTime().isBefore(firstNotifyTime) || part.getStartTime().isEqual(firstNotifyTime)) {

                tokensForFirst.add(part.getPhotographerSchedule().getPhotographer().getExpoPushToken());
                part.setNotifiedTime(now);

            } else if (part.getStartTime().isBefore(secondNotifyTime) || part.getStartTime().isEqual(secondNotifyTime)) {

                tokensForSecond.add(part.getPhotographerSchedule().getPhotographer().getExpoPushToken());
                part.setNotifiedTime(now);
            }
        }

        sendUpcomingNotify(String.format(UPCOMING_MESSAGE, 15L), tokensForFirst);
        sendUpcomingNotify(String.format(UPCOMING_MESSAGE, 2L), tokensForSecond);
    }

    private void sendUpcomingNotify(String message, List<String> tokens) {
        if (tokens.isEmpty()) {
            return;
        }

        MessageContext context = MessageContext.builder()
                .tokens(tokens)
                .title("Скоро съемка!")
                .message(message)
                .build();

        pushService.send(context);
    }

}
