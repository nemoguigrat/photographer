package com.example.photographer.service.impl;

import com.example.photographer.domain.Photographer;
import com.example.photographer.domain.TechniqueInfo;
import com.example.photographer.event.EventPublishEvent;
import com.example.photographer.event.UserBlockedEvent;
import com.example.photographer.exception.UserAlreadyExists;
import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.service.AdminPhotographerService;
import com.example.photographer.service.dto.AdminListResponse;
import com.example.photographer.service.dto.photographer.request.AdminPhotographerCreateRequest;
import com.example.photographer.service.dto.photographer.request.AdminPhotographerFilter;
import com.example.photographer.service.dto.photographer.request.AdminPhotographerUpdateRequest;
import com.example.photographer.service.dto.photographer.response.AdminPhotographerResponse;
import com.example.photographer.support.UserStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminPhotographerServiceImpl implements AdminPhotographerService {

    PhotographerRepository repository;
    PasswordEncoder passwordEncoder;
    ApplicationEventPublisher publisher;

    @Override
    @Transactional(readOnly = true)
    public AdminListResponse<AdminPhotographerResponse> findAll(AdminPhotographerFilter filter, Pageable pageable) {
        Page<Photographer> photographers = repository.findPhotographersWithFilter(pageable);
        return AdminListResponse.of(photographers.map(this::buildPhotographerResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public AdminPhotographerResponse find(Long id) {
        Photographer photographer = repository.findPhotographerById(id);
        return buildPhotographerResponse(photographer);
    }

    @Override
    @Transactional
    public void create(AdminPhotographerCreateRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExists();
        }
        TechniqueInfo techniqueInfo = new TechniqueInfo();

        Photographer photographer = Photographer.builder()
                .firstname(request.getFirstname())
                .surname(request.getSurname())
                .middleName(request.getMiddleName())
                .trainee(request.getTrainee())
                .description(request.getDescription())
                .techniqueInfo(techniqueInfo)
                .email(request.getEmail())
                .status(UserStatus.CREATED)
                .registrationDate(LocalDate.now())
                .build();

        repository.save(photographer);
    }

    @Override
    @Transactional
    public void update(AdminPhotographerUpdateRequest request) {
        Photographer photographer = repository.findPhotographerById(request.getId());
        UserStatus oldStatus = photographer.getStatus();

        if (!photographer.getEmail().equals(request.getEmail()) && repository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExists("Пользователь с таким адресом уже существует!");
        }

        photographer.updateFrom(request);

        if (oldStatus != UserStatus.BLOCKED && photographer.getStatus() == UserStatus.BLOCKED) {
            publisher.publishEvent(UserBlockedEvent.of(photographer.getId()));
        }
    }

    @Override
    @Transactional
    public void resetPassword(Long id) {
        // reset password and send email to photographer with new pass
    }

    private AdminPhotographerResponse buildPhotographerResponse(Photographer photographer) {
        return AdminPhotographerResponse.builder()
                .id(photographer.getId())
                .email(photographer.getEmail())
                .firstname(photographer.getFirstname())
                .surname(photographer.getSurname())
                .middleName(photographer.getMiddleName())
                .contacts(photographer.getContacts())
                .phone(photographer.getPhone())
                .birthdate(photographer.getBirthdate())
                .status(photographer.getStatus())
                .trainee(photographer.isTrainee())
                .score(photographer.getScore())
                .description(photographer.getDescription())
                .registrationDate(photographer.getRegistrationDate())
                .portfolio(photographer.getPortfolio())
                .techniqueInfoId(photographer.getTechniqueInfo().getId())
                .build();
    }
}
