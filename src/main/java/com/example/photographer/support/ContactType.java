package com.example.photographer.support;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ContactType {
    @JsonProperty("vk")
    VK,
    @JsonProperty("tg")
    TG
}
