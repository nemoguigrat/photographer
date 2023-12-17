package com.example.photographer.api.web.controller;

import com.example.photographer.service.EventScheduleService;
import com.example.photographer.service.dto.ListResponse;
import com.example.photographer.service.dto.activity.response.ActivityResponse;
import com.example.photographer.service.dto.event.response.EventResponse;
import com.example.photographer.service.dto.location.response.LocationResponse;
import com.example.photographer.service.dto.zone.response.ZoneResponse;
import com.example.photographer.support.UmnUserDetails;
import com.example.photographer.support.api.WebApi;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@WebApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class EventScheduleController {

    EventScheduleService scheduleService;

    @GetMapping("/event/list")
    public ListResponse<EventResponse> findEvents(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails, @ParameterObject Pageable pageable) {
        return scheduleService.events(userDetails, pageable);
    }

    @GetMapping("/event/{eventId}/zone")
    public List<ZoneResponse> zones(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails, @PathVariable Long eventId) {
        return scheduleService.zones(userDetails, eventId);
    }

    @GetMapping("/event/{eventId}/locations")
    public List<LocationResponse> locations(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails, @PathVariable Long eventId) {
        return scheduleService.locations(userDetails, eventId);
    }

    @GetMapping("/event/{eventId}/activities")
    public List<ActivityResponse> activities(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails, @PathVariable Long eventId) {
        return scheduleService.activities(userDetails, eventId);
    }

    @PostMapping("/event/{eventId}/register")
    public void register(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails, @PathVariable Long eventId) {
        scheduleService.register(userDetails, eventId);
    }

    @PutMapping("/event/{eventId}/publish")
    public void publish(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails, @PathVariable Long eventId, @RequestParam Boolean publish) {
        scheduleService.changePublish(userDetails, eventId, publish);
    }
}
