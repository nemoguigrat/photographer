package com.example.photographer.service.impl;

import com.example.photographer.domain.User;
import com.example.photographer.exception.UserAlreadyExists;
import com.example.photographer.repository.UserRepository;
import com.example.photographer.service.dto.photographer.request.PhotographerChangeCredentialRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PhotographerServiceImpl implements PhotographerService {

    PhotographerRepository photographerRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public PhotographerInfoResponse info(UmnUserDetails userDetails) {
        Photographer photographer = photographerRepository.findByUser_Id(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("msg"));

        return PhotographerInfoResponse.builder()
                .id(photographer.getId())
                .email(photographer.getUser().getEmail())
                .firstname(photographer.getFirstname())
                .surname(photographer.getSurname())
                .middleName(photographer.getMiddleName())
                .birthdate(photographer.getBirthdate())
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

    @Override
    @Transactional
    public void updateCredential(UmnUserDetails userDetails, PhotographerChangeCredentialRequest request) {
        User user = userRepository.findByIdWithPhotographer(userDetails.getId());

        if (request.getEmail() != null && !user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExists("Пользователь с таким адресом уже существует!");
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getPassword() != null) {
            String hashedPass = passwordEncoder.encode(request.getPassword());
            user.setPassword(hashedPass);
        }
    }
}
