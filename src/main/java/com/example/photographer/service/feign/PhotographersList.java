package com.example.photographer.service.feign;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class PhotographersList {

    Long employee;

    List<Long> list;
}
