package com.example.photographer.service.push;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageContext {

    List<String> tokens;
    String title;
    String message;
    Map<String, Object> data;
}
