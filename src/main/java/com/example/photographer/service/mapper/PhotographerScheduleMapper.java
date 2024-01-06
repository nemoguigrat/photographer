package com.example.photographer.service.mapper;

import com.example.photographer.domain.PhotographerSchedule;
import com.example.photographer.service.dto.schedule.response.AdminPhotographerScheduleResponse;
import com.example.photographer.service.dto.schedule.response.PhotographerScheduleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhotographerScheduleMapper {

    @Mapping(source = "photographerSchedule.event.id", target = "eventId")
    @Mapping(source = "photographerSchedule.photographer.id", target = "photographerId")
    AdminPhotographerScheduleResponse domainToAdminResponse(PhotographerSchedule photographerSchedule);

    @Mapping(source = "photographerSchedule.event.id", target = "eventId")
    @Mapping(source = "photographerSchedule.event.name", target = "eventName")
    @Mapping(source = "photographerSchedule.event.description", target = "eventDescription")
    PhotographerScheduleResponse domainToApiResponse(PhotographerSchedule photographerSchedule);
}
