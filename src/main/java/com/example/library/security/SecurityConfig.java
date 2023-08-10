package com.example.library.security;

import com.example.library.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.annotation.web.builders.HttpSecurity.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailService userDetailService;

    @Autowired
    public SecurityConfig(CustomUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //hasRole and hasAuthority
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authorize) -> {
                    authorize
//                            .requestMatchers("/registration", "/login", "/register", "/checkUser", "/authorize", "/testAdminPage").permitAll()

//                            .requestMatchers(new AntPathRequestMatcher("/authorize")).authenticated()
//                            .requestMatchers(new AntPathRequestMatcher("/testURL")).hasRole("User")
//                            .requestMatchers(new AntPathRequestMatcher("/testURL2")).hasRole("Admin")
//                            .anyRequest().authenticated();
                            .anyRequest().permitAll();
                }
        ).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
//pom 3.1.2

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests((authorize) -> {
//                    authorize
//                            .requestMatchers("/registration", "/login", "/register").permitAll()
//                            .requestMatchers("/testUserPage").hasRole("User")
//                            .requestMatchers("/testAdminPage").hasRole("Admin")
//                            .anyRequest().authenticated();
//                }
//        ).build();
//    }

//    @Bean
//    @Order(2)
//    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
//        return http.securityMatcher()
//    }


}
