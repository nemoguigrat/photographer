package com.example.photographer.service.impl.auth;

import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.support.UmnUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Component
public class ApiLogoutHandler implements LogoutHandler {

    PhotographerRepository photographerRepository;

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return;
        }

        UmnUserDetails userDetails = (UmnUserDetails) authentication.getPrincipal();
        photographerRepository.clearTokens(userDetails.getId());
    }
}
