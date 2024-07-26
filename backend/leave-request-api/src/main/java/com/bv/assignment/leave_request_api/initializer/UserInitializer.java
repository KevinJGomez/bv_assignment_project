package com.bv.assignment.leave_request_api.initializer;

import com.bv.assignment.leave_request_api.models.RequestUser;
import com.bv.assignment.leave_request_api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserInitializer {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String username = "admin";
            String password = "admin@123";

            if (userRepository.findByUsername(username) == null) {
                RequestUser user = new RequestUser();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(password));
                user.setRole(RequestUser.Role.ADMIN);
                userRepository.save(user);
                System.out.println("Default user created: " + username);
            } else {
                System.out.println("User already exists: " + username);
            }
        };
    }
}

