package com.example.photographer.service.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface DistributionApi {

    @RequestLine("POST /distribute/{eventId}/{zoneId}")
    @Headers("Content-Type: application/json")
    void distribute(@Param("eventId") Long eventId, @Param("zoneId") Long zoneId, PhotographersList list);

    @RequestLine("POST /check/{eventId}/{zoneId}")
    @Headers("Content-Type: application/json")
    void check(@Param("eventId") Long eventId, @Param("zoneId") Long zoneId, PhotographersList list);
}
