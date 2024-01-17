package com.example.photographer.event.listener;

import com.example.photographer.event.AllocationClearEvent;
import com.example.photographer.service.DistributionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AllocationClearEventListener {

    DistributionService distributionService;

    @Async("taskExecutor")
    @TransactionalEventListener
    public void handleEvent(AllocationClearEvent event) {
        distributionService.clear(event.getIds());
    }
}
