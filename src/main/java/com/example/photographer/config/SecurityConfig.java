package com.example.photographer.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] WHITE_LIST_URLS = {
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/actuator/**"
    };

    private static final String PATH_API = "/api/**";
    private static final String PATH_ADMIN = "/admin/**";

    private final UserDetailsService apiUserDetailsService;
    private final UserDetailsService adminUserDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider adminAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationProvider apiAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(apiUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices =
                new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    public SecurityFilterChain webSecurityConfig(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher(PATH_API)
                .authenticationProvider(apiAuthProvider())
                .authorizeHttpRequests(customizer -> customizer
                        .antMatchers("/api/auth/**").permitAll()
                        .antMatchers(PATH_API).hasRole("USER")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(customizer -> customizer
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(customizer -> customizer
                        .logoutUrl("/api/logout")
                        .deleteCookies()
                        .invalidateHttpSession(true)
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()))
                .rememberMe().rememberMeServices(rememberMeServices());

        log.info("web is configured");
        return http.build();
    }

    @Bean
    public SecurityFilterChain adminSecurityConfig(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher(PATH_ADMIN)
                .authenticationProvider(adminAuthProvider())
                .authorizeHttpRequests(customizer -> customizer
                        .antMatchers("/admin/auth/**").permitAll()
                        .antMatchers(PATH_ADMIN).hasRole("ADMIN")
                )
                .exceptionHandling(customizer -> customizer
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(customizer -> customizer
                        .logoutUrl("/admin/auth/logout")
                        .invalidateHttpSession(true)
                        .permitAll());

        log.info("admin is configured");

        return http.build();
    }

    @Bean
    public SecurityFilterChain defaultSecurityConfig(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .antMatchers(WHITE_LIST_URLS).permitAll()
                .anyRequest().denyAll();

        log.info("deafult is configured");

        return http.build();
    }
}
