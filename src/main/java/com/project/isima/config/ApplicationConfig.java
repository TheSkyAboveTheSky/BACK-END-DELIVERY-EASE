package com.project.isima.config;

import com.project.isima.entities.User;
import com.project.isima.enums.AccountStatus;
import com.project.isima.enums.Role;
import com.project.isima.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import static com.project.isima.entities.ImageConstants.*;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig implements WebMvcConfigurer {

    private final UserRepository userRepository;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = "file:///" + ABSOLUTE_PATH + "/" + DIRECTORY;
        registry.addResourceHandler("/content/**")
                .addResourceLocations(path);
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> (UserDetails) userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner createAdminAccount(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Optional<User> adminUser = userRepository.findByRole(Role.ADMIN);

            if (adminUser.isEmpty()) {
                User admin = User.builder()
                        .firstName("Admin")
                        .lastName("Admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("123456"))
                        .role(Role.ADMIN)
                        .picturePath(DIRECTORY+"Anonyme.jpeg")
                        .accountStatus(AccountStatus.ACTIVATED)
                        .build();
                userRepository.save(admin);
            }
        };
    }

}