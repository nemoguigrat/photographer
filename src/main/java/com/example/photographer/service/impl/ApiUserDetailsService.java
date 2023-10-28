package com.example.photographer.service.impl;

import com.example.photographer.domain.Photographer;
import com.example.photographer.repository.PhotographerRepository;
import com.example.photographer.support.UmnUserDetails;
import com.example.photographer.support.UserStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ApiUserDetailsService implements UserDetailsService {

    PhotographerRepository photographerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Photographer photographer = photographerRepository.findPhotographerById(Long.parseLong(username));

        if (photographer.getStatus() != UserStatus.APPROVED) {
            throw new UsernameNotFoundException("msg");
        }

        String id = photographer.getId().toString();
        String password = photographer.getPassword();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        return new UmnUserDetails(id, password, List.of(grantedAuthority));
    }
}
