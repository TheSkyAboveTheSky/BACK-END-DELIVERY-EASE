package com.project.isima.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(withDefaults())
                .authorizeHttpRequests(authorize->authorize.requestMatchers("/api/v1/auth/**").permitAll());
        httpSecurity.authorizeHttpRequests(authorize->authorize.requestMatchers("/content/**").permitAll());

        httpSecurity.authorizeHttpRequests(authorize->authorize.requestMatchers("/api/v1/parcels/**").hasAuthority("SENDER"));
        httpSecurity.authorizeHttpRequests(authorize->authorize.requestMatchers("/api/v1/trips/all/**").hasAuthority("DELIVERY_PERSON"));
        httpSecurity.authorizeHttpRequests(authorize->authorize.requestMatchers("/api/v1/trips/add/**").hasAuthority("DELIVERY_PERSON"));
        httpSecurity.authorizeHttpRequests(authorize->authorize.requestMatchers("/api/v1/trips/update/**").hasAuthority("DELIVERY_PERSON"));
        httpSecurity.authorizeHttpRequests(authorize->authorize.requestMatchers("/api/v1/trips/delete/**").hasAuthority("DELIVERY_PERSON"));
        httpSecurity.authorizeHttpRequests(authorize->authorize.requestMatchers("/api/v1/trips/searchTrips").hasAuthority("SENDER"));
        httpSecurity.authorizeHttpRequests(authorize->authorize.requestMatchers("/api/v1/users/all").hasAuthority("ADMIN"));
        httpSecurity.authorizeHttpRequests(authorize->authorize.requestMatchers("/api/v1/users/updateAccountStatus/**").hasAuthority("ADMIN"));

        httpSecurity.authorizeHttpRequests(authorize->authorize.anyRequest().authenticated());
        httpSecurity
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}