package com.example.photographer.api.web.controller;

import com.example.photographer.service.ScheduleService;
import com.example.photographer.service.dto.schedule.part.response.SchedulePartResponse;
import com.example.photographer.service.dto.schedule.request.FreetimeRequest;
import com.example.photographer.service.dto.schedule.request.PriorityRequest;
import com.example.photographer.service.dto.schedule.response.FreetimeResponse;
import com.example.photographer.service.dto.schedule.response.PriorityResponse;
import com.example.photographer.support.UmnUserDetails;
import com.example.photographer.support.api.WebApi;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@WebApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class PhotographerScheduleController {

    ScheduleService scheduleService;

    @GetMapping("/event/{eventId}/schedule")
    public List<SchedulePartResponse> schedule(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails,
                                               @PathVariable Long eventId) {
        return scheduleService.schedule(userDetails, eventId);
    }

    @GetMapping("/event/{eventId}/freetime")
    public List<FreetimeResponse> freetime(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails,
                                           @PathVariable Long eventId) {
        return scheduleService.freetime(userDetails, eventId);
    }

    @GetMapping("/event/{eventId}/zone_priority")
    public List<PriorityResponse> zonePriority(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails,
                                               @PathVariable Long eventId) {
        return scheduleService.zonePriority(userDetails, eventId);
    }


    @PostMapping("/event/{eventId}/zone_priority/upsert")
    public void upsertZonePriority(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails,
                                   @PathVariable Long eventId,
                                   @RequestBody PriorityRequest request) {
        scheduleService.upsertZonePriority(userDetails, eventId, request);
    }

    @PostMapping("/event/{eventId}/freetime/upsert")
    public void upsertFreeTime(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails,
                               @PathVariable Long eventId,
                               @RequestBody FreetimeRequest request) {
        scheduleService.upsertFreeTime(userDetails, eventId, request);
    }

    @DeleteMapping("/event/{eventId}/zone_priority/delete/{zonePriorityId}")
    public void deleteZonePriority(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails,
                                   @PathVariable Long eventId,
                                   @PathVariable Long zonePriorityId) {
        scheduleService.deleteZonePriority(userDetails, eventId, zonePriorityId);
    }

    @DeleteMapping("/event/{eventId}/freetime/delete/{freetimeId}")
    public void deleteFreeTime(@Parameter(hidden = true) @AuthenticationPrincipal UmnUserDetails userDetails,
                               @PathVariable Long eventId,
                               @PathVariable Long freetimeId) {
        scheduleService.deleteFreeTime(userDetails, eventId, freetimeId);
    }
}
