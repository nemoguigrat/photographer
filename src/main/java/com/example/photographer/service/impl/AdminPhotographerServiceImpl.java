package com.example.photographer.service.impl;

import com.example.photographer.domain.Photographer;
import com.example.photographer.domain.TechniqueInfo;
import com.example.photographer.domain.User;
import com.example.photographer.exception.UserAlreadyExists;
import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.repository.UserRepository;
import com.example.photographer.service.AdminPhotographerService;
import com.example.photographer.service.dto.photographer.request.AdminPhotographerCreateRequest;
import com.example.photographer.service.dto.photographer.request.AdminPhotographerFilter;
import com.example.photographer.service.dto.photographer.response.AdminPhotographerList;
import com.example.photographer.service.dto.photographer.request.AdminPhotographerUpdateRequest;
import com.example.photographer.service.dto.photographer.response.AdminPhotographerResponse;
import com.example.photographer.support.UserStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public AdminPhotographerList findAll(AdminPhotographerFilter filter, Pageable pageable) {
        Page<Photographer> photographers = repository.findPhotographersWithFilter(pageable);
        return AdminPhotographerList.of(photographers.map(this::buildPhotographerResponse));
    }

    @Override
    public AdminPhotographerResponse find(Long id) {
        Photographer photographer = repository.findByIdWithUser(id);
        return buildPhotographerResponse(photographer);
    }

    @Override
    @Transactional
    public void create(AdminPhotographerCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
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
                .build();

        User user = User.builder()
                .email(request.getEmail())
                .status(UserStatus.CREATED)
                .registrationDate(LocalDate.now())
                .photographer(photographer)
                .build();

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(AdminPhotographerUpdateRequest request) {
        Photographer photographer = repository.findByIdWithUser(request.getId());

        photographer.updateFrom(request);

        User user = photographer.getUser();
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExists("Пользователь с таким адресом уже существует!");
        }

        user.setEmail(request.getEmail());
        user.setStatus(request.getStatus());
    }

    @Override
    @Transactional
    public void resetPassword(Long id) {
        // reset password and send email to photographer with new pass
    }

    private AdminPhotographerResponse buildPhotographerResponse(Photographer photographer) {
        return AdminPhotographerResponse.builder()
                .id(photographer.getId())
                .email(photographer.getUser().getEmail())
                .firstname(photographer.getFirstname())
                .surname(photographer.getSurname())
                .middleName(photographer.getMiddleName())
                .contacts(photographer.getContacts())
                .phone(photographer.getPhone())
                .birthdate(photographer.getBirthdate())
                .status(photographer.getUser().getStatus())
                .trainee(photographer.isTrainee())
                .score(photographer.getScore())
                .description(photographer.getDescription())
                .registrationDate(photographer.getUser().getRegistrationDate())
                .build();
    }
}
