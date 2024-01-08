package com.example.photographer.event;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value(staticConstructor = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBlockedEvent {

    Long id;
}
