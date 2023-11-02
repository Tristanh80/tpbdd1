package com.example.backendtporacle.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(req -> req
                        .successHandler(new CustomAuthenticationSuccessHandler())
                ).logout(logout -> logout
                        .logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("JSESSIONID", "region"));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails nord =
                User.withDefaultPasswordEncoder()
                        .username("Nord")
                        .password("Nord")
                        .build();
        UserDetails sud =
                User.withDefaultPasswordEncoder()
                        .username("Sud")
                        .password("Sud")
                        .build();
        UserDetails est =
                User.withDefaultPasswordEncoder()
                        .username("Est")
                        .password("Est")
                        .build();
        UserDetails ouest =
                User.withDefaultPasswordEncoder()
                        .username("Ouest")
                        .password("Ouest")
                        .build();
        UserDetails centre =
                User.withDefaultPasswordEncoder()
                        .username("Centre")
                        .password("Centre")
                        .build();

        return new InMemoryUserDetailsManager(nord, sud, est, ouest, centre);
    }
}
