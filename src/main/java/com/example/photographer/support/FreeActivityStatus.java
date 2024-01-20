package com.example.photographer.support;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FreeActivityStatus {

    @JsonProperty("not_distributed")
    NOT_DISTRIBUTED,

    @JsonProperty("photographer_lack")
    PHOTOGRAPHER_LACK,

    @JsonProperty("distributed")
    DISTRIBUTED


}
