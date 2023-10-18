package com.example.photographer.service.impl;

import com.example.photographer.service.dto.photographer.request.PhotographerUpdateRequest;
import com.example.photographer.service.dto.photographer.response.PhotographerInfoResponse;
import com.example.photographer.domain.Photographer;
import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.service.PhotographerService;
import com.example.photographer.support.UmnUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PhotographerServiceImpl implements PhotographerService {

    private final PhotographerRepository photographerRepository;

    @Transactional(readOnly = true)
    public PhotographerInfoResponse info(UmnUserDetails userDetails) {
        Photographer photographer = photographerRepository.findByUser_Id(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("msg"));

        return PhotographerInfoResponse.builder()
                .id(photographer.getId())
                .firstname(photographer.getFirstname())
                .surname(photographer.getSurname())
                .middleName(photographer.getMiddleName())
                .birthdate(photographer.getBirthdate())
                .email(photographer.getEmail())
                .phone(photographer.getPhone())
                .contacts(photographer.getContacts())
                .score(photographer.getScore())
                .build();
    }

    @Override
    @Transactional
    public void update(UmnUserDetails userDetails, PhotographerUpdateRequest request) {
        Photographer photographer = photographerRepository.findByUser_Id(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("msg"));

        photographer.updateFrom(request);
        //transaction save
    }
}
