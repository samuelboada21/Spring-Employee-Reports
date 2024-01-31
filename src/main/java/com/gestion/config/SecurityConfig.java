package com.gestion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/form","/form/*", "/delete/*").hasRole("ADMIN")
                    .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/employeelist", true)
                .permitAll()
                )
                .logout(logout -> logout
                .permitAll()
                );

        return http.build();
    }
    
     @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User
                .withUsername("Samuel")
                .password("$2a$10$gJr0htlGVh23zciB/sG7wu1od4xW8542isybSvZALaMsz4feOMY9i")
                .roles("USER")
                .build();

        UserDetails user2 = User
                .withUsername("admin")
                .password("$2a$10$gJr0htlGVh23zciB/sG7wu1od4xW8542isybSvZALaMsz4feOMY9i")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}