package com.example.photographer.config;

import com.example.photographer.config.properties.EmployeeProperties;
import com.example.photographer.service.impl.auth.ApiLogoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(EmployeeProperties.class)
public class SecurityConfig {

    private static final String[] WHITE_LIST_URLS = {
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/actuator/**",
    };

    private static final String[] WHITE_ADMIN_LIST_URLS = {
            "/admin/activity/short/all",
            "/admin/activity/*",
            "/admin/event/*",
            "/admin/location/all",
            "/admin/zone/all"
    };

    private static final String PATH_API = "/api/**";
    private static final String PATH_ADMIN = "/admin/**";

    private final UserDetailsService apiUserDetailsService;
    private final UserDetailsService adminUserDetailsService;
    private final ApiLogoutHandler apiLogoutHandler;

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
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieMaxAge(7200);
        serializer.setUseHttpOnlyCookie(false);
        serializer.setSameSite("None");
        serializer.setUseSecureCookie(true);
        return serializer;
    }

    @Bean
    public SecurityFilterChain webSecurityConfig(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
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
                        .logoutUrl("/api/auth/logout")
                        .deleteCookies()
                        .invalidateHttpSession(true)
                        .addLogoutHandler(apiLogoutHandler)
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                );
        log.info("web is configured");
        return http.build();
    }

    @Bean
    public SecurityFilterChain adminSecurityConfig(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors(customizer ->
                        customizer.configurationSource(corsConfigurationSource())
                )
                .antMatcher(PATH_ADMIN)
                .authenticationProvider(adminAuthProvider())
                .authorizeHttpRequests(customizer -> customizer
                        .antMatchers("/admin/auth/**").permitAll()
                        .antMatchers(HttpMethod.GET, WHITE_ADMIN_LIST_URLS).permitAll()
                        .antMatchers(PATH_ADMIN).hasRole("ADMIN")
                )
                .exceptionHandling(customizer -> customizer
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(customizer -> customizer
                        .logoutUrl("/admin/auth/logout")
                        .deleteCookies()
                        .invalidateHttpSession(true)
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                );

        log.info("admin is configured");

        return http.build();
    }

    @Bean
    public SecurityFilterChain defaultSecurityConfig(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("*"));

        configuration.setAllowedOriginPatterns(List.of("http://localhost:[*]", "https://localhost:[*]", "https://shumakooov.github.io", "https://photographersekb.ru", "https://photographersekb.ru:[*]"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
