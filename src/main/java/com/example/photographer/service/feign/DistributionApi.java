package com.example.photographer.service.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface DistributionApi {

    @RequestLine("POST /api/distribute/{eventId}/{zoneId}")
    @Headers("Content-Type: application/json")
    void distribute(@Param("eventId") Long eventId, @Param("zoneId") Long zoneId, PhotographersList list);

    @RequestLine("POST /api/check/{eventId}/{zoneId}")
    @Headers("Content-Type: application/json")
    ResultResponse check(@Param("eventId") Long eventId, @Param("zoneId") Long zoneId, PhotographersList list);
}
