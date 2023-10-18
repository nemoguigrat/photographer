package com.example.photographer.api.admin.controller;


import com.example.photographer.service.dto.auth.LoginResponse;
import com.example.photographer.support.UmnUserDetails;
import com.example.photographer.support.api.AdminApi;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

import static lombok.AccessLevel.PRIVATE;

@AdminApi
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AdminUserController {

}
