package com.example.photographer.service.impl;

import com.example.photographer.domain.AllocationEvent;
import com.example.photographer.event.AllocationClearEvent;
import com.example.photographer.repository.AllocationEventRepository;
import com.example.photographer.service.DistributionService;
import com.example.photographer.service.dto.activity.distribution.request.AdminDistributionRequest;
import com.example.photographer.service.dto.activity.distribution.response.AdminDistributionResponse;
import com.example.photographer.service.feign.DistributionApi;
import com.example.photographer.service.feign.PhotographersList;
import com.example.photographer.service.feign.ResultResponse;
import com.example.photographer.support.UmnUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DistributionServiceImpl implements DistributionService {

    AllocationEventRepository allocationEventRepository;
    ApplicationEventPublisher applicationEventPublisher;
    DistributionApi distributionApi;

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void distribute(UmnUserDetails admin, AdminDistributionRequest request) {
        PhotographersList photographersList = PhotographersList.of(admin.getId(), request.getPhotographers());

        distributionApi.distribute(request.getEventId(), request.getZoneId(), photographersList);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public ResultResponse check(UmnUserDetails admin, AdminDistributionRequest request) {
        PhotographersList photographersList = PhotographersList.of(admin.getId(), request.getPhotographers());

        return distributionApi.check(request.getEventId(), request.getZoneId(), photographersList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminDistributionResponse> ping(UmnUserDetails admin) {
        List<AllocationEvent> allocationEvents = allocationEventRepository.findByEmployee_Id(admin.getId());

        List<Long> eventsIds = new ArrayList<>();
        List<AdminDistributionResponse> events = allocationEvents.stream().peek(x -> eventsIds.add(x.getId()))
                .map(x -> AdminDistributionResponse.builder()
                .code(x.getCode())
                .message(x.getMessage())
                .eventTime(x.getEventTime())
                .build()
        ).collect(Collectors.toList());

        applicationEventPublisher.publishEvent(AllocationClearEvent.of(eventsIds));

        return events;
    }

    @Override
    @Transactional
    public void clear(List<Long> ids) {
        allocationEventRepository.deleteByIds(ids);
    }
}
