package com.example.photographer.service.push;

import com.example.photographer.domain.Event;
import com.example.photographer.domain.Photographer;
import com.example.photographer.event.EventPublishEvent;
import com.example.photographer.event.ScheduleChangedEvent;
import com.example.photographer.repository.EventRepository;
import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.support.UserStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PhotographerNotificationService {

    PhotographerRepository photographerRepository;
    EventRepository eventRepository;
    PushService pushService;

    static String NEW_EVENT_MESSAGE = "Открыт набор на новое событие %s. Время проведения %s - %s";
    static String SCHEDULE_CHANGES = "Расписание события %s изменилось! Сверьтесь со своим графиком";

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
    public void sendEventNotification() {

    }

}
