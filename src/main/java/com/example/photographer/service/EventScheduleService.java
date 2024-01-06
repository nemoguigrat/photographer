package com.example.photographer.service;

import com.example.photographer.service.dto.ListResponse;
import com.example.photographer.service.dto.activity.response.ActivityResponse;
import com.example.photographer.service.dto.event.response.EventResponse;
import com.example.photographer.service.dto.location.response.LocationResponse;
import com.example.photographer.service.dto.schedule.response.PhotographerScheduleResponse;
import com.example.photographer.service.dto.zone.response.ZoneResponse;
import com.example.photographer.support.UmnUserDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface EventScheduleService {

    ListResponse<EventResponse> events(UmnUserDetails userDetails, Pageable pageable);

    ListResponse<ZoneResponse> zones(UmnUserDetails userDetails, Long eventId, Pageable pageable);

    ListResponse<LocationResponse> locations(UmnUserDetails userDetails, Long eventId, Pageable pageable);

    ListResponse<ActivityResponse> activities(UmnUserDetails userDetails, Long eventId, Pageable pageable);

    ListResponse<PhotographerScheduleResponse> photographerEvents(UmnUserDetails userDetails, Pageable pageable);

    void register(UmnUserDetails userDetails, Long eventId);

    void changePublish(UmnUserDetails userDetails, Long eventId, boolean publish);
}
