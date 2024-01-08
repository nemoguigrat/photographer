package com.example.photographer.service;

import com.example.photographer.service.dto.schedule.part.response.SchedulePartResponse;
import com.example.photographer.service.dto.schedule.request.FreetimeRequest;
import com.example.photographer.service.dto.schedule.request.PriorityRequest;
import com.example.photographer.service.dto.schedule.response.FreetimeResponse;
import com.example.photographer.service.dto.schedule.response.PriorityResponse;
import com.example.photographer.support.UmnUserDetails;

import java.util.List;

public interface ScheduleService {

    List<FreetimeResponse> freetime(UmnUserDetails userDetails, Long eventId);

    List<SchedulePartResponse> schedule(UmnUserDetails userDetails, Long eventId);

    List<PriorityResponse> zonePriority(UmnUserDetails userDetails, Long eventId);

    void upsertZonePriority(UmnUserDetails userDetails, Long eventId, PriorityRequest request);

    void upsertFreeTime(UmnUserDetails userDetails, Long eventId, FreetimeRequest request);

    void deleteZonePriority(UmnUserDetails userDetails, Long eventId, Long zonePriorityId);

    void deleteFreeTime(UmnUserDetails userDetails, Long eventId, Long freetimeId);
}
