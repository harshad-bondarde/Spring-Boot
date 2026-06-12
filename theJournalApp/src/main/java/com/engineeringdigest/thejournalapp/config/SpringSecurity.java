package com.engineeringdigest.thejournalapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean; // 1. Added missing import
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer; // Needed for the new lambda syntax

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // 2. Changed to requestMatchers (the modern equivalent)
                        .requestMatchers("/journal/**","/user/**").authenticated()
                        .anyRequest().permitAll()
                )
                // 3. Added standard API configurations so Postman requests work
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()); //out api is stateless so we disabled csrf , csrf means we have to send a token in http request

        return http.build();
    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}