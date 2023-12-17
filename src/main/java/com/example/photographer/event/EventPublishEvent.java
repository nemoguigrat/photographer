package com.example.photographer.event;

import com.example.photographer.domain.Event;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.TimeZone;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventPublishEvent {

    Long eventId;

    String name;

    LocalDateTime startTime;

    LocalDateTime endTime;

    TimeZone timeZone;

    public static EventPublishEvent from(Event event) {
       return EventPublishEvent
                .builder()
                .eventId(event.getId())
                .name(event.getName())
                .timeZone(event.getTimeZone())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .build();
    }
}
