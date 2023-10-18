package com.example.photographer.support;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserStatus {

    @JsonProperty("created")
    CREATED,

    @JsonProperty("blocked")
    BLOCKED,

    @JsonProperty("approved")
    APPROVED
}
