package com.example.photographer.service.impl;

import com.example.photographer.config.properties.PhotographerProperties;
import com.example.photographer.exception.NotSupportedMediaTypeException;
import com.example.photographer.exception.UserAlreadyExists;
import com.example.photographer.service.dto.photographer.request.PhotographerChangeCredentialRequest;
import com.example.photographer.service.dto.photographer.request.PhotographerUpdateRequest;
import com.example.photographer.service.dto.photographer.response.PhotographerAvatarResponse;
import com.example.photographer.service.dto.photographer.response.PhotographerInfoResponse;
import com.example.photographer.domain.Photographer;
import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.service.PhotographerService;
import com.example.photographer.support.UmnUserDetails;
import com.example.photographer.util.FileUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PhotographerServiceImpl implements PhotographerService {

    PhotographerRepository photographerRepository;
    PasswordEncoder passwordEncoder;
    PhotographerProperties photographerProperties;
    ResourceLoader resourceLoader;

    @Override
    @Transactional(readOnly = true)
    public PhotographerInfoResponse info(UmnUserDetails userDetails) {
        Photographer photographer = photographerRepository.findPhotographerById(userDetails.getId());

        return PhotographerInfoResponse.builder()
                .id(photographer.getId())
                .email(photographer.getEmail())
                .firstname(photographer.getFirstname())
                .surname(photographer.getSurname())
                .middleName(photographer.getMiddleName())
                .birthdate(photographer.getBirthdate())
                .phone(photographer.getPhone())
                .contacts(photographer.getContacts())
                .score(photographer.getScore())
                .portfolio(photographer.getPortfolio())
                .build();
    }

    @Override
    @Transactional
    public void update(UmnUserDetails userDetails, PhotographerUpdateRequest request) {
        Photographer photographer = photographerRepository.findPhotographerById(userDetails.getId());

        photographer.updateFrom(request);
        //transaction save
    }

    @Override
    @Transactional
    public void updateCredential(UmnUserDetails userDetails, PhotographerChangeCredentialRequest request) {
        Photographer photographer = photographerRepository.findPhotographerById(userDetails.getId());

        if (request.getEmail() != null && !photographer.getEmail().equals(request.getEmail()) && photographerRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExists("Пользователь с таким адресом уже существует!");
        }

        if (request.getEmail() != null) {
            photographer.setEmail(request.getEmail());
        }

        if (request.getPassword() != null) {
            String hashedPass = passwordEncoder.encode(request.getPassword());
            photographer.setPassword(hashedPass);
        }
    }

    @SneakyThrows
    @Transactional
    @Override
    public void upload(UmnUserDetails userDetails, MultipartFile file) {
        Photographer photographer = photographerRepository.findPhotographerById(userDetails.getId());

        byte[] content = null;
        String contentType = null;

        if (!file.isEmpty()) {
            String ext = FileUtils.resolveContentType(file.getBytes(), file.getName());

            if (!photographerProperties.getMediaTypes().contains(ext)) {
                throw new NotSupportedMediaTypeException();
            }

            content = file.getBytes();
            contentType = ext;
        }

        photographer.setImage(content);
        photographer.setContentType(contentType);
    }

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public PhotographerAvatarResponse download(Long photographerId) {
        Photographer photographer = photographerRepository.findPhotographerById(photographerId);

        if (photographer.getImage() != null) {
            Resource resource = new ByteArrayResource(photographer.getImage());
            return PhotographerAvatarResponse.builder()
                    .content(resource)
                    .contentType(photographer.getContentType())
                    .build();
        }

        String filepath = photographerProperties.getDefaultImage();
        Resource resource = resourceLoader.getResource(filepath);
        String contentType = FileUtils.resolveContentType(resource.getInputStream());

        return PhotographerAvatarResponse.builder()
                .content(resource)
                .contentType(contentType)
                .build();
    }
}
