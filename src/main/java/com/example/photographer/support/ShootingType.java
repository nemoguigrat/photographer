package com.example.photographer.support;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ShootingType {
    @JsonProperty("start")
    START,
    @JsonProperty("end")
    END,
    @JsonProperty("all")
    ALL,
    @JsonProperty("start_end")
    START_END,
    @JsonProperty("any")
    ANY
}
