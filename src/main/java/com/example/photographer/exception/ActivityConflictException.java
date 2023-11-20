package com.example.photographer.exception;

import com.example.photographer.service.dto.activity.response.ActivityConflictResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityConflictException extends RuntimeException {

    final List<ActivityConflictResponse> conflicts;

    public ActivityConflictException(List<ActivityConflictResponse> conflicts) {
        this.conflicts = conflicts;
    }

    public ActivityConflictException() {
        this.conflicts = null;
    }
}
