package com.example.photographer.service.impl;

import com.example.photographer.domain.User;
import com.example.photographer.repository.UserRepository;
import com.example.photographer.support.UmnUserDetails;
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

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserById(Long.parseLong(username));

        if (Boolean.TRUE.equals(user.getBlocked())) {
            throw new UsernameNotFoundException("msg");
        }

        String id = user.getId().toString();
        String password = user.getPassword();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        return new UmnUserDetails(id, password, List.of(grantedAuthority));
    }
}
