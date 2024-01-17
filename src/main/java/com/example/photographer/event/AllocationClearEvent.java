package com.example.photographer.event;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Value(staticConstructor = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AllocationClearEvent {

    List<Long> ids;
}
