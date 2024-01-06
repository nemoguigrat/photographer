package com.example.photographer.api.admin.controller;

import com.example.photographer.support.api.AdminApi;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@AdminApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AdminAllocationController {
    // для работы с распределением на стороне админа. Так же будет проверять сервис распеределения на наличие ошибок в распределении и выводить их
}
