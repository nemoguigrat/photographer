package com.example.photographer.service;

import com.example.photographer.service.dto.photographer.request.PhotographerChangeCredentialRequest;
import com.example.photographer.service.dto.photographer.request.PhotographerUpdateRequest;
import com.example.photographer.service.dto.photographer.response.PhotographerAvatarResponse;
import com.example.photographer.service.dto.photographer.response.PhotographerInfoResponse;
import com.example.photographer.support.UmnUserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface PhotographerService {

    PhotographerInfoResponse info(UmnUserDetails userDetails);

    void update(UmnUserDetails userDetails, PhotographerUpdateRequest request);

    void updateCredential(UmnUserDetails userDetails, PhotographerChangeCredentialRequest request);

    void upload(UmnUserDetails userDetails, MultipartFile file);

    PhotographerAvatarResponse download(Long photographerId);
}
