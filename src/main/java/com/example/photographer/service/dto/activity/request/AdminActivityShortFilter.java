package com.example.photographer.service.dto.activity.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminActivityShortFilter {

    Long locationId;

    Long eventId;

    Long zoneId;
}
