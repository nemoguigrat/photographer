package com.example.photographer.support;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ShootingType {
    @JsonProperty("start")
    START,
    @JsonProperty("start")
    END,
    @JsonProperty("start")
    ALL,
    @JsonProperty("start")
    START_END,
    @JsonProperty("start")
    ANY
}
